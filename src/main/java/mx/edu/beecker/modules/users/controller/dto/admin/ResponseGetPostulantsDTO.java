package mx.edu.beecker.modules.users.controller.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.users.model.BeanUser;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseGetPostulantsDTO {
    private Long userId;
    private String email;
    private String name;
    private String firstLastName;
    private String secondLastName;
    private String phoneNumber;
    private String adressState;
    private String adressCountry;
    private boolean status;


    public ResponseGetPostulantsDTO(BeanUser user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.name = user.getPersonalInformation().getName();
        this.firstLastName = user.getPersonalInformation().getFirstLastName();
        this.secondLastName = user.getPersonalInformation().getSecondLastName();
        this.phoneNumber = user.getPersonalInformation().getPhoneNumber();
        this.adressState = user.getPersonalInformation().getAdressState();
        this.adressCountry = user.getPersonalInformation().getAdressCountry();
        this.status = user.isStatus();
    }
}
