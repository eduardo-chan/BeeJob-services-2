package mx.edu.beecker.modules.education.controller;

import jakarta.validation.Valid;
import mx.edu.beecker.modules.education.controller.dto.RequestBaseForDeleteAndGetOneDTO;
import mx.edu.beecker.modules.education.controller.dto.RequestEducationDTO;
import mx.edu.beecker.modules.education.controller.dto.RequestUpdateEducationDTO;
import mx.edu.beecker.modules.education.controller.dto.ResponseEducationDTO;
import mx.edu.beecker.modules.education.model.BeanEducation;
import mx.edu.beecker.modules.education.service.EducationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/beejob/education")
public class EducationController {

    private final EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @PostMapping("/add")
    public ResponseEntity<ResponseEducationDTO> addEducation(@Valid @RequestBody RequestEducationDTO educationDTO) {
        BeanEducation education = educationService.addEducation(educationDTO);
        return ResponseEntity.ok(new ResponseEducationDTO(education));
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @PutMapping("/update")
    public ResponseEntity<ResponseEducationDTO> updateEducation(@Valid @RequestBody RequestUpdateEducationDTO requestDTO) {
        return ResponseEntity.ok(educationService.updateEducation(requestDTO));
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteWorkExperience(@Valid @RequestBody RequestBaseForDeleteAndGetOneDTO requestDTO) {
        educationService.deleteEducation(requestDTO.getId());
        return ResponseEntity.ok("Education record deleted successfully");
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @GetMapping("/get-all")
    public ResponseEntity<List<ResponseEducationDTO>> getAllEducations() {
        List<ResponseEducationDTO> educations = educationService.getAllEducations();
        return ResponseEntity.ok(educations);
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @GetMapping("/get-one")
    public ResponseEntity<ResponseEducationDTO> getEducationById(@Valid @RequestBody RequestBaseForDeleteAndGetOneDTO requestDTO) {
        return ResponseEntity.ok(educationService.getEducationById(requestDTO));
    }
}
