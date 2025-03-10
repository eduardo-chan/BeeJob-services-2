package mx.edu.beecker.modules.users.controller.dto.postulant;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.users.controller.dto.OnRegister;
import mx.edu.beecker.modules.users.controller.dto.OnUpdate;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUserDTO {

    @NotBlank(message = "Email is required", groups = {OnRegister.class, OnUpdate.class})
    @Email(message = "Invalid email format", groups = {OnRegister.class, OnUpdate.class})
    private String email;

    @NotBlank(message = "Password is required", groups = {OnRegister.class, OnUpdate.class})
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}",
            message = "Password must contain at least one number, one uppercase, " +
                    "letter, one lowercase letter, and one special character " +
                    "and must be at least 8 characters long",
            groups = {OnRegister.class, OnUpdate.class})
    private String password;

    private String role;

    @NotNull(message = "User's name can't be null", groups = {OnRegister.class, OnUpdate.class})
    @NotBlank(message = "User's name can't be blank", groups = {OnRegister.class, OnUpdate.class})
    @Size(max = 30, message = "The name must not exceed 30 characters", groups = {OnRegister.class, OnUpdate.class})
    private String name;

    @NotNull(message = "User's name can't be null", groups = {OnRegister.class, OnUpdate.class})
    @NotBlank(message = "User's name can't be blank", groups = {OnRegister.class, OnUpdate.class})
    @Size(max = 30, message = "The first last name must not exceed 30 characters",
            groups = {OnRegister.class, OnUpdate.class})
    private String firstLastName;

    @Size(max = 30, message = "The second last name must not exceed 30 characters",
            groups = {OnRegister.class, OnUpdate.class})
    private String secondLastName;

    @NotNull(message = "Phone number can't be null", groups = OnUpdate.class)
    @NotBlank(message = "Phone number can't be blank", groups = OnUpdate.class)
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must contain exactly 10 digits",
            groups = {OnUpdate.class})
    private String phoneNumber;

    @Size(max = 30, message = "Work experience must not be exceed {max} characters",
            groups = {OnUpdate.class})
    private String adressState;

    @Size(max = 30, message = "Adress Country must not be exceed {max} characters",
            groups = {OnUpdate.class})
    private String adressCountry;
}
