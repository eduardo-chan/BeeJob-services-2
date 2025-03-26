package mx.edu.beecker.modules.users.service;


import mx.edu.beecker.modules.personal_information.BeanPersonalInformation;
import mx.edu.beecker.modules.personal_information.IPersonalInformation;
import mx.edu.beecker.modules.users.controller.dto.admin.PaginationRequestDTO;
import mx.edu.beecker.modules.users.controller.dto.admin.UpdateUserStatusDTO;
import mx.edu.beecker.modules.users.controller.dto.postulant.RequestGetPostulantDTO;
import mx.edu.beecker.modules.users.controller.dto.postulant.RequestUserDTO;
import mx.edu.beecker.modules.users.controller.dto.admin.ResponseGetPostulantsDTO;
import mx.edu.beecker.modules.users.controller.dto.postulant.ResponseGetUserDTO;
import mx.edu.beecker.modules.users.controller.dto.postulant.ResponseImageUserDTO;
import mx.edu.beecker.modules.users.model.BeanUser;
import mx.edu.beecker.modules.users.model.ERole;
import mx.edu.beecker.modules.users.model.IUser;
import mx.edu.beecker.security.service.EmailService;
import mx.edu.beecker.utils.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@Transactional
public class UserService {

    private final IUser userRepository;
    private final IPersonalInformation personalInformationRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    public UserService(IUser userRepository, IPersonalInformation personalInformationRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.personalInformationRepository = personalInformationRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }
    private void updateField(String newValue, Consumer<String> setter) {
        if (newValue != null && !newValue.isEmpty()) {
            setter.accept(newValue);
        } else {
            setter.accept(null);
        }
    }

