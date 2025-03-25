package mx.edu.beecker.modules.vacants.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.vacants.model.EArea;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestVacantDTO {

    @NotBlank(message = "Position name is required")
    @Size(max = 50, message = "Position name must not be exceed {max} characters")
    private String positionName;

    @NotNull(message = "Area is required")
    private EArea area;

    @NotBlank(message = "Location is required")
    private String location;

    private boolean relocation;

    @NotBlank(message = "Job description is required")
    @Size(max = 1500, message = "Job description must not be exceed {max} characters")
    private String jobDescription;

    @PositiveOrZero(message = "Salary must be 0 or a positive number")
    private int salary;

    @NotNull(message = "Deadline is required")
    @Future(message = "Deadline must be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    @NotBlank(message = "Requirements are required")
    @Size(max = 1500, message = "Requirements must not be exceed {max} characters")
    private String requirements;

    private boolean status;

    @Size(max = 1500, message = "Aditional information must not be exceed {max} characters")
    private String aditionalInformation;

}
