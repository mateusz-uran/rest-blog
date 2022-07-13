package io.github.mateuszuran.restblog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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
    private String intro;
    @Column(length = 555)
    private String content;
    private String imagePath;
    private String imageName;
    private String imageType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> comments;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Tags> tags;

    public void updateForm(Post source) {
        header = source.header;
        intro = source.intro;
        content = source.content;
        imagePath = source.imagePath;
        imageName = source.imageName;
        imageType = source.imageType;
    }
}
