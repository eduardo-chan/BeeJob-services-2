package mx.edu.beecker.modules.resume.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.resume.model.BeanResume;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestResumeDTO {
    @NotNull(message = "PDF file cannot be null")
    private MultipartFile pdf;

}
