package net.shaheri.micro.repository;

import net.shaheri.micro.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostRepository extends ElasticsearchRepository<Post, String> {
    Page<Post> findAllByAuthor_Username(String author, Pageable pageable);
}
