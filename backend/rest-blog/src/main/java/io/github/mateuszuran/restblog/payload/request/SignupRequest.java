package io.github.mateuszuran.restblog.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class SignupRequest {
    @NotBlank @Size(min = 3, max = 20)
    private String username;
    @NotBlank @Size(max = 50) @Email
    private String email;
    @NotBlank @Size(min = 6, max = 40)
    private String password;
    @NotBlank @Size(min = 3, max = 40)
    private String gender;
    private String avatar;

    public SignupRequest(final String username, final String email, final String password, final String gender, final String avatar) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.avatar = avatar;
    }
}
