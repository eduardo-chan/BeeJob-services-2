package mx.edu.beecker.modules.hard_skills.controller;

import jakarta.validation.Valid;
import mx.edu.beecker.modules.education.controller.dto.RequestBaseForDeleteAndGetOneDTO;
import mx.edu.beecker.modules.hard_skills.controller.dto.RequestHardSkillDTO;
import mx.edu.beecker.modules.hard_skills.controller.dto.RequestUpdateHardSkillDTO;
import mx.edu.beecker.modules.hard_skills.controller.dto.ResponseHardSkillDTO;
import mx.edu.beecker.modules.hard_skills.model.BeanHardSkill;
import mx.edu.beecker.modules.hard_skills.service.HardSkillService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/beejob/hardskills")
public class HardSkillController {
    private final HardSkillService hardSkillService;

    public HardSkillController(HardSkillService hardSkillService) {
        this.hardSkillService = hardSkillService;
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @PostMapping("/add")
    public ResponseEntity<ResponseHardSkillDTO> addHardSkill(@Valid @RequestBody RequestHardSkillDTO hardSkillDTO) {
        BeanHardSkill hardSkill = hardSkillService.addHardSkill(hardSkillDTO);
        return ResponseEntity.ok(new ResponseHardSkillDTO(hardSkill));
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @PutMapping("/update")
    public ResponseEntity<ResponseHardSkillDTO> updateHardSkill(@Valid @RequestBody RequestUpdateHardSkillDTO requestDTO) {
        return ResponseEntity.ok(hardSkillService.updateHardSkill(requestDTO));
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteHardSkill(@Valid @RequestBody RequestBaseForDeleteAndGetOneDTO request) {
        hardSkillService.deleteHardSkill(request.getId());
        return ResponseEntity.ok("Hard skill deleted successfully");
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @GetMapping("/get-all")
    public ResponseEntity<List<ResponseHardSkillDTO>> getAllHardSkills() {
        List<ResponseHardSkillDTO> hardSkills = hardSkillService.getAllHardSkills();
        return ResponseEntity.ok(hardSkills);
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @GetMapping("/get-one")
    public ResponseEntity<ResponseHardSkillDTO> getHardSkillById(@Valid @RequestBody RequestBaseForDeleteAndGetOneDTO requestDTO) {
        return ResponseEntity.ok(hardSkillService.getHardSkillById(requestDTO));
    }
}
