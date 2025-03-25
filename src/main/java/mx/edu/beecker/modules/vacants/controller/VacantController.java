package mx.edu.beecker.modules.vacants.controller;

import jakarta.validation.Valid;
import mx.edu.beecker.modules.users.controller.dto.admin.PaginationRequestDTO;
import mx.edu.beecker.modules.vacants.controller.dto.*;
import mx.edu.beecker.modules.vacants.service.VacantService;
import mx.edu.beecker.modules.work_experience.controller.dto.RequestVacantIdDTO;
import mx.edu.beecker.utils.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/beejob/vacants")
public class VacantController {

    private final VacantService vacantService;

    public VacantController (VacantService vacantService) {
        this.vacantService = vacantService;

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ResponseVacantDTO> createVacant(@RequestBody @Valid RequestVacantDTO requestDTO) {
        ResponseVacantDTO createdVacant = vacantService.createVacant(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVacant);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<ResponseVacantDTO> updateVacant(@Valid @RequestBody RequestUpdateVacantDTO requestDTO) {
        return ResponseEntity.ok(vacantService.updateVacant(requestDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update-status")
    public ResponseEntity<ResponseStatusVacantDTO> updateVacantStatus(
            @Valid @RequestBody RequestUpdateVacantStatusDTO requestDTO) {
        return ResponseEntity.ok(vacantService.updateVacantStatus(requestDTO));
    }


    @GetMapping("/get-one")
    public ResponseEntity<ResponseVacantDTO> getVacantById(@RequestBody @Valid RequestVacantIdDTO requestDTO) {
        ResponseVacantDTO response = vacantService.getVacantById(requestDTO);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/get-all")
    public ResponseEntity<Page<ResponseVacantDTO>> getAllVacants(@Valid @RequestBody RequestPaginationVacantDTO filter) {
        Page<ResponseVacantDTO> response = vacantService.getAllVacants(filter);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/get-all-all")
    public ResponseEntity<Page<ResponseVacantDTO>> getAllVacants(@Valid @RequestBody RequestPaginationVacantAdminDTO filter) {
        Page<ResponseVacantDTO> response = vacantService.getAllVacantsForAdmin(filter);
        return ResponseEntity.ok(response);
    }










}
