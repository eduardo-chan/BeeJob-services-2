package mx.edu.beecker.modules.social_media_profiles.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ISocialMediaProfile extends JpaRepository<BeanSocialMediaProfile, Long> {
}
