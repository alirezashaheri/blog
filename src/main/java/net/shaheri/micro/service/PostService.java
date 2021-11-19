package net.shaheri.micro.service;

import net.shaheri.micro.domain.Post;
import net.shaheri.micro.model.ActionResult;
import net.shaheri.micro.model.PostPreview;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PostService {
    ActionResult save(Post post);

    Optional<Post> findPostById(String id);

    Page<PostPreview> findAll(int pageNum, int pageSize);

    Page<PostPreview> findAllByAuthor(String author, int pageNum, int pageSize);

    Page<PostPreview> findAllByKeyword(String keyword, int pageNum, int pageSize);

    Page<PostPreview> findAllByCategory(String category, int pageNum, int pageSize);

    Page<PostPreview> searchAllByTitleAndContent(String keyword, int pageNum, int pageSize);
}
