package mx.edu.beecker.modules.work_experience.controller;

import jakarta.validation.Valid;
import mx.edu.beecker.modules.education.controller.dto.RequestBaseForDeleteAndGetOneDTO;
import mx.edu.beecker.modules.work_experience.controller.dto.RequestUpdateWorkExperienceDTO;
import mx.edu.beecker.modules.work_experience.controller.dto.RequestWorkExperienceDTO;
import mx.edu.beecker.modules.work_experience.controller.dto.ResponseWorkExperienceDTO;
import mx.edu.beecker.modules.work_experience.model.BeanWorkExperience;
import mx.edu.beecker.modules.work_experience.service.WorkExperienceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/beejob/workexperience")
public class WorkExperienceController {

    private final WorkExperienceService workExperienceService;

    public WorkExperienceController(WorkExperienceService workExperienceService) {
        this.workExperienceService = workExperienceService;
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @PostMapping("/add")
    public ResponseEntity<ResponseWorkExperienceDTO> addWorkExperience(@Valid @RequestBody RequestWorkExperienceDTO workExperienceDTO) {
        BeanWorkExperience workExperience = workExperienceService.addWorkExperience(workExperienceDTO);
        return ResponseEntity.ok(new ResponseWorkExperienceDTO(workExperience));
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @PutMapping("/update")
    public ResponseEntity<ResponseWorkExperienceDTO> updateWorkExperience(@Valid @RequestBody RequestUpdateWorkExperienceDTO requestDTO) {
        return ResponseEntity.ok(workExperienceService.updateWorkExperience(requestDTO));
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteWorkExperience(@Valid @RequestBody RequestBaseForDeleteAndGetOneDTO requestDTO) {
        workExperienceService.deleteWorkExperience(requestDTO.getId());
        return ResponseEntity.ok("Work experience deleted successfully");
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @GetMapping("/get-all")
    public ResponseEntity<List<ResponseWorkExperienceDTO>> getAllWorkExperiences() {
        List<ResponseWorkExperienceDTO> workExperiences = workExperienceService.getAllWorkExperiences();
        return ResponseEntity.ok(workExperiences);
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @GetMapping("/get-one")
    public ResponseEntity<ResponseWorkExperienceDTO> getWorkExperienceById(@Valid @RequestBody RequestBaseForDeleteAndGetOneDTO requestDTO) {
        return ResponseEntity.ok(workExperienceService.getWorkExperienceById(requestDTO));
    }
}
