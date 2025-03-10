package mx.edu.beecker.modules.professional_information.model;

import mx.edu.beecker.modules.users.model.BeanUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProfessionalInformation extends JpaRepository<BeanProfessionalInformation, Long> {
    Optional<BeanProfessionalInformation> findByUser(BeanUser user);
}
