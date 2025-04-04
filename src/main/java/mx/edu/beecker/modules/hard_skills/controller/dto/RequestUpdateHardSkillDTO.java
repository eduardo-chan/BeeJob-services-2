package mx.edu.beecker.modules.hard_skills.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateHardSkillDTO {
    @NotNull(message = "id is required")
    private Long id;

    @NotBlank(message = "Skill name cannot be empty")
    private String skillName;
}
