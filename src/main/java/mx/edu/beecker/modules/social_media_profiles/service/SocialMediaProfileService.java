package mx.edu.beecker.modules.social_media_profiles.service;

import mx.edu.beecker.modules.education.controller.dto.RequestBaseForDeleteAndGetOneDTO;
import mx.edu.beecker.modules.professional_information.model.BeanProfessionalInformation;
import mx.edu.beecker.modules.professional_information.model.IProfessionalInformation;
import mx.edu.beecker.modules.social_media_profiles.controller.dto.RequestSocialMediaProfileDTO;
import mx.edu.beecker.modules.social_media_profiles.controller.dto.RequestUpdateSocialMediaProfileDTO;
import mx.edu.beecker.modules.social_media_profiles.controller.dto.ResponseSocialMediaProfileDTO;
import mx.edu.beecker.modules.social_media_profiles.model.BeanSocialMediaProfile;
import mx.edu.beecker.modules.social_media_profiles.model.ISocialMediaProfile;
import mx.edu.beecker.modules.users.model.BeanUser;
import mx.edu.beecker.modules.users.model.IUser;
import mx.edu.beecker.utils.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SocialMediaProfileService {
    private final ISocialMediaProfile socialMediaProfileRepository;
    private final IProfessionalInformation professionalInformationRepository;
    private final IUser userRepository;

    public SocialMediaProfileService(ISocialMediaProfile socialMediaProfileRepository, IProfessionalInformation professionalInformationRepository, IUser userRepository) {
        this.socialMediaProfileRepository = socialMediaProfileRepository;
        this.professionalInformationRepository = professionalInformationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public BeanSocialMediaProfile addSocialMediaProfile(RequestSocialMediaProfileDTO profileDTO) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));


        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseGet(() -> {
                    BeanProfessionalInformation newProfessionalInfo = new BeanProfessionalInformation();
                    newProfessionalInfo.setUser(user);
                    return professionalInformationRepository.save(newProfessionalInfo);
                });


        BeanSocialMediaProfile profile = new BeanSocialMediaProfile();
        profile.setPlatformName(profileDTO.getPlatformName());
        profile.setProfileLink(profileDTO.getProfileLink());
        profile.setProfessionalInformation(professionalInfo);

        return socialMediaProfileRepository.save(profile);
    }

    @Transactional
    public ResponseSocialMediaProfileDTO updateSocialMediaProfile(RequestUpdateSocialMediaProfileDTO requestDTO) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));


        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));

        BeanSocialMediaProfile socialMediaProfile = socialMediaProfileRepository.findById(requestDTO.getId())
                .orElseThrow(() -> new CustomException("Social media profile not found", HttpStatus.NOT_FOUND));

        if (!socialMediaProfile.getProfessionalInformation().equals(professionalInfo)) {
            throw new CustomException("You do not have permission to update this social media profile", HttpStatus.FORBIDDEN);
        }

        socialMediaProfile.setPlatformName(requestDTO.getPlatformName());
        socialMediaProfile.setProfileLink(requestDTO.getProfileLink());

        socialMediaProfileRepository.save(socialMediaProfile);
        return new ResponseSocialMediaProfileDTO(socialMediaProfile);
    }

    @Transactional
    public void deleteSocialMediaProfile(Long id) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));


        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));

        BeanSocialMediaProfile socialMediaProfile = socialMediaProfileRepository.findById(id)
                .orElseThrow(() -> new CustomException("Social media profile not found", HttpStatus.NOT_FOUND));

        if (!socialMediaProfile.getProfessionalInformation().equals(professionalInfo)) {
            throw new CustomException("You do not have permission to delete this social media profile", HttpStatus.FORBIDDEN);
        }

        socialMediaProfileRepository.delete(socialMediaProfile);

        professionalInfo.getSocialMediaProfiles().remove(socialMediaProfile);
    }

    public List<ResponseSocialMediaProfileDTO> getAllSocialMediaProfiles() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));

        return professionalInfo.getSocialMediaProfiles().stream()
                .map(profile -> new ResponseSocialMediaProfileDTO(profile))
                .collect(Collectors.toList());
    }

    public ResponseSocialMediaProfileDTO getSocialMediaProfileById(RequestBaseForDeleteAndGetOneDTO requestDTO) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));

        BeanSocialMediaProfile socialMediaProfile = socialMediaProfileRepository.findById(requestDTO.getId())
                .orElseThrow(() -> new CustomException("Social media profile not found", HttpStatus.NOT_FOUND));

        if (!socialMediaProfile.getProfessionalInformation().equals(professionalInfo)) {
            throw new CustomException("You do not have permission to view this social media profile", HttpStatus.FORBIDDEN);
        }

        return new ResponseSocialMediaProfileDTO(socialMediaProfile);
    }

}
