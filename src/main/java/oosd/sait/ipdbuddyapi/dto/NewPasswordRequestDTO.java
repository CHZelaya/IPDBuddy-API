package oosd.sait.ipdbuddyapi.dto;

import com.fasterxml.jackson.core.SerializableString;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPasswordRequestDTO {

    @NotBlank(message = "Username cannot be blank")
    private String email;

    @NotBlank(message = "New password cannot be blank")
    private String newPassword;

    private String session;
}
