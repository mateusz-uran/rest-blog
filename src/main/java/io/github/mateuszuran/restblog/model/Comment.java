package io.github.mateuszuran.restblog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    @Column(length = 555)
    private String content;
    private String date;
    @JsonBackReference(value = "post-comment")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
    @JsonBackReference(value = "user-comment")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public void toUpdate(Comment toUpdate) {
        author = toUpdate.author;
        content = toUpdate.content;
        date = toUpdate.date;
    }
}
