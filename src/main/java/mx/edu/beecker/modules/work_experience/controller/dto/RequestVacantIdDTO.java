package mx.edu.beecker.modules.work_experience.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestVacantIdDTO {
    @NotNull(message = "id is required")
    private Long id;
}
