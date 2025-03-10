package mx.edu.beecker.modules.postulation.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPostulationDTO {
    @NotNull(message = "Vacant id is required")
    private Long id;
}
