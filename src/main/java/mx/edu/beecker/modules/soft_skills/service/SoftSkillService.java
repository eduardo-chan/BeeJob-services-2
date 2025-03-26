package mx.edu.beecker.modules.soft_skills.service;

import mx.edu.beecker.modules.education.controller.dto.RequestBaseForDeleteAndGetOneDTO;
import mx.edu.beecker.modules.hard_skills.controller.dto.ResponseHardSkillDTO;
import mx.edu.beecker.modules.professional_information.model.BeanProfessionalInformation;
import mx.edu.beecker.modules.professional_information.model.IProfessionalInformation;
import mx.edu.beecker.modules.soft_skills.controller.dto.RequestSoftSkillDTO;
import mx.edu.beecker.modules.soft_skills.controller.dto.RequestUpdateSoftSkillDTO;
import mx.edu.beecker.modules.soft_skills.controller.dto.ResponseSoftSkillDTO;
import mx.edu.beecker.modules.soft_skills.model.BeanSoftSkill;
import mx.edu.beecker.modules.soft_skills.model.ISoftSkill;
import mx.edu.beecker.modules.users.model.BeanUser;
import mx.edu.beecker.modules.users.model.IUser;
import mx.edu.beecker.utils.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SoftSkillService {
    private final ISoftSkill softSkillRepository;
    private final IProfessionalInformation professionalInformationRepository;
    private final IUser userRepository;

    public SoftSkillService(ISoftSkill softSkillRepository, IProfessionalInformation professionalInformationRepository, IUser userRepository) {
        this.softSkillRepository = softSkillRepository;
        this.professionalInformationRepository = professionalInformationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public BeanSoftSkill addsoftSkill(RequestSoftSkillDTO softSkillDTO) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));


        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseGet(() -> {
                    BeanProfessionalInformation newProfessionalInfo = new BeanProfessionalInformation();
                    newProfessionalInfo.setUser(user);
                    return professionalInformationRepository.save(newProfessionalInfo);
                });


        BeanSoftSkill softSkill = new BeanSoftSkill();
        softSkill.setSkillName(softSkillDTO.getSkillName());
        softSkill.setProfessionalInformation(professionalInfo);

        return softSkillRepository.save(softSkill);

    }

    @Transactional
    public ResponseSoftSkillDTO updateSoftSkill(RequestUpdateSoftSkillDTO softSkillDTO) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));


        BeanProfessionalInformation professionalInformation = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));


        BeanSoftSkill softSkill = softSkillRepository.findById(softSkillDTO.getId())
                .orElseThrow(() -> new CustomException("Soft skill not found", HttpStatus.NOT_FOUND));


        softSkill.setSkillName(softSkillDTO.getSkillName());
        softSkill.setProfessionalInformation(professionalInformation);

        softSkillRepository.save(softSkill);
        return new ResponseSoftSkillDTO(softSkill);
    }
    @Transactional
    public void deleteSoftSkill(Long id) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        BeanProfessionalInformation professionalInformation = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));

        BeanSoftSkill softSkill = softSkillRepository.findById(id)
                .orElseThrow(() -> new CustomException("Soft skill not found", HttpStatus.NOT_FOUND));

        if (!softSkill.getProfessionalInformation().equals(professionalInformation)) {
            throw new CustomException("Soft skill does not belong to the authenticated user", HttpStatus.FORBIDDEN);
        }

        softSkillRepository.delete(softSkill);
    }

    public List<ResponseSoftSkillDTO> getAllSoftSkills() {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));


        Optional<BeanProfessionalInformation> professionalInformationOpt = professionalInformationRepository.findByUser(user);

        if (professionalInformationOpt.isEmpty()) {
            return Collections.emptyList();
        }

        return professionalInformationOpt.get().getSoftSkills().stream()
                .map(ResponseSoftSkillDTO::new)
                .collect(Collectors.toList());
    }

    public ResponseSoftSkillDTO getSoftSkillById(RequestBaseForDeleteAndGetOneDTO requestDTO) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));

        BeanSoftSkill softSkill = softSkillRepository.findById(requestDTO.getId())
                .orElseThrow(() -> new CustomException("Soft skill not found", HttpStatus.NOT_FOUND));

        if (!softSkill.getProfessionalInformation().equals(professionalInfo)) {
            throw new CustomException("You do not have permission to view this soft skill", HttpStatus.FORBIDDEN);
        }

        return new ResponseSoftSkillDTO(softSkill);
    }
}
