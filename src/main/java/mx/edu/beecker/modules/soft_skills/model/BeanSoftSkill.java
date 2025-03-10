package mx.edu.beecker.modules.soft_skills.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.professional_information.model.BeanProfessionalInformation;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "soft_skills")
public class BeanSoftSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "skill_name", nullable = false)
    private String skillName;

    //many to one to professional_information
    @ManyToOne
    @JoinColumn(name = "professional_information_id", nullable = false)
    private BeanProfessionalInformation professionalInformation;
}
