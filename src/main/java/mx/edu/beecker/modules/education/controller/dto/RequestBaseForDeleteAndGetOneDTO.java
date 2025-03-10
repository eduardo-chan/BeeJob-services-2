package mx.edu.beecker.modules.education.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestBaseForDeleteAndGetOneDTO {
    @NotNull(message = "id is required")
    private Long id;
}
