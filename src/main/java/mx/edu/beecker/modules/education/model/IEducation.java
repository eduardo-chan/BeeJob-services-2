package mx.edu.beecker.modules.education.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IEducation extends JpaRepository<BeanEducation, Long> {
}
