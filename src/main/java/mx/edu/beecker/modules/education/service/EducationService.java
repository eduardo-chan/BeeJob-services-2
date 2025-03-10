package mx.edu.beecker.modules.education.service;

import mx.edu.beecker.modules.education.controller.dto.RequestBaseForDeleteAndGetOneDTO;
import mx.edu.beecker.modules.education.controller.dto.RequestEducationDTO;
import mx.edu.beecker.modules.education.controller.dto.RequestUpdateEducationDTO;
import mx.edu.beecker.modules.education.controller.dto.ResponseEducationDTO;
import mx.edu.beecker.modules.education.model.BeanEducation;
import mx.edu.beecker.modules.education.model.IEducation;
import mx.edu.beecker.modules.professional_information.model.BeanProfessionalInformation;
import mx.edu.beecker.modules.professional_information.model.IProfessionalInformation;
import mx.edu.beecker.modules.users.model.BeanUser;
import mx.edu.beecker.modules.users.model.IUser;
import mx.edu.beecker.utils.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EducationService {

    private final IEducation educationRepository;
    private final IProfessionalInformation professionalInformationRepository;
    private final IUser userRepository;

    public EducationService(IEducation educationRepository, IProfessionalInformation professionalInformationRepository, IUser userRepository) {
        this.educationRepository = educationRepository;
        this.professionalInformationRepository = professionalInformationRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public BeanEducation addEducation(RequestEducationDTO educationDTO) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseGet(() -> {
                    BeanProfessionalInformation newProfessionalInfo = new BeanProfessionalInformation();
                    newProfessionalInfo.setUser(user);
                    return professionalInformationRepository.save(newProfessionalInfo);
                });

        BeanEducation education = new BeanEducation();
        education.setInstitution(educationDTO.getInstitution());
        education.setMajor(educationDTO.getMajor());
        education.setDegree(educationDTO.getDegree());
        education.setSchoolLocation(educationDTO.getSchoolLocation());
        education.setDescription(educationDTO.getDescription());
        education.setStartDate(educationDTO.getStartDate());
        education.setEndDate(educationDTO.getEndDate());
        education.setProfessionalInformation(professionalInfo);

        return educationRepository.save(education);
    }

    @Transactional
    public ResponseEducationDTO  updateEducation(RequestUpdateEducationDTO requestDTO) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));


        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));


        BeanEducation education = educationRepository.findById(requestDTO.getId())
                .orElseThrow(() -> new CustomException("Education not found", HttpStatus.NOT_FOUND));

        if (!education.getProfessionalInformation().equals(professionalInfo)) {
            throw new CustomException("You do not have permission to update this education", HttpStatus.FORBIDDEN);
        }

        education.setInstitution(requestDTO.getInstitution());
        education.setMajor(requestDTO.getMajor());
        education.setDegree(requestDTO.getDegree());
        education.setSchoolLocation(requestDTO.getSchoolLocation());
        education.setDescription(requestDTO.getDescription());
        education.setStartDate(requestDTO.getStartDate());
        education.setEndDate(requestDTO.getEndDate());

        educationRepository.save(education);
        return new ResponseEducationDTO(education);
    }

    @Transactional
    public void deleteEducation(Long id) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));

        BeanEducation education = educationRepository.findById(id)
                .orElseThrow(() -> new CustomException("Education not found", HttpStatus.NOT_FOUND));

        if (!education.getProfessionalInformation().equals(professionalInfo)) {
            throw new CustomException("You do not have permission to delete this education", HttpStatus.FORBIDDEN);
        }

        educationRepository.delete(education);

        professionalInfo.getEducations().remove(education);
    }

    public List<ResponseEducationDTO> getAllEducations() {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));


        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));


        return professionalInfo.getEducations().stream()
                .map(education -> new ResponseEducationDTO(education))
                .collect(Collectors.toList());
    }

    public ResponseEducationDTO getEducationById(RequestBaseForDeleteAndGetOneDTO requestDTO) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));

        BeanEducation education = educationRepository.findById(requestDTO.getId())
                .orElseThrow(() -> new CustomException("Education not found", HttpStatus.NOT_FOUND));

        if (!education.getProfessionalInformation().equals(professionalInfo)) {
            throw new CustomException("You do not have permission to view this education", HttpStatus.FORBIDDEN);
        }

        return new ResponseEducationDTO(education);
    }

}
