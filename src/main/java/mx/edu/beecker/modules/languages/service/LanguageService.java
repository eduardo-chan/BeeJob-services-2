package mx.edu.beecker.modules.languages.service;

import mx.edu.beecker.modules.education.controller.dto.RequestBaseForDeleteAndGetOneDTO;
import mx.edu.beecker.modules.hard_skills.controller.dto.ResponseHardSkillDTO;
import mx.edu.beecker.modules.languages.controller.dto.RequestLanguageDTO;
import mx.edu.beecker.modules.languages.controller.dto.RequestUpdateLanguageDTO;
import mx.edu.beecker.modules.languages.controller.dto.ResponseLanguageDTO;
import mx.edu.beecker.modules.languages.model.BeanLanguage;
import mx.edu.beecker.modules.languages.model.ILanguage;
import mx.edu.beecker.modules.professional_information.model.BeanProfessionalInformation;
import mx.edu.beecker.modules.professional_information.model.IProfessionalInformation;
import mx.edu.beecker.modules.users.model.BeanUser;
import mx.edu.beecker.modules.users.model.IUser;
import mx.edu.beecker.utils.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LanguageService {
    private final ILanguage languageRepository;
    private final IProfessionalInformation professionalInformationRepository;
    private final IUser userRepository;

    public LanguageService(ILanguage languageRepository, IProfessionalInformation professionalInformationRepository, IUser userRepository) {
        this.languageRepository = languageRepository;
        this.professionalInformationRepository = professionalInformationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public BeanLanguage addLanguage(RequestLanguageDTO languageDTO) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));


        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseGet(() -> {
                    BeanProfessionalInformation newProfessionalInfo = new BeanProfessionalInformation();
                    newProfessionalInfo.setUser(user);
                    return professionalInformationRepository.save(newProfessionalInfo);
                });

        BeanLanguage language = new BeanLanguage();
        language.setLanguage(languageDTO.getLanguage());
        language.setLevel(languageDTO.getLevel());
        language.setProfessionalInformation(professionalInfo);

        return languageRepository.save(language);
    }

    @Transactional
    public ResponseLanguageDTO updateLanguage(RequestUpdateLanguageDTO requestDTO) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));

        BeanLanguage language = languageRepository.findById(requestDTO.getId())
                .orElseThrow(() -> new CustomException("Language not found", HttpStatus.NOT_FOUND));

        if (!language.getProfessionalInformation().equals(professionalInfo)) {
            throw new CustomException("You do not have permission to update this language", HttpStatus.FORBIDDEN);
        }

        language.setLanguage(requestDTO.getLanguage());
        language.setLevel(requestDTO.getLevel());

        languageRepository.save(language);
        return new ResponseLanguageDTO(language);
    }

    @Transactional
    public void deleteLanguage(Long id) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));

        BeanLanguage language = languageRepository.findById(id)
                .orElseThrow(() -> new CustomException("Language not found", HttpStatus.NOT_FOUND));

        if (!language.getProfessionalInformation().equals(professionalInfo)) {
            throw new CustomException("You do not have permission to delete this language", HttpStatus.FORBIDDEN);
        }

        languageRepository.delete(language);

        professionalInfo.getLanguages().remove(language);
    }

    public List<ResponseLanguageDTO> getAllLanguages() {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        Optional<BeanProfessionalInformation> professionalInfoOpt = professionalInformationRepository.findByUser(user);

        if (professionalInfoOpt.isEmpty()) {
            return Collections.emptyList();
        }

        return professionalInfoOpt.get().getLanguages().stream()
                .map(ResponseLanguageDTO::new)
                .collect(Collectors.toList());

    }

    public ResponseLanguageDTO getLanguageById(RequestBaseForDeleteAndGetOneDTO requestDTO) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));

        BeanLanguage language = languageRepository.findById(requestDTO.getId())
                .orElseThrow(() -> new CustomException("Language not found", HttpStatus.NOT_FOUND));

        if (!language.getProfessionalInformation().equals(professionalInfo)) {
            throw new CustomException("You do not have permission to view this language", HttpStatus.FORBIDDEN);
        }

        return new ResponseLanguageDTO(language);
    }



}
