package mx.edu.beecker.modules.social_media_profiles.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.professional_information.model.BeanProfessionalInformation;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "social_media_profiles")
public class BeanSocialMediaProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "platform_name", nullable = false)
    private String platformName;

    @Column(name = "profile_link", nullable = false)
    private String profileLink;

    //many to one to professional_information
    @ManyToOne
    @JoinColumn(name = "professional_information_id", nullable = false)
    private BeanProfessionalInformation professionalInformation;
}
