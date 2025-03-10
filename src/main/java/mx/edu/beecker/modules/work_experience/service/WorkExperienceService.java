package mx.edu.beecker.modules.work_experience.service;

import mx.edu.beecker.modules.education.controller.dto.RequestBaseForDeleteAndGetOneDTO;
import mx.edu.beecker.modules.professional_information.model.BeanProfessionalInformation;
import mx.edu.beecker.modules.professional_information.model.IProfessionalInformation;
import mx.edu.beecker.modules.users.model.BeanUser;
import mx.edu.beecker.modules.users.model.IUser;
import mx.edu.beecker.modules.work_experience.controller.dto.RequestUpdateWorkExperienceDTO;
import mx.edu.beecker.modules.work_experience.controller.dto.RequestWorkExperienceDTO;
import mx.edu.beecker.modules.work_experience.controller.dto.ResponseWorkExperienceDTO;
import mx.edu.beecker.modules.work_experience.model.BeanWorkExperience;
import mx.edu.beecker.modules.work_experience.model.IWorkExperience;
import mx.edu.beecker.utils.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkExperienceService {
    private final IWorkExperience workExperienceRepository;
    private final IProfessionalInformation professionalInformationRepository;
    private final IUser userRepository;

    public WorkExperienceService(IWorkExperience workExperienceRepository, IProfessionalInformation professionalInformationRepository, IUser userRepository) {
        this.workExperienceRepository = workExperienceRepository;
        this.professionalInformationRepository = professionalInformationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public BeanWorkExperience addWorkExperience(RequestWorkExperienceDTO workExperienceDTO) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));


        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseGet(() -> {
                    BeanProfessionalInformation newProfessionalInfo = new BeanProfessionalInformation();
                    newProfessionalInfo.setUser(user);
                    return professionalInformationRepository.save(newProfessionalInfo);
                });


        BeanWorkExperience workExperience = new BeanWorkExperience();
        workExperience.setJobTitle(workExperienceDTO.getJobTitle());
        workExperience.setCompanyName(workExperienceDTO.getCompanyName());
        workExperience.setDescription(workExperienceDTO.getDescription());
        workExperience.setLocation(workExperienceDTO.getLocation());
        workExperience.setProfessionalInformation(professionalInfo);
        workExperience.setStartDate(workExperienceDTO.getStartDate());
        workExperience.setEndDate(workExperienceDTO.getEndDate());

        return workExperienceRepository.save(workExperience);
    }

    @Transactional
    public ResponseWorkExperienceDTO updateWorkExperience(RequestUpdateWorkExperienceDTO requestDTO) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));


        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));


        BeanWorkExperience workExperience = workExperienceRepository.findById(requestDTO.getId())
                .orElseThrow(() -> new CustomException("Work experience not found", HttpStatus.NOT_FOUND));


        if (!workExperience.getProfessionalInformation().equals(professionalInfo)) {
            throw new CustomException("You do not have permission to update this work experience", HttpStatus.FORBIDDEN);
        }


        workExperience.setJobTitle(requestDTO.getJobTitle());
        workExperience.setCompanyName(requestDTO.getCompanyName());
        workExperience.setDescription(requestDTO.getDescription());
        workExperience.setLocation(requestDTO.getLocation());


        workExperienceRepository.save(workExperience);
        return new ResponseWorkExperienceDTO(workExperience);
    }

    @Transactional
    public void deleteWorkExperience(Long id) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));

        BeanWorkExperience workExperience = workExperienceRepository.findById(id)
                .orElseThrow(() -> new CustomException("Work experience not found", HttpStatus.NOT_FOUND));

        if (!workExperience.getProfessionalInformation().equals(professionalInfo)) {
            throw new CustomException("You do not have permission to delete this work experience", HttpStatus.FORBIDDEN);
        }

        workExperienceRepository.delete(workExperience);

        professionalInfo.getWorkExperiences().remove(workExperience);
    }

    public List<ResponseWorkExperienceDTO> getAllWorkExperiences() {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));

        return professionalInfo.getWorkExperiences().stream()
                .map(workExperience -> new ResponseWorkExperienceDTO(workExperience))
                .collect(Collectors.toList());
    }

    public ResponseWorkExperienceDTO getWorkExperienceById(RequestBaseForDeleteAndGetOneDTO requestDTO) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));

        BeanWorkExperience workExperience = workExperienceRepository.findById(requestDTO.getId())
                .orElseThrow(() -> new CustomException("Work experience not found", HttpStatus.NOT_FOUND));

        if (!workExperience.getProfessionalInformation().equals(professionalInfo)) {
            throw new CustomException("You do not have permission to view this work experience", HttpStatus.FORBIDDEN);
        }

        return new ResponseWorkExperienceDTO(workExperience);
    }
}
