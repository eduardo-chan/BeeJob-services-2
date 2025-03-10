package mx.edu.beecker.modules.professional_information.service;

import mx.edu.beecker.modules.professional_information.controller.dto.ResponseProfessionalInformationDTO;
import mx.edu.beecker.modules.professional_information.model.BeanProfessionalInformation;
import mx.edu.beecker.modules.professional_information.model.IProfessionalInformation;
import mx.edu.beecker.modules.users.model.BeanUser;
import mx.edu.beecker.modules.users.model.IUser;
import mx.edu.beecker.utils.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProfessionalInformationService {

    private final IUser userRepository;
    private final IProfessionalInformation professionalInformationRepository;

    public ProfessionalInformationService (IUser userRepository, IProfessionalInformation professionalInformationRepository) {
        this.userRepository = userRepository;
        this.professionalInformationRepository = professionalInformationRepository;
    }



    public ResponseEntity<ResponseProfessionalInformationDTO> getProfessionalInformation() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        BeanProfessionalInformation professionalInformation = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(new ResponseProfessionalInformationDTO(professionalInformation));
    }

}
