package mx.edu.beecker.modules.professional_information.controller;

import mx.edu.beecker.modules.professional_information.controller.dto.ResponseProfessionalInformationDTO;
import mx.edu.beecker.modules.professional_information.service.ProfessionalInformationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/beejob/professionalInformation")
public class ProfessionalInformationController {

    private final ProfessionalInformationService professionalInformationService;

    public ProfessionalInformationController(ProfessionalInformationService professionalInformationService) {
        this.professionalInformationService = professionalInformationService;
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @GetMapping("/get-professional-information")
    public ResponseEntity<ResponseProfessionalInformationDTO> getProfessionalInformation() {
        return professionalInformationService.getProfessionalInformation();
    }

}
