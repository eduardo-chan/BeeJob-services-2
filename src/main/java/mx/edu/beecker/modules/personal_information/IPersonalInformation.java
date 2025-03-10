package mx.edu.beecker.modules.personal_information;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonalInformation  extends JpaRepository<BeanPersonalInformation, Long> {

}
