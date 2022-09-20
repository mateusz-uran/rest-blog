package io.github.mateuszuran.restblog.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Setter
@Getter
public class TokenRefreshRequest {
    @NotBlank
    private String refreshToken;
}