    //method for validate image
    private byte[] validateAndProcessImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new CustomException("Image file is required", HttpStatus.BAD_REQUEST);
        }
        String contentType = image.getContentType();
        if (!("image/jpeg".equals(contentType) || "image/png".equals(contentType))) {
            throw new CustomException("Only JPG and PNG formats are allowed", HttpStatus.BAD_REQUEST);
        }
        long maxSize = 2 * 1024 * 1024;
        if (image.getSize() > maxSize) {
            throw new CustomException("Image size must not exceed 2MB", HttpStatus.BAD_REQUEST);
        }
        try {
            return image.getBytes();
        } catch (IOException e) {
            throw new CustomException("Error processing image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //register user
    @Transactional
    public BeanUser registerUser(RequestUserDTO userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new CustomException("Email is already in use", HttpStatus.BAD_REQUEST);
        }

        //create BeanUser
        BeanUser user = new BeanUser();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(ERole.ADMIN);
        user.setStatus(true);

        //save the user first
        BeanUser savedUser = userRepository.save(user);

        //create BeanPersonalInformation
        BeanPersonalInformation personalInfo = new BeanPersonalInformation();
        personalInfo.setName(userDto.getName());
        personalInfo.setFirstLastName(userDto.getFirstLastName());
        personalInfo.setSecondLastName(userDto.getSecondLastName());
        personalInfo.setPhoneNumber(userDto.getPhoneNumber());

        //relationship
        savedUser.setPersonalInformation(personalInfo);
        personalInfo.setUser(savedUser);

        // save the personal information
        personalInformationRepository.save(personalInfo);

        return savedUser;
    }

    //update user
    @Transactional
    public BeanUser updateUser(RequestUserDTO userDto) {
        //get the authenticated user from the session
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        if (!userDto.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(userDto.getEmail())) {
                throw new CustomException("Email is already in use", HttpStatus.BAD_REQUEST);
            }
            user.setEmail(userDto.getEmail());
        }

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        //ensure that personal information exists
        if (user.getPersonalInformation() == null) {
            user.setPersonalInformation(new BeanPersonalInformation());
        }

        BeanPersonalInformation personalInfo = user.getPersonalInformation();

        personalInfo.setName(userDto.getName());
        personalInfo.setFirstLastName(userDto.getFirstLastName());
        updateField(userDto.getSecondLastName(), personalInfo::setSecondLastName);
        personalInfo.setPhoneNumber(userDto.getPhoneNumber());

        updateField(userDto.getAdressState(), personalInfo::setAdressState);
        updateField(userDto.getAdressCountry(), personalInfo::setAdressCountry);

        //save the changes in the database
        personalInformationRepository.save(personalInfo);
        userRepository.save(user);

        return user;

    }
    @Transactional
    public ResponseImageUserDTO updateUserImage(MultipartFile image) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        if (user.getPersonalInformation() == null) {
            user.setPersonalInformation(new BeanPersonalInformation());
        }

        byte[] newImage = validateAndProcessImage(image);
        user.getPersonalInformation().setImage(newImage);

        personalInformationRepository.save(user.getPersonalInformation());
        userRepository.save(user);

        return new ResponseImageUserDTO(user.getUserId(), Base64.getEncoder().encodeToString(newImage));
    }
    @Transactional
    public void deleteUserImage() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        if (user.getPersonalInformation() != null) {
            user.getPersonalInformation().setImage(null);
            personalInformationRepository.save(user.getPersonalInformation());
        }
    }

    //get one user
    @Transactional(readOnly = true)
    public ResponseGetUserDTO getUser() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        return new ResponseGetUserDTO(user);
    }

    @Transactional
    public void disableUser() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        if (!user.isStatus()) {
            throw new CustomException("User account is disabled", HttpStatus.BAD_REQUEST);
        }
        user.setStatus(false);
        userRepository.save(user);
    }


    //for admin
    @Transactional(readOnly = true)
    public Page<ResponseGetPostulantsDTO> getAllPostulants(PaginationRequestDTO filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), Sort.by(filter.getSortDirection(), "email"));

        Page<BeanUser> usersPage = userRepository.findByRoleAndFilters(
                ERole.POSTULANT,
                filter.getStatus(),
                filter.getSearch(),
                pageable
        );

        return usersPage.map(ResponseGetPostulantsDTO::new);
    }

    @Transactional
    public void updateUserStatus(UpdateUserStatusDTO request) {
        BeanUser user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        if (!user.getRole().equals(ERole.POSTULANT)) {
            throw new CustomException("Only postulants can be modified", HttpStatus.FORBIDDEN);
        }

        user.setStatus(request.isStatus());
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public ResponseGetPostulantsDTO getPostulantById(RequestGetPostulantDTO request) {
        BeanUser postulant = userRepository.findById(request.getId())
                .orElseThrow(() -> new CustomException("Postulant not found", HttpStatus.NOT_FOUND));

        if (postulant.getRole() != ERole.POSTULANT) {
            throw new CustomException("You can only view postulants", HttpStatus.FORBIDDEN);
        }

        return new ResponseGetPostulantsDTO(postulant);
    }


    public Optional<BeanUser> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //for email service
    public void generateVerificationCode(String email) {
        BeanUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        String code = generateRandomCode();
        user.setVerificationCode(code);
        user.setVerificationCodeExpiry(LocalDateTime.now().plusMinutes(15));

        userRepository.save(user);

        //we pass the username along with the email and code
        emailService.sendVerificationCode(user.getEmail(), user.getPersonalInformation().getName(), code);
    }


    private String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        return String.format("%06d", random.nextInt(999999));
    }

    public void resetPasswordWithCode(String email, String code, String newPassword) {
        BeanUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        //check if the code is valid
        if (user.getVerificationCode() == null || !user.getVerificationCode().equals(code)) {
            throw new CustomException("Invalid code", HttpStatus.BAD_REQUEST);
        }

        //check if the code has expired
        if (LocalDateTime.now().isAfter(user.getVerificationCodeExpiry())) {
            throw new CustomException("Expired code", HttpStatus.BAD_REQUEST);
        }

        //if everything is fine, the password is changed
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setVerificationCode(null); //we remove the code from the DB
        user.setVerificationCodeExpiry(null); //we remove the expiration date

        userRepository.save(user);
    }



}
