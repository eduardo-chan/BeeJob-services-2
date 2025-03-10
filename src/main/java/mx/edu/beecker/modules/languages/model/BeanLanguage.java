package mx.edu.beecker.modules.languages.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.professional_information.model.BeanProfessionalInformation;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "languages")
public class BeanLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "level", nullable = false)
    private String level;

    //many to one to professional_information
    @ManyToOne
    @JoinColumn(name = "professional_information_id", nullable = false)
    private BeanProfessionalInformation professionalInformation;
}
