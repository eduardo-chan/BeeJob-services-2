package mx.edu.beecker.modules.work_experience.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.work_experience.model.BeanWorkExperience;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWorkExperienceDTO {
    private Long id;
    private String jobTitle;
    private String companyName;
    private String description;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;

    public ResponseWorkExperienceDTO(BeanWorkExperience workExperience) {
        this.id = workExperience.getId();
        this.jobTitle = workExperience.getJobTitle();
        this.companyName = workExperience.getCompanyName();
        this.description = workExperience.getDescription();
        this.location = workExperience.getLocation();
        this.startDate = workExperience.getStartDate();
        this.endDate = workExperience.getEndDate();
    }
}
