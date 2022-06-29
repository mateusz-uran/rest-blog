package io.github.mateuszuran.restblog.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String header;
    private String content;
}
