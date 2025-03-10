package mx.edu.beecker.modules.vacants.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.postulation.model.BeanPostulation;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "vacants")
public class BeanVacant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "position_name", nullable = false)
    private String positionName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EArea area;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "relocation", nullable = false)
    private boolean relocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "relocation_type")
    private ERelocationType relocationType;

    @Column(name = "job_description", nullable = false)
    private String jobDescription;

    @Column(name = "salary")
    private int salary;

    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;

    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;

    @Column(name = "requirements", nullable = false)
    private String requirements;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "aditional_information")
    private String aditionalInformation;

    @OneToMany(mappedBy = "vacant")
    private List<BeanPostulation> postulations;
}
