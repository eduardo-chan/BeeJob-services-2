package mx.edu.beecker.modules.postulation.controller;

import jakarta.validation.Valid;
import mx.edu.beecker.modules.postulation.controller.dto.*;
import mx.edu.beecker.modules.postulation.service.PostulationService;
import mx.edu.beecker.modules.vacants.controller.dto.RequestGetPostulationDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/beejob/postulations")
public class PostulationController {

    private final PostulationService postulationService;

    public PostulationController (PostulationService postulationService) {
        this.postulationService = postulationService;

    }

    @PreAuthorize("hasRole('POSTULANT')")
    @PostMapping("/apply")
    public ResponseEntity<ResponsePostulationDTO> applyForVacant(@Valid @RequestBody RequestPostulationDTO requestDTO) {
        return ResponseEntity.ok(postulationService.applyForVacant(requestDTO));
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @PostMapping("/my-postulations")
    public ResponseEntity<Page<ResponseGetPostulationDTO>> getUserPostulations(@Valid @RequestBody RequestPaginationPostulationDTO filter) {
        return ResponseEntity.ok(postulationService.getUserPostulations(filter));
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @GetMapping("/my-postulation")
    public ResponseEntity<ResponseGetPostulationDTO> getUserPostulationById(@Valid @RequestBody RequestGetPostulationDTO request) {
        return ResponseEntity.ok(postulationService.getUserPostulationById(request));
    }

    @PreAuthorize("hasRole('ADMIN')") //referencia para mostrar solo la del admin en sesión?
    @PostMapping("/get-postulations")
    public ResponseEntity<Page<ResponseGetPostulationDTO>> getAllPostulations(@Valid @RequestBody RequestPaginationPostulationDTO filter) {
        return ResponseEntity.ok(postulationService.getAllPostulations(filter));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-postulation")
    public ResponseEntity<ResponseGetPostulationDTO> getPostulation(@Valid @RequestBody RequestGetPostulationDTO requestDTO) {
        return ResponseEntity.ok(postulationService.getPostulation(requestDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update-postulation-status")
    public ResponseEntity<ResponseGetPostulationDTO> updatePostulationStatus(@Valid @RequestBody RequestUpdatePostulationStatusDTO requestDTO) {
        return ResponseEntity.ok(postulationService.updatePostulationStatus(requestDTO));
    }





}
