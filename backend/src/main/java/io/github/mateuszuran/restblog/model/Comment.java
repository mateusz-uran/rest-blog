package io.github.mateuszuran.restblog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    @Column(length = 400)
    private String authorAvatar;
    @Column(length = 555)
    private String content;
    private String date;
    @JsonBackReference(value = "post-comment")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
    @JsonBackReference(value = "user-comment")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Comment(final Long id, final String author, final String content, final Post post, final User user) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.post = post;
        this.user = user;
    }

    public void toUpdate(Comment toUpdate) {
        author = toUpdate.author;
        authorAvatar = toUpdate.authorAvatar;
        content = toUpdate.content;
        date = toUpdate.date;
    }
}
