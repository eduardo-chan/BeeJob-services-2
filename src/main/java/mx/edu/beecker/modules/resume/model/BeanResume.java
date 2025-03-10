package mx.edu.beecker.modules.resume.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.professional_information.model.BeanProfessionalInformation;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "resume")
public class BeanResume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "pdf", columnDefinition = "LONGBLOB")
    private byte[] pdf;

    //many to one to professional_information
    @OneToOne
    @JoinColumn(name = "professional_information_id", nullable = false)
    private BeanProfessionalInformation professionalInformation;
}
