package mx.edu.beecker.modules.vacants.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.vacants.model.BeanVacant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseStatusVacantDTO {

    private Long id;
    private boolean status;

    public ResponseStatusVacantDTO(BeanVacant vacant) {
        this.id = vacant.getId();
        this.status = vacant.isStatus();
    }


}
