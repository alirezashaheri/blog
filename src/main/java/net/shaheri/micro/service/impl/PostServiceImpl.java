package net.shaheri.micro.service.impl;

import net.shaheri.micro.domain.Post;
import net.shaheri.micro.model.ActionResult;
import net.shaheri.micro.model.PostPreview;
import net.shaheri.micro.model.enumeration.ActionStatus;
import net.shaheri.micro.service.PostService;
import net.shaheri.micro.util.QueryUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final QueryUtil util;

    public PostServiceImpl(QueryUtil util) {
        this.util = util;
    }

    @Override
    public ActionResult save(Post post) {
        ActionResult result = new ActionResult(ActionStatus.FAILED, null);
        try {
            result.setId(util.save(post).getId());
            result.setStatus(ActionStatus.SUCCESSFUL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<Post> findPostById(String id) {
        return util.findById(id);
    }

    @Override
    public Page<PostPreview> findAll(int pageNum, int pageSize) {
        return util.findAll(pageNum, pageSize).map(PostPreview::of);
    }

    @Override
    public Page<PostPreview> findAllByAuthor(String author, int pageNum, int pageSize) {
        return util.findByAuthor(author, pageNum, pageSize).map(PostPreview::of);
    }

    @Override
    public Page<PostPreview> findAllByKeyword(String keyword, int pageNum, int pageSize) {
        return util.findAllByKeyword(keyword, pageNum, pageSize);
    }

    @Override
    public Page<PostPreview> findAllByCategory(String category, int pageNum, int pageSize) {
        return util.findAllByCategory(category, pageNum, pageSize);
    }

    @Override
    public Page<PostPreview> searchAllByTitleAndContent(String keyword, int pageNum, int pageSize) {
        return util.search(keyword, pageNum, pageSize);
    }
}
