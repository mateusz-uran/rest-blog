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
    private String content;
    private String date;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public void toUpdate(Comment toUpdate) {
        author = toUpdate.author;
        content = toUpdate.content;
        date = toUpdate.date;
    }

    public void addComment(Comment comment) {
        author = comment.author;
        content = comment.content;
        date = comment.date;
    }
}
