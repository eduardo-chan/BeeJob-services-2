package mx.edu.beecker.modules.resume.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.resume.model.BeanResume;

import java.util.Base64;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResumeDTO {
    private Long id;
    private String pdf;


    public ResponseResumeDTO (BeanResume resume) {
        this.id = resume.getId();
        byte[] pdfBytes = resume.getPdf();
        if (pdfBytes != null) {
            this.pdf = Base64.getEncoder().encodeToString(pdfBytes);
        }

    }
}
