package mx.edu.beecker.modules.users.controller;

import jakarta.validation.Valid;
import mx.edu.beecker.modules.users.controller.dto.*;
import mx.edu.beecker.modules.users.controller.dto.admin.PaginationRequestDTO;
import mx.edu.beecker.modules.users.controller.dto.admin.ResponseGetPostulantsDTO;
import mx.edu.beecker.modules.users.controller.dto.admin.UpdateUserStatusDTO;
import mx.edu.beecker.modules.users.controller.dto.postulant.*;
import mx.edu.beecker.modules.users.model.BeanUser;
import mx.edu.beecker.modules.users.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/beejob/users")

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/register-user")
    public ResponseEntity<ResponseBaseUserDTO> registerUser(
            @Validated(OnRegister.class) @RequestBody RequestUserDTO userDto) {

        BeanUser registeredUser = userService.registerUser(userDto);
        ResponseBaseUserDTO response = new ResponseBaseUserDTO(registeredUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @PutMapping("/update-user")
    public ResponseEntity<ResponseBaseUserDTO> updateUser(
            @Validated(OnUpdate.class) @RequestBody  RequestUserDTO userDto) {
        BeanUser updatedUser = userService.updateUser(userDto);
        ResponseBaseUserDTO response = new ResponseBaseUserDTO(updatedUser);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @PutMapping("/update-image")
    public ResponseEntity<ResponseImageUserDTO> updateUserImage(@ModelAttribute RequestUpdateImageDTO imageDTO) {
        ResponseImageUserDTO response = userService.updateUserImage(imageDTO.getImage());
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasRole('POSTULANT')")
    @DeleteMapping("/delete-profile-image")
    public ResponseEntity<String> deleteUserImage() {
        userService.deleteUserImage();
        return ResponseEntity.ok("Image deleted successfully");
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @GetMapping("/get-user-in-session")
    public ResponseEntity<ResponseGetUserDTO> getUser() {
        return ResponseEntity.ok(userService.getUser());
    }

    @PreAuthorize("hasRole('POSTULANT')")
    @PutMapping("/disable-user")
    public ResponseEntity<String> disableUser() {
        userService.disableUser();
        return ResponseEntity.ok("User account has been disabled successfully");
    }

    //for admin
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/get-all-postulants")
    public ResponseEntity<Page<ResponseGetPostulantsDTO>> getAllPostulants(@Valid @RequestBody PaginationRequestDTO request) {
        return ResponseEntity.ok(userService.getAllPostulants(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-postulant-status")
    public ResponseEntity<String> updatePostulantStatus(@Valid @RequestBody UpdateUserStatusDTO request) {
        userService.updateUserStatus(request);
        return ResponseEntity.ok("Postulant status updated successfully");
    }

}
