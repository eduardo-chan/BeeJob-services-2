package mx.edu.beecker.modules.resume.model;

import mx.edu.beecker.modules.professional_information.model.BeanProfessionalInformation;
import mx.edu.beecker.modules.resume.model.BeanResume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IResume extends JpaRepository<BeanResume, Long> {

    Optional<BeanResume> findByProfessionalInformation(BeanProfessionalInformation professionalInformation);
}
