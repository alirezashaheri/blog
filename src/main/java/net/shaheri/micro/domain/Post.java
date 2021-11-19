package net.shaheri.micro.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@Document(indexName = "blog")
public class Post implements Serializable {

    @Id
    private String id;

    private String pic;

    private String language;

    private String title;

    private String description;

    private String content;

    @Field(name = "@timestamp", type = FieldType.Date)
    @JsonProperty("@timestamp")
    private Date timestamp;

    private Set<String> keywords;

    @Field(type = FieldType.Nested, includeInParent = true)
    private User author;

    @Field(type = FieldType.Nested, includeInParent = true)
    private Set<Category> categories;

    @Field(type = FieldType.Nested, includeInParent = true)
    private Set<Review> reviews;

    @Data
    public static class Category implements Serializable {
        private String code;
        private String title;
        private Category parent;

        @Override
        public String toString() {
            return "Category{" +
                    "code='" + code + '\'' +
                    ", title='" + title + '\'' +
                    ", parent=" + parent +
                    '}';
        }
    }
    @Data
    public static class User implements Serializable {
        private String username;
        private String pic;

        @Override
        public String toString() {
            return "User{" +
                    "username='" + username + '\'' +
                    ", pic='" + pic + '\'' +
                    '}';
        }
    }
    @Data
    public static class Review implements Serializable {
        private User owner;
        @Field(name = "@timestamp", type = FieldType.Date)
        @JsonProperty("@timestamp")
        private Date timestamp;
        private String language;
        private String content;
        private Double score;
    }
}