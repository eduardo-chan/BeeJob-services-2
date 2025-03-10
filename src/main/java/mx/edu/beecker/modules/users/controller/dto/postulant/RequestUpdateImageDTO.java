package mx.edu.beecker.modules.users.controller.dto.postulant;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateImageDTO {
    @NotNull(message = "Image cannot be null")
    private MultipartFile image;
}
