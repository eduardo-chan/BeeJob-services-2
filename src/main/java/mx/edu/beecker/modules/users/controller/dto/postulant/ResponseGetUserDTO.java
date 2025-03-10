package mx.edu.beecker.modules.users.controller.dto.postulant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.users.model.BeanUser;

import java.util.Base64;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseGetUserDTO {
    private Long userId;
    private String email;
    private String name;
    private String image;
    private String firstLastName;
    private String secondLastName;
    private String phoneNumber;
    private String adressState;
    private String adressCountry;


    public ResponseGetUserDTO (BeanUser user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.name = user.getPersonalInformation().getName();
        byte[] imageBytes = user.getPersonalInformation().getImage();
        if (imageBytes != null) {
            this.image = Base64.getEncoder().encodeToString(imageBytes);
        }
        this.firstLastName = user.getPersonalInformation().getFirstLastName();
        this.secondLastName = user.getPersonalInformation().getSecondLastName();
        this.phoneNumber = user.getPersonalInformation().getPhoneNumber();
        this.adressState = user.getPersonalInformation().getAdressState();
        this.adressCountry = user.getPersonalInformation().getAdressCountry();
    }
}
