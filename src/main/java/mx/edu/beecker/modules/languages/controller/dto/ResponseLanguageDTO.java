package mx.edu.beecker.modules.languages.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.languages.model.BeanLanguage;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseLanguageDTO {
    private Long id;
    private String language;
    private String level;

    public ResponseLanguageDTO(BeanLanguage language) {
        this.id = language.getId();
        this.language = language.getLanguage();
        this.level = language.getLevel();
    }
}
