package mx.edu.beecker.modules.vacants.service;

import mx.edu.beecker.modules.users.model.IUser;
import mx.edu.beecker.modules.vacants.controller.dto.*;
import mx.edu.beecker.modules.vacants.model.BeanVacant;
import mx.edu.beecker.modules.vacants.model.IVacant;
import mx.edu.beecker.modules.work_experience.controller.dto.RequestVacantIdDTO;
import mx.edu.beecker.utils.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VacantService {

    private final IVacant vacantRepository;

    private final IUser userRepository;

    public VacantService (IVacant vacantRepository, IUser userRepository) {
        this.vacantRepository = vacantRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public ResponseVacantDTO createVacant(RequestVacantDTO requestDTO) {

        //create the `BeanVacant` entity with the received data
        BeanVacant vacant = new BeanVacant();
        vacant.setPositionName(requestDTO.getPositionName());
        vacant.setArea(requestDTO.getArea());
        vacant.setLocation(requestDTO.getLocation());
        vacant.setRelocation(requestDTO.isRelocation());
        vacant.setJobDescription(requestDTO.getJobDescription());
        vacant.setSalary(requestDTO.getSalary());
        vacant.setPublicationDate(LocalDate.now());
        vacant.setDeadline(requestDTO.getDeadline());
        vacant.setRequirements(requestDTO.getRequirements());
        vacant.setStatus(requestDTO.isStatus());
        vacant.setAditionalInformation(requestDTO.getAditionalInformation());

        //save in the database
        vacantRepository.save(vacant);

        //return the created vacancy in DTO format
        return new ResponseVacantDTO(vacant);
    }

    @Transactional
    public ResponseVacantDTO updateVacant(RequestUpdateVacantDTO requestDTO) {
        //if a vacancy has a status of false and the deadline has already passed,
        //its status cannot be modified or general modifications made

        //retrieve the vacant entity from the database
        BeanVacant vacant = vacantRepository.findById(requestDTO.getId())
                .orElseThrow(() -> new CustomException("Vacant not found", HttpStatus.NOT_FOUND));

        // Check if the vacant's status is false and the deadline has passed
        if (!vacant.isStatus() && vacant.getDeadline().isBefore(LocalDate.now())) {
            throw new CustomException("This vacant cannot be modified as its status is 'false' and the deadline has passed", HttpStatus.FORBIDDEN);
        }

        //update the vacant fields
        vacant.setPositionName(requestDTO.getPositionName());
        vacant.setArea(requestDTO.getArea());
        vacant.setLocation(requestDTO.getLocation());
        vacant.setRelocation(requestDTO.isRelocation());
        vacant.setJobDescription(requestDTO.getJobDescription());
        vacant.setSalary(requestDTO.getSalary());
        vacant.setDeadline(requestDTO.getDeadline());
        vacant.setRequirements(requestDTO.getRequirements());
        vacant.setAditionalInformation(requestDTO.getAditionalInformation());

        // Save the updated vacant
        vacantRepository.save(vacant);

        // Return the updated vacant
        return new ResponseVacantDTO(vacant);
    }

    @Transactional
    public ResponseStatusVacantDTO updateVacantStatus(RequestUpdateVacantStatusDTO requestDTO) {
        // search the vacant in the id
        BeanVacant vacant = vacantRepository.findById(requestDTO.getId())
                .orElseThrow(() -> new CustomException("Vacant not found", HttpStatus.NOT_FOUND));

        //if the vacancy is inactive and the deadline has passed, the status cannot be changed
        if (!vacant.isStatus() && vacant.getDeadline().isBefore(LocalDate.now())) {
            throw new CustomException("This vacant cannot be modified as its status is 'false' and the deadline has passed", HttpStatus.FORBIDDEN);
        }

        // update status
        vacant.setStatus(requestDTO.getStatus());
        vacantRepository.save(vacant);

        return new ResponseStatusVacantDTO(vacant);
    }



    @Transactional(readOnly = true)
    public ResponseVacantDTO getVacantById(RequestVacantIdDTO requestDTO) {
        // search the vacant in the database
        BeanVacant vacant = vacantRepository.findById(requestDTO.getId())
                .orElseThrow(() -> new CustomException("Vacant not found", HttpStatus.NOT_FOUND));

        return new ResponseVacantDTO(vacant);
    }

    @Transactional(readOnly = true)
    public Page<ResponseVacantDTO> getAllVacants(RequestPaginationVacantDTO filter) {
        //creating the Pageable for pagination and sorting
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), Sort.by(filter.getSortDirection(), "publicationDate"));

        //view vacancies filtered by status true and search
        Page<BeanVacant> vacantsPage = vacantRepository.findByStatusAndFilters(
                true,  // Solo vacantes con status true
                filter.getSearch(),
                pageable
        );
        return vacantsPage.map(ResponseVacantDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<ResponseVacantDTO> getAllVacantsForAdmin(RequestPaginationVacantAdminDTO filter) {

        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), Sort.by(filter.getSortDirection(), "publicationDate"));


        Page<BeanVacant> vacantsPage = vacantRepository.findByStatusAndFiltersForAdmin(
                filter.getStatus() != null ? filter.getStatus() : null,
                filter.getSearch(),
                pageable
        );
        return vacantsPage.map(ResponseVacantDTO::new);
    }



















}
