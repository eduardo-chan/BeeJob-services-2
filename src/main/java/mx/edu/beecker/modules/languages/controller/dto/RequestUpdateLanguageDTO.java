package mx.edu.beecker.modules.languages.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateLanguageDTO {

    @NotNull(message = "id is required")
    private Long id;

    @NotBlank(message = "Language cannot be empty")
    private String language;

    @NotBlank(message = "Level cannot be empty")
    private String level;
}
