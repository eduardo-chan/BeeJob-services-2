package mx.edu.beecker.modules.languages.controller;

import jakarta.validation.Valid;
import mx.edu.beecker.modules.education.controller.dto.RequestBaseForDeleteAndGetOneDTO;
import mx.edu.beecker.modules.languages.controller.dto.RequestLanguageDTO;
import mx.edu.beecker.modules.languages.controller.dto.RequestUpdateLanguageDTO;
import mx.edu.beecker.modules.languages.controller.dto.ResponseLanguageDTO;
import mx.edu.beecker.modules.languages.model.BeanLanguage;
import mx.edu.beecker.modules.languages.service.LanguageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/beejob/languages")
public class LanguageController {
    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @PostMapping("/add")
    public ResponseEntity<ResponseLanguageDTO> addLanguage(@Valid @RequestBody RequestLanguageDTO languageDTO) {
        BeanLanguage language = languageService.addLanguage(languageDTO);
        return ResponseEntity.ok(new ResponseLanguageDTO(language));
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @PutMapping("/update")
    public ResponseEntity<ResponseLanguageDTO> updateLanguage(@Valid @RequestBody RequestUpdateLanguageDTO requestDTO) {
        return ResponseEntity.ok(languageService.updateLanguage(requestDTO));
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteLanguage(@Valid @RequestBody RequestBaseForDeleteAndGetOneDTO requestDTO) {
        languageService.deleteLanguage(requestDTO.getId());
        return ResponseEntity.ok("Language deleted successfully");
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @GetMapping("/get-all")
    public ResponseEntity<List<ResponseLanguageDTO>> getAllLanguages() {
        return ResponseEntity.ok(languageService.getAllLanguages());
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @GetMapping("/get-one")
    public ResponseEntity<ResponseLanguageDTO> getLanguageById(@Valid @RequestBody RequestBaseForDeleteAndGetOneDTO requestDTO) {
        return ResponseEntity.ok(languageService.getLanguageById(requestDTO));
    }

}
