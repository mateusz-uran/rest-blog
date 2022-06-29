package io.github.mateuszuran.restblog.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String author;
    private String content;
    private String date;
}
