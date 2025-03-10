package mx.edu.beecker.modules.users.controller.dto.postulant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.users.model.BeanUser;

import java.util.Base64;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBaseUserDTO {
    private Long userId;
    private String email;
    private String role;
    private boolean status;
    private String name;
    private String firstLastName;
    private String secondLastName;
    private String phoneNumber;
    private String adressState;
    private String adressCountry;

    public ResponseBaseUserDTO(BeanUser user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.role = user.getRole().name();
        this.status = user.isStatus();
        this.name = user.getPersonalInformation().getName();
        this.firstLastName = user.getPersonalInformation().getFirstLastName();
        this.secondLastName = user.getPersonalInformation().getSecondLastName();
        this.phoneNumber = user.getPersonalInformation().getPhoneNumber();
        this.adressState = user.getPersonalInformation().getAdressState();
        this.adressCountry = user.getPersonalInformation().getAdressCountry();
    }

}
