package io.github.mateuszuran.restblog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 20)
    private String username;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(nullable = false, length = 120)
    private String password;
    @Column(nullable = false, length = 50)
    private String gender;
    @Column(nullable = false, length = 400)
    private String avatar;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Comment> comments;

    public User() {
    }

    public User(final String username, final String email, final String password, final String gender, final String avatar) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.avatar = avatar;
    }

    public User(final Long id, final String username, final String email, final String password, final String gender, final String avatar) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.avatar = avatar;
    }

    public User(final Long id, final String username, final String email, final String password, final String gender, final String avatar, final Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.avatar = avatar;
        this.roles = roles;
    }
}
