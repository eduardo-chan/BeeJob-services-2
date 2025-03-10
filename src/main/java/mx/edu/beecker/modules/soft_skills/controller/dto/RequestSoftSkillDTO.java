package mx.edu.beecker.modules.soft_skills.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestSoftSkillDTO {
    @NotBlank(message = "Skill name cannot be empty")
    private String skillName;
}
