package io.github.mateuszuran.restblog.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String imagePath;
    private String imageName;
    private String imageType;
}
