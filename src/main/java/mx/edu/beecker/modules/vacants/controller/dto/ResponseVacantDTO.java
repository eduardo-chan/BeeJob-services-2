package mx.edu.beecker.modules.vacants.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.vacants.model.BeanVacant;
import mx.edu.beecker.modules.vacants.model.EArea;
import mx.edu.beecker.modules.vacants.model.ERelocationType;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseVacantDTO {

    private Long id;
    private String positionName;
    private EArea area;
    private String location;
    private boolean relocation;
    private ERelocationType relocationType;
    private String jobDescription;
    private int salary;

    //@JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;

   //@JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    private String requirements;
    private boolean status;
    private String aditionalInformation;

    public ResponseVacantDTO(BeanVacant vacant) {
        this.id = vacant.getId();
        this.positionName = vacant.getPositionName();
        this.area = vacant.getArea();
        this.location = vacant.getLocation();
        this.relocation = vacant.isRelocation();
        this.relocationType = vacant.getRelocationType();
        this.jobDescription = vacant.getJobDescription();
        this.salary = vacant.getSalary();
        this.publicationDate = vacant.getPublicationDate();
        this.deadline = vacant.getDeadline();
        this.requirements = vacant.getRequirements();
        this.status = vacant.isStatus();
        this.aditionalInformation = vacant.getAditionalInformation();
    }

}
