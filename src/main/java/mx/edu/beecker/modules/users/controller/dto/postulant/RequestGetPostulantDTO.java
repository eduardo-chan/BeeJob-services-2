package mx.edu.beecker.modules.users.controller.dto.postulant;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestGetPostulantDTO {
    @NotNull(message = "User postulant id is required")
    private Long id;
}
