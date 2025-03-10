package mx.edu.beecker.modules.social_media_profiles.controller;

import jakarta.validation.Valid;
import mx.edu.beecker.modules.education.controller.dto.RequestBaseForDeleteAndGetOneDTO;
import mx.edu.beecker.modules.social_media_profiles.controller.dto.RequestSocialMediaProfileDTO;
import mx.edu.beecker.modules.social_media_profiles.controller.dto.RequestUpdateSocialMediaProfileDTO;
import mx.edu.beecker.modules.social_media_profiles.controller.dto.ResponseSocialMediaProfileDTO;
import mx.edu.beecker.modules.social_media_profiles.model.BeanSocialMediaProfile;
import mx.edu.beecker.modules.social_media_profiles.service.SocialMediaProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/beejob/social-media-profiles")
public class SocialMediaProfileController {
    private final SocialMediaProfileService socialMediaProfileService;

    public SocialMediaProfileController(SocialMediaProfileService socialMediaProfileService) {
        this.socialMediaProfileService = socialMediaProfileService;
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @PostMapping("/add")
    public ResponseEntity<ResponseSocialMediaProfileDTO> addSocialMediaProfile(@Valid @RequestBody RequestSocialMediaProfileDTO profileDTO) {
        BeanSocialMediaProfile profile = socialMediaProfileService.addSocialMediaProfile(profileDTO);
        return ResponseEntity.ok(new ResponseSocialMediaProfileDTO(profile));
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @PutMapping("/update")
    public ResponseEntity<ResponseSocialMediaProfileDTO> updateSocialMediaProfile(@Valid @RequestBody RequestUpdateSocialMediaProfileDTO requestDTO) {
        return ResponseEntity.ok(socialMediaProfileService.updateSocialMediaProfile(requestDTO));
    }
    @PreAuthorize("hasRole('POSTULANT')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSocialMediaProfile(@Valid @RequestBody RequestBaseForDeleteAndGetOneDTO request) {
        socialMediaProfileService.deleteSocialMediaProfile(request.getId());
        return ResponseEntity.ok("Social media profile deleted successfully");
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @GetMapping("/get-all")
    public ResponseEntity<List<ResponseSocialMediaProfileDTO>> getAllSocialMediaProfiles() {
        List<ResponseSocialMediaProfileDTO> socialMediaProfiles = socialMediaProfileService.getAllSocialMediaProfiles();
        return ResponseEntity.ok(socialMediaProfiles);
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @GetMapping("/get-one")
    public ResponseEntity<ResponseSocialMediaProfileDTO> getSocialMediaProfileById(@Valid @RequestBody RequestBaseForDeleteAndGetOneDTO requestDTO) {
        return ResponseEntity.ok(socialMediaProfileService.getSocialMediaProfileById(requestDTO));
    }
}
