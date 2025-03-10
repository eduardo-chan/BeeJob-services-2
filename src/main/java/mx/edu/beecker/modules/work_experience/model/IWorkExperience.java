package mx.edu.beecker.modules.work_experience.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IWorkExperience extends JpaRepository<BeanWorkExperience, Long> {
}
