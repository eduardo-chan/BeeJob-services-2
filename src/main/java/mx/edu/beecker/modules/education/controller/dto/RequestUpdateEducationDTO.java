package mx.edu.beecker.modules.education.controller.dto;

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
public class RequestUpdateEducationDTO {

    @NotNull(message = "id is required")
    private Long id;

    @NotBlank(message = "Institution name is required")
    private String institution;

    @NotBlank(message = "Major is required")
    private String major;

    @NotBlank(message = "Degree is required")
    private String degree;

    @NotBlank(message = "School location is required")
    private String schoolLocation;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description must not be exceed {max} characters")
    private String description;

    @NotNull(message = "Start date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
