package mx.edu.beecker.modules.users.controller.dto.admin;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserStatusDTO {
    @NotNull(message = "user id is required")
    private Long userId;

    @NotNull(message = "status id is required")
    private boolean status;
}
