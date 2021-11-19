package net.shaheri.micro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.shaheri.micro.domain.Post;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostPreview implements Serializable {
    private String id;
    private String pic;
    private String language;
    private String title;
    private String description;
    private Date timestamp;
    private Post.User author;
    private Set<Post.Category> categories;

    public static PostPreview of(Post post) {
        PostPreview target = new PostPreview();
        BeanUtils.copyProperties(post, target);
        return target;
    }

    @Override
    public String toString() {
        return "PostPreview{" +
                "id='" + id + '\'' +
                ", pic='" + pic + '\'' +
                ", language='" + language + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", timestamp=" + timestamp +
                ", author=" + author +
                ", categories=" + categories +
                '}';
    }
}
