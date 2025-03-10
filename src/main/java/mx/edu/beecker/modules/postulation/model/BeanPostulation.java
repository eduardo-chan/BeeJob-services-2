package mx.edu.beecker.modules.postulation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.professional_information.model.BeanProfessionalInformation;
import mx.edu.beecker.modules.users.model.BeanUser;
import mx.edu.beecker.modules.vacants.model.BeanVacant;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "postulations")
public class BeanPostulation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EPostulationStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private BeanUser user;

    @ManyToOne
    @JoinColumn(name = "vacant_id", nullable = false)
    private BeanVacant vacant;

    @ManyToOne
    @JoinColumn(name = "professional_information_id", nullable = false)
    private BeanProfessionalInformation professionalInformation;
}
