package mx.edu.beecker.modules.professional_information.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.education.controller.dto.ResponseEducationDTO;
import mx.edu.beecker.modules.hard_skills.controller.dto.ResponseHardSkillDTO;
import mx.edu.beecker.modules.languages.controller.dto.ResponseLanguageDTO;
import mx.edu.beecker.modules.professional_information.model.BeanProfessionalInformation;
import mx.edu.beecker.modules.resume.controller.dto.ResponseResumeDTO;
import mx.edu.beecker.modules.social_media_profiles.controller.dto.ResponseSocialMediaProfileDTO;
import mx.edu.beecker.modules.soft_skills.controller.dto.ResponseSoftSkillDTO;
import mx.edu.beecker.modules.work_experience.controller.dto.ResponseWorkExperienceDTO;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProfessionalInformationDTO {
    private List<ResponseWorkExperienceDTO> workExperiences;
    private List<ResponseLanguageDTO> languages;
    private List<ResponseEducationDTO> educations;
    private List<ResponseHardSkillDTO> hardSkills;
    private List<ResponseSoftSkillDTO> softSkills;
    private ResponseResumeDTO resume;
    private List<ResponseSocialMediaProfileDTO> socialMediaProfiles;

    public ResponseProfessionalInformationDTO(BeanProfessionalInformation professionalInformation) {
        this.workExperiences = professionalInformation.getWorkExperiences().stream()
                .map(ResponseWorkExperienceDTO::new)
                .collect(Collectors.toList());
        this.languages = professionalInformation.getLanguages().stream()
                .map(ResponseLanguageDTO::new)
                .collect(Collectors.toList());
        this.educations = professionalInformation.getEducations().stream()
                .map(ResponseEducationDTO::new)
                .collect(Collectors.toList());
        this.hardSkills = professionalInformation.getHardSkills().stream()
                .map(ResponseHardSkillDTO::new)
                .collect(Collectors.toList());
        this.softSkills = professionalInformation.getSoftSkills().stream()
                .map(ResponseSoftSkillDTO::new)
                .collect(Collectors.toList());
        this.resume = professionalInformation.getResume() != null ? new ResponseResumeDTO(professionalInformation.getResume()) : null;
        this.socialMediaProfiles = professionalInformation.getSocialMediaProfiles().stream()
                .map(ResponseSocialMediaProfileDTO::new)
                .collect(Collectors.toList());
    }
}
