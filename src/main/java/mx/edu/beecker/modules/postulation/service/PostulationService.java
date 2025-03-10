package mx.edu.beecker.modules.postulation.service;

import mx.edu.beecker.modules.postulation.controller.dto.*;
import mx.edu.beecker.modules.postulation.model.BeanPostulation;
import mx.edu.beecker.modules.postulation.model.EPostulationStatus;
import mx.edu.beecker.modules.postulation.model.IPostulation;
import mx.edu.beecker.modules.professional_information.model.BeanProfessionalInformation;
import mx.edu.beecker.modules.professional_information.model.IProfessionalInformation;
import mx.edu.beecker.modules.users.model.BeanUser;
import mx.edu.beecker.modules.users.model.IUser;
import mx.edu.beecker.modules.vacants.controller.dto.RequestGetPostulationDTO;
import mx.edu.beecker.modules.vacants.model.BeanVacant;
import mx.edu.beecker.modules.vacants.model.IVacant;
import mx.edu.beecker.utils.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional

public class PostulationService {

    private final IUser userRepository;
    private final IProfessionalInformation professionalInformationRepository;
    private final IVacant vacantRepository;
    private final IPostulation postulationRepository;

    public PostulationService (IUser userRepository,
                               IProfessionalInformation professionalInformationRepository,
                               IVacant vacantRepository, IPostulation postulationRepository) {
        this.userRepository = userRepository;
        this.professionalInformationRepository = professionalInformationRepository;
        this.vacantRepository = vacantRepository;
        this.postulationRepository = postulationRepository;
    }

    @Transactional
    public ResponsePostulationDTO applyForVacant(RequestPostulationDTO requestDTO) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        // Obtener información profesional del usuario
        BeanProfessionalInformation professionalInformation = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));

        // Obtener la vacante
        BeanVacant vacant = vacantRepository.findById(requestDTO.getId())
                .orElseThrow(() -> new CustomException("Vacant not found", HttpStatus.NOT_FOUND));

        // Validar si el usuario ya se postuló a esta vacante
        if (postulationRepository.existsByUserAndVacant(user, vacant)) {
            throw new CustomException("User has already applied for this vacant", HttpStatus.BAD_REQUEST);
        }

        // Crear postulación
        BeanPostulation postulation = new BeanPostulation();
        postulation.setUser(user);
        postulation.setVacant(vacant);
        postulation.setProfessionalInformation(professionalInformation);
        postulation.setApplicationDate(LocalDate.now());
        postulation.setStatus(EPostulationStatus.PENDING);

        // Guardar postulación
        postulationRepository.save(postulation);

        return new ResponsePostulationDTO(postulation);
    }

    @Transactional(readOnly = true)
    public Page<ResponseGetPostulationDTO> getUserPostulations(RequestPaginationPostulationDTO filter) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), Sort.by(filter.getSortDirection(), "applicationDate"));
        Page<BeanPostulation> postulationsPage = postulationRepository.findByUserAndStatus(
                user,
                filter.getStatus(),
                pageable
        );

        return postulationsPage.map(ResponseGetPostulationDTO::new);
    }

    @Transactional(readOnly = true)
    public ResponseGetPostulationDTO getUserPostulationById(RequestGetPostulationDTO request) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        BeanPostulation postulation = postulationRepository.findByIdAndUser(request.getId(), user)
                .orElseThrow(() -> new CustomException("Postulation not found or does not belong to the user", HttpStatus.NOT_FOUND));

        return new ResponseGetPostulationDTO(postulation);
    }
    @Transactional(readOnly = true)
    public Page<ResponseGetPostulationDTO> getAllPostulations(RequestPaginationPostulationDTO filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), Sort.by(filter.getSortDirection(), "applicationDate"));
        Page<BeanPostulation> postulationsPage = postulationRepository.findByStatus(
                filter.getStatus(),
                pageable
        );
        return postulationsPage.map(ResponseGetPostulationDTO::new);
    }

    @Transactional(readOnly = true)
    public ResponseGetPostulationDTO getPostulation(RequestGetPostulationDTO requestDTO) {
        BeanPostulation postulation = postulationRepository.findById(requestDTO.getId())
                .orElseThrow(() -> new CustomException("Postulation not found", HttpStatus.NOT_FOUND));

        return new ResponseGetPostulationDTO(postulation);
    }

    @Transactional
    public ResponseGetPostulationDTO updatePostulationStatus(RequestUpdatePostulationStatusDTO requestDTO) {

        BeanPostulation postulation = postulationRepository.findById(requestDTO.getPostulationId())
                .orElseThrow(() -> new CustomException("Postulation not found", HttpStatus.NOT_FOUND));

        postulation.setStatus(requestDTO.getStatus());

        postulationRepository.save(postulation);

        return new ResponseGetPostulationDTO(postulation);
    }















}
