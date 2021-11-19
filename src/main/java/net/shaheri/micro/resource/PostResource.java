package net.shaheri.micro.resource;

import net.shaheri.micro.domain.Post;
import net.shaheri.micro.model.ActionResult;
import net.shaheri.micro.model.PostPreview;
import net.shaheri.micro.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PostResource {
    private final PostService service;

    public PostResource(PostService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ActionResult> save(@RequestBody Post post) {
        return ResponseEntity.ok(service.save(post));
    }

    @GetMapping
    public Page<PostPreview> findAll(@RequestParam(name = "pageSize", required = false, defaultValue = "15") int pageSize,
                                     @RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum) {
        return service.findAll(pageNum, pageSize);
    }

    @GetMapping("/by/author/{author}")
    public Page<PostPreview> byAuthor(@PathVariable String author,
                                      @RequestParam(name = "pageSize", required = false, defaultValue = "15") int pageSize,
                                      @RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum) {
        return service.findAllByAuthor(author, pageNum, pageSize);
    }

    @GetMapping("/by/keyword/{keyword}")
    public Page<PostPreview> byKeyword(@PathVariable String keyword,
                                      @RequestParam(name = "pageSize", required = false, defaultValue = "15") int pageSize,
                                      @RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum) {
        return service.findAllByKeyword(keyword, pageNum, pageSize);
    }

    @GetMapping("/by/category/{category}")
    public Page<PostPreview> byCategory(@PathVariable String category,
                                       @RequestParam(name = "pageSize", required = false, defaultValue = "15") int pageSize,
                                       @RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum) {
        return service.findAllByCategory(category, pageNum, pageSize);
    }

    @GetMapping("/search")
    public Page<PostPreview> search(@RequestParam(name = "query") String query,
                                        @RequestParam(name = "pageSize", required = false, defaultValue = "15") int pageSize,
                                        @RequestParam(name = "pageNum", required = false, defaultValue = "0") int pageNum) {
        return service.searchAllByTitleAndContent(query, pageNum, pageSize);
    }

    @GetMapping("/{id}")
    ResponseEntity<Post> findById(@PathVariable String id) {
        return ResponseEntity.of(service.findPostById(id));
    }
}
