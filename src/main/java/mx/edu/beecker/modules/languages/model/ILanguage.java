package mx.edu.beecker.modules.languages.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ILanguage extends JpaRepository<BeanLanguage, Long> {
}
