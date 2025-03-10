package mx.edu.beecker.modules.users.controller.dto.postulant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.users.model.BeanUser;

import java.util.Base64;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseImageUserDTO {

    private Long userId;

    private String image;

    public ResponseImageUserDTO(BeanUser user) {

        this.userId = user.getUserId();

        byte[] imageBytes = user.getPersonalInformation().getImage();
        if (imageBytes != null) {
            this.image = Base64.getEncoder().encodeToString(imageBytes);
        }
    }
}
