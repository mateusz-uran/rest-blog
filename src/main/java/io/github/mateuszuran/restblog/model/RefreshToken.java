package io.github.mateuszuran.restblog.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Column(nullable = false, unique = true)
    private String refreshToken;
    @Column(nullable = false)
    private Instant expiryDate;

    public RefreshToken() {
    }

    public RefreshToken(final String refreshToken, final Instant expiryDate) {
        this.refreshToken = refreshToken;
        this.expiryDate = expiryDate;
    }

    public RefreshToken(final User user, final String refreshToken, final Instant expiryDate) {
        this.user = user;
        this.refreshToken = refreshToken;
        this.expiryDate = expiryDate;
    }
}
