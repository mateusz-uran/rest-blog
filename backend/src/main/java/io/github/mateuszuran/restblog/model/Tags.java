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
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public void toUpdate(Tags toUpdate) {
        content = toUpdate.content;
    }
}
