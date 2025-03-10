package mx.edu.beecker.modules.soft_skills.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ISoftSkill extends JpaRepository<BeanSoftSkill, Long> {
}
