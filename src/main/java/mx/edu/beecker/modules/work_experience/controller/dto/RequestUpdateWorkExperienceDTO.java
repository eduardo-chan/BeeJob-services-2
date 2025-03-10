package mx.edu.beecker.modules.work_experience.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateWorkExperienceDTO {

    @NotNull(message = "id is required")
    private Long id;

    @NotBlank(message = "Job title cannot be empty")
    private String jobTitle;

    @NotBlank(message = "Company name cannot be empty")
    private String companyName;

    @NotBlank(message = "Description cannot be empty")
    @Size(max = 500, message = "Description must not be exceed {max} characters")
    private String description;

    @NotBlank(message = "Location cannot be empty")
    private String location;

    @NotNull(message = "Start date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
