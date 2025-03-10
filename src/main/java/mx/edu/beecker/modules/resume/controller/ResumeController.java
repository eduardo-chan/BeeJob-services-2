package mx.edu.beecker.modules.resume.controller;

import mx.edu.beecker.modules.resume.controller.dto.RequestResumeDTO;
import mx.edu.beecker.modules.resume.controller.dto.ResponseResumeDTO;
import mx.edu.beecker.modules.resume.model.BeanResume;
import mx.edu.beecker.modules.resume.service.ResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/beejob/resume")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @PostMapping("/upload")
    public ResponseEntity<ResponseResumeDTO> uploadResume(@ModelAttribute RequestResumeDTO resumeDTO) {

        BeanResume resume = resumeService.uploadPDF(resumeDTO);

        return ResponseEntity.ok(new ResponseResumeDTO(resume));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteResume() {
        resumeService.deleteResume();
        return ResponseEntity.ok("Resume deleted successfully");
    }
}
