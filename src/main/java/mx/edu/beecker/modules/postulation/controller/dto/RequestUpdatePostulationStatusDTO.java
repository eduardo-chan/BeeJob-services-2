package mx.edu.beecker.modules.postulation.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.postulation.model.EPostulationStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdatePostulationStatusDTO {

    @NotNull(message = "Postulation id is required")
    private Long postulationId;

    @NotNull(message = "Status is required")
    private EPostulationStatus status;
}
