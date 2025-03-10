package mx.edu.beecker.modules.education.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.education.model.BeanEducation;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEducationDTO {
    private Long id;
    private String institution;
    private String major;
    private String degree;
    private String schoolLocation;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    public ResponseEducationDTO(BeanEducation education) {
        this.id = education.getId();
        this.institution = education.getInstitution();
        this.major = education.getMajor();
        this.degree = education.getDegree();
        this.schoolLocation = education.getSchoolLocation();
        this.description = education.getDescription();
        this.startDate = education.getStartDate();
        this.endDate = education.getEndDate();
    }
}
