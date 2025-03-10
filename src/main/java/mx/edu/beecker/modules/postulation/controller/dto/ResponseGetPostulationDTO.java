package mx.edu.beecker.modules.postulation.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.postulation.model.BeanPostulation;
import mx.edu.beecker.modules.postulation.model.EPostulationStatus;
import mx.edu.beecker.modules.professional_information.controller.dto.ResponseProfessionalInformationDTO;
import mx.edu.beecker.modules.users.controller.dto.admin.ResponseGetPostulantsDTO;
import mx.edu.beecker.modules.vacants.controller.dto.ResponseVacantDTO;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseGetPostulationDTO {
    private Long id;
    private LocalDate applicationDate;
    private EPostulationStatus status;
    private ResponseGetPostulantsDTO user;
    private ResponseVacantDTO vacant;
    private ResponseProfessionalInformationDTO professionalInformation;
    public ResponseGetPostulationDTO(BeanPostulation postulation) {
        this.id = postulation.getId();
        this.applicationDate = postulation.getApplicationDate();
        this.status = postulation.getStatus();
        this.user = new ResponseGetPostulantsDTO(postulation.getUser());
        this.vacant = new ResponseVacantDTO(postulation.getVacant());
        this.professionalInformation = new ResponseProfessionalInformationDTO(postulation.getProfessionalInformation());
    }
}
