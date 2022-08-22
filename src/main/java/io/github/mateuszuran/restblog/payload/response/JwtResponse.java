package io.github.mateuszuran.restblog.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private Long id;
    private String username;
    private String avatar;
    private String email;
    private List<String> roles;

    public JwtResponse(final String accessToken, final String refreshToken, final Long id, final String username, String avatar, final String email, final List<String> roles) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.email = email;
        this.roles = roles;
    }
}
