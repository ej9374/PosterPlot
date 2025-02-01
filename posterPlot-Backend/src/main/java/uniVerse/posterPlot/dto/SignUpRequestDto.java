package uniVerse.posterPlot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {

    @Schema(example = "user123")
    private String id;

    @Schema(example = "password123")
    private String password;

    @Schema(example = "password123")
    private String confirmPassword;

    @Schema(example = "user@gmail.com")
    private String email;

    public SignUpRequestDto(String id, String password, String confirmPassword, String email) {
        this.id = id;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
    }
}
