package mx.edu.beecker.modules.users.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.personal_information.BeanPersonalInformation;
import mx.edu.beecker.modules.postulation.model.BeanPostulation;
import mx.edu.beecker.modules.professional_information.model.BeanProfessionalInformation;

import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class BeanUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false)
    private Long userId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private ERole role;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "verification_code_expiry")
    private LocalDateTime verificationCodeExpiry;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private BeanPersonalInformation personalInformation;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private BeanProfessionalInformation professionalInformation;

    @OneToMany(mappedBy = "user")
    private List<BeanPostulation> postulations;

}
