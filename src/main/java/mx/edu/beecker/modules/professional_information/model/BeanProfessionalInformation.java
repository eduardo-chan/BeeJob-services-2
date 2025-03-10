package mx.edu.beecker.modules.professional_information.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.education.model.BeanEducation;
import mx.edu.beecker.modules.hard_skills.model.BeanHardSkill;
import mx.edu.beecker.modules.languages.model.BeanLanguage;
import mx.edu.beecker.modules.resume.model.BeanResume;
import mx.edu.beecker.modules.social_media_profiles.model.BeanSocialMediaProfile;
import mx.edu.beecker.modules.soft_skills.model.BeanSoftSkill;
import mx.edu.beecker.modules.users.model.BeanUser;
import mx.edu.beecker.modules.work_experience.model.BeanWorkExperience;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "professional_information")
public class BeanProfessionalInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professional_information_id", updatable = false, nullable = false)
    private Long professionalInformationId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private BeanUser user;

    @OneToMany(mappedBy = "professionalInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BeanWorkExperience> workExperiences;

    @OneToMany(mappedBy = "professionalInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BeanLanguage> languages;

    @OneToMany(mappedBy = "professionalInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BeanEducation> educations;

    @OneToMany(mappedBy = "professionalInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BeanHardSkill> hardSkills;

    @OneToMany(mappedBy = "professionalInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BeanSoftSkill> softSkills;

    @OneToOne(mappedBy = "professionalInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private BeanResume resume;

    @OneToMany(mappedBy = "professionalInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BeanSocialMediaProfile> socialMediaProfiles;

}
