package net.shaheri.micro.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.shaheri.micro.domain.Post;
import net.shaheri.micro.model.PostPreview;
import net.shaheri.micro.repository.PostRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class QueryUtil {

    private final PostRepository repository;
    private final RestHighLevelClient client;

    private final ObjectMapper objectMapper;
    private final PageUtil pageUtil;

    public QueryUtil(PostRepository repository, RestHighLevelClient client, PageUtil pageUtil) {
        this.repository = repository;
        this.client = client;
        this.pageUtil = pageUtil;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        this.objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX"));
    }

    private <T> Page<T> query(QueryBuilder queryBuilder, int pageNum, int pageSize, Class<T> type, Comparator<? super T> comparator) {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(queryBuilder);
        sourceBuilder.timeout(TimeValue.timeValueSeconds(10));
        searchRequest.source(sourceBuilder);
        List<T> items = new ArrayList<>(Collections.emptyList());
        SearchResponse result = null;
        try {
            result = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        if (result != null && result.getHits() != null && result.getHits().getHits() != null) {
            items.addAll(Arrays.stream(result.getHits().getHits())
                    .map(documentFields -> {
                        try {
                            return objectMapper.readValue(documentFields.getSourceAsString(), type);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace(System.out);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .sorted(comparator)
                    .collect(Collectors.toList()));
        }
        return pageUtil.toPage(items, pageSize, pageNum);
    }


    public Post save(Post post) {
        return repository.save(post);
    }

    public Optional<Post> findById(String id) {
        return repository.findById(id);
    }

    public Page<Post> findAll(int pageNum, int pageSize) {
        return repository.findAll(PageRequest.of(pageNum, pageSize, Sort.by("timestamp").descending()));
    }

    public Page<Post> findByAuthor(String author, int pageNum, int pageSize) {
        return repository.findAllByAuthor_Username(author, PageRequest.of(pageNum, pageSize, Sort.by("timestamp").descending()));
    }

    public Page<PostPreview> findAllByKeyword(String keyword, int pageNum, int pageSize) {
        return query(
                QueryBuilders
                        .boolQuery()
                        .must(QueryBuilders.matchQuery("keywords", keyword)),
                pageNum,
                pageSize,
                Post.class,
                Comparator.comparing(post -> ((Post) post).getTimestamp()).reversed()
        ).map(PostPreview::of);
    }

    public Page<PostPreview> findAllByCategory(String category, int pageNum, int pageSize) {
        return query(
                QueryBuilders
                        .boolQuery()
                        .must(QueryBuilders.multiMatchQuery(category, "categories.code", "categories.parent.code")),
                pageNum,
                pageSize,
                Post.class,
                Comparator.comparing(post -> ((Post) post).getTimestamp()).reversed()
        ).map(PostPreview::of);
    }

    public Page<PostPreview> search(String keyword, int pageNum, int pageSize) {
        return query(
                QueryBuilders
                        .boolQuery()
                        .must(QueryBuilders.multiMatchQuery(keyword, "title", "description", "content").fuzziness(1)),
                pageNum,
                pageSize,
                Post.class,
                Comparator.comparing(post -> ((Post) post).getTimestamp()).reversed()
        ).map(PostPreview::of);
    }
}
