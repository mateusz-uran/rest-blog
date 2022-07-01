package io.github.mateuszuran.restblog.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String header;
    private String content;
    private String imagePath;
    private String imageName;
    private String imageType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private Set<Comment> comments;

    public void updateForm(Post source) {
        header = source.header;
        content = source.content;
        imagePath = source.imagePath;
        imageName = source.imageName;
        imageType = source.imageType;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", content='" + content + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", imageName='" + imageName + '\'' +
                ", imageType='" + imageType + '\'' +
                ", comments=" + comments +
                '}';
    }
}
