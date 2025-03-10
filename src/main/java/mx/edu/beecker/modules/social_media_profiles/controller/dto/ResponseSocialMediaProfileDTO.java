package mx.edu.beecker.modules.social_media_profiles.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.social_media_profiles.model.BeanSocialMediaProfile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSocialMediaProfileDTO {
    private Long id;
    private String platformName;
    private String profileLink;

    public ResponseSocialMediaProfileDTO(BeanSocialMediaProfile profile) {
        this.id = profile.getId();
        this.platformName = profile.getPlatformName();
        this.profileLink = profile.getProfileLink();
    }
}
