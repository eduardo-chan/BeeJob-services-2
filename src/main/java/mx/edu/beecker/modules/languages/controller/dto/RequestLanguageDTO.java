package mx.edu.beecker.modules.languages.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestLanguageDTO {
    @NotBlank(message = "Language cannot be empty")
    private String language;

    @NotBlank(message = "Level cannot be empty")
    private String level;
}
