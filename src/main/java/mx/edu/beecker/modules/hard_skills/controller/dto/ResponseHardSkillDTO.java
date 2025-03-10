package mx.edu.beecker.modules.hard_skills.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.hard_skills.model.BeanHardSkill;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseHardSkillDTO {
    private Long id;
    private String skillName;

    public ResponseHardSkillDTO(BeanHardSkill hardSkill) {
        this.id = hardSkill.getId();
        this.skillName = hardSkill.getSkillName();
    }
}
