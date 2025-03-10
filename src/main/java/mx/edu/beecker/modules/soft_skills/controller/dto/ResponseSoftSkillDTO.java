package mx.edu.beecker.modules.soft_skills.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.soft_skills.model.BeanSoftSkill;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSoftSkillDTO {
    private Long id;
    private String skillName;

    public ResponseSoftSkillDTO(BeanSoftSkill softSkill) {
        this.id = softSkill.getId();
        this.skillName = softSkill.getSkillName();
    }
}
