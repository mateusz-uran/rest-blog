package io.github.mateuszuran.restblog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
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
    private List<Comment> comments;

    public void updateForm(Post source) {
        header = source.header;
        content = source.content;
        imagePath = source.imagePath;
        imageName = source.imageName;
        imageType = source.imageType;
    }
}
