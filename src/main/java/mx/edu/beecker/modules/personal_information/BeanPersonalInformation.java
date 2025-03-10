package mx.edu.beecker.modules.personal_information;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.users.model.BeanUser;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "personal_information")
public class BeanPersonalInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_information_id", updatable = false, nullable = false)
    private Long personalInformationId;

    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "first_last_name", nullable = false)
    private String firstLastName;

    @Column(name = "second_last_name")
    private String secondLastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "adress_state")
    private String adressState;

    @Column(name = "adress_country")
    private String adressCountry;

    // one-to-one relationship with users
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private BeanUser user;
}