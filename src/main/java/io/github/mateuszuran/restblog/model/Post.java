package io.github.mateuszuran.restblog.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    private String intro;
    @Column(length = 555)
    private String content;
    private String imagePath;
    private String imageName;
    private String imageType;
    private String projectCodeLink;
    private String projectDemoLink;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> comments;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Tags> tags;

    public Post(final Long id, final String header, final String intro, final String content, final String imagePath, final String imageName, final String imageType, final String projectCodeLink, final String projectDemoLink) {
        this.id = id;
        this.header = header;
        this.intro = intro;
        this.content = content;
        this.imagePath = imagePath;
        this.imageName = imageName;
        this.imageType = imageType;
        this.projectCodeLink = projectCodeLink;
        this.projectDemoLink = projectDemoLink;
    }

    public void updateForm(Post source) {
        header = source.header;
        intro = source.intro;
        content = source.content;
        imagePath = source.imagePath;
        imageName = source.imageName;
        imageType = source.imageType;
        projectCodeLink = source.projectCodeLink;
        projectDemoLink = source.projectDemoLink;
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
