package mx.edu.beecker.modules.social_media_profiles.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestSocialMediaProfileDTO {
    @NotBlank(message = "Platform name cannot be empty")
    private String platformName;

    @NotBlank(message = "Profile link cannot be empty")
    private String profileLink;
}
