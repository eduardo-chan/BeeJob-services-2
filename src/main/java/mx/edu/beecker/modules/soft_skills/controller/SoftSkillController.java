package mx.edu.beecker.modules.soft_skills.controller;

import jakarta.validation.Valid;
import mx.edu.beecker.modules.education.controller.dto.RequestBaseForDeleteAndGetOneDTO;
import mx.edu.beecker.modules.soft_skills.controller.dto.RequestSoftSkillDTO;
import mx.edu.beecker.modules.soft_skills.controller.dto.RequestUpdateSoftSkillDTO;
import mx.edu.beecker.modules.soft_skills.controller.dto.ResponseSoftSkillDTO;
import mx.edu.beecker.modules.soft_skills.model.BeanSoftSkill;
import mx.edu.beecker.modules.soft_skills.service.SoftSkillService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/beejob/softskills")
public class SoftSkillController {
    private final SoftSkillService softSkillService;

    public SoftSkillController(SoftSkillService softSkillService) {
        this.softSkillService = softSkillService;
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @PostMapping("/add")
    public ResponseEntity<ResponseSoftSkillDTO> addSoftSkill(@Valid @RequestBody RequestSoftSkillDTO softSkillDTO) {
        BeanSoftSkill softSkill = softSkillService.addsoftSkill(softSkillDTO);
        return ResponseEntity.ok(new ResponseSoftSkillDTO(softSkill));
    }
    @PreAuthorize("hasRole('POSTULANT')")
    @PutMapping("/update")
    public ResponseEntity<ResponseSoftSkillDTO> updateSoftSkill(@Valid @RequestBody RequestUpdateSoftSkillDTO requestDTO) {
        return ResponseEntity.ok(softSkillService.updateSoftSkill(requestDTO));
    }
    @PreAuthorize("hasRole('POSTULANT')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSoftSkill(@Valid @RequestBody RequestBaseForDeleteAndGetOneDTO request) {
        softSkillService.deleteSoftSkill(request.getId());
        return ResponseEntity.ok("Soft skill deleted successfully");
    }
    @PreAuthorize("hasRole('POSTULANT')")
    @GetMapping("/get-all")
    public ResponseEntity<List<ResponseSoftSkillDTO>> getAllSoftSkills() {
        List<ResponseSoftSkillDTO> softSkills = softSkillService.getAllSoftSkills();
        return ResponseEntity.ok(softSkills);
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @GetMapping("/get-one")
    public ResponseEntity<ResponseSoftSkillDTO> getSoftSkillById(@Valid @RequestBody RequestBaseForDeleteAndGetOneDTO requestDTO) {
        return ResponseEntity.ok(softSkillService.getSoftSkillById(requestDTO));
    }
}
