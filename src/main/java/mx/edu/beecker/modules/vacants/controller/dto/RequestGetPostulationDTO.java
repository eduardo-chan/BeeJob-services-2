package mx.edu.beecker.modules.vacants.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestGetPostulationDTO {
    @NotNull(message = "Postulation id is required")
    private Long id;

}
