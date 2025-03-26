package mx.edu.beecker.modules.hard_skills.service;

import mx.edu.beecker.modules.education.controller.dto.RequestBaseForDeleteAndGetOneDTO;
import mx.edu.beecker.modules.hard_skills.controller.dto.RequestHardSkillDTO;
import mx.edu.beecker.modules.hard_skills.controller.dto.RequestUpdateHardSkillDTO;
import mx.edu.beecker.modules.hard_skills.controller.dto.ResponseHardSkillDTO;
import mx.edu.beecker.modules.hard_skills.model.BeanHardSkill;
import mx.edu.beecker.modules.hard_skills.model.IHardSkill;
import mx.edu.beecker.modules.professional_information.model.BeanProfessionalInformation;
import mx.edu.beecker.modules.professional_information.model.IProfessionalInformation;
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
public class HardSkillService {

    private final IHardSkill hardSkillRepository;
    private final IProfessionalInformation professionalInformationRepository;
    private final IUser userRepository;

    public HardSkillService(IHardSkill hardSkillRepository, IProfessionalInformation professionalInformationRepository, IUser userRepository) {
        this.hardSkillRepository = hardSkillRepository;
        this.professionalInformationRepository = professionalInformationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public BeanHardSkill addHardSkill(RequestHardSkillDTO hardSkillDTO) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));


        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseGet(() -> {
                    BeanProfessionalInformation newProfessionalInfo = new BeanProfessionalInformation();
                    newProfessionalInfo.setUser(user);
                    return professionalInformationRepository.save(newProfessionalInfo);
                });


        BeanHardSkill hardSkill = new BeanHardSkill();
        hardSkill.setSkillName(hardSkillDTO.getSkillName());
        hardSkill.setProfessionalInformation(professionalInfo);

        return hardSkillRepository.save(hardSkill);
    }

    @Transactional
    public ResponseHardSkillDTO  updateHardSkill(RequestUpdateHardSkillDTO hardSkillDTO) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));


        BeanProfessionalInformation professionalInformation = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));


        BeanHardSkill hardSkill = hardSkillRepository.findById(hardSkillDTO.getId())
                .orElseThrow(() -> new CustomException("Hard skill not found", HttpStatus.NOT_FOUND));


        hardSkill.setSkillName(hardSkillDTO.getSkillName());
        hardSkill.setProfessionalInformation(professionalInformation);

        hardSkillRepository.save(hardSkill);
        return new ResponseHardSkillDTO(hardSkill);
    }

    @Transactional
    public void deleteHardSkill(Long id) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        BeanProfessionalInformation professionalInformation = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));

        BeanHardSkill hardSkill = hardSkillRepository.findById(id)
                .orElseThrow(() -> new CustomException("Hard skill not found", HttpStatus.NOT_FOUND));

        if (!hardSkill.getProfessionalInformation().equals(professionalInformation)) {
            throw new CustomException("Hard skill does not belong to the authenticated user", HttpStatus.FORBIDDEN);
        }

        hardSkillRepository.delete(hardSkill);
    }

    public List<ResponseHardSkillDTO> getAllHardSkills() {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));


        Optional<BeanProfessionalInformation> professionalInformationOpt = professionalInformationRepository.findByUser(user);

        if (professionalInformationOpt.isEmpty()) {
            return Collections.emptyList();
        }

        return professionalInformationOpt.get().getHardSkills().stream()
                .map(ResponseHardSkillDTO::new)
                .collect(Collectors.toList());
    }

    public ResponseHardSkillDTO getHardSkillById(RequestBaseForDeleteAndGetOneDTO requestDTO) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));

        BeanHardSkill hardSkill = hardSkillRepository.findById(requestDTO.getId())
                .orElseThrow(() -> new CustomException("Hard skill not found", HttpStatus.NOT_FOUND));

        if (!hardSkill.getProfessionalInformation().equals(professionalInfo)) {
            throw new CustomException("You do not have permission to view this hard skill", HttpStatus.FORBIDDEN);
        }

        return new ResponseHardSkillDTO(hardSkill);
    }

}
