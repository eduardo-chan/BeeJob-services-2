package mx.edu.beecker.modules.postulation.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.postulation.model.BeanPostulation;
import mx.edu.beecker.modules.postulation.model.EPostulationStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePostulationDTO {
    private Long id;
    private EPostulationStatus status;

    public ResponsePostulationDTO(BeanPostulation postulation) {
        this.id = postulation.getId();
        this.status = postulation.getStatus();
    }
}
