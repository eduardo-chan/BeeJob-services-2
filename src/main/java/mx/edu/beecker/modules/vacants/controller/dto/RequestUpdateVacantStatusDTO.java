package mx.edu.beecker.modules.vacants.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateVacantStatusDTO {

    @NotNull(message = "id is required")
    private Long id;

    @NotNull(message = "Status is required")
    private Boolean status;

}
