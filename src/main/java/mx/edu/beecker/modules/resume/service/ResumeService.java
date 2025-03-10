package mx.edu.beecker.modules.resume.service;

import mx.edu.beecker.modules.professional_information.model.BeanProfessionalInformation;
import mx.edu.beecker.modules.professional_information.model.IProfessionalInformation;
import mx.edu.beecker.modules.resume.model.IResume;
import mx.edu.beecker.modules.resume.controller.dto.RequestResumeDTO;
import mx.edu.beecker.modules.resume.model.BeanResume;
import mx.edu.beecker.modules.users.model.BeanUser;
import mx.edu.beecker.modules.users.model.IUser;
import mx.edu.beecker.utils.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
public class ResumeService {

    private final IUser userRepository;
    private final IProfessionalInformation professionalInformationRepository;
    private final IResume resumeRepository;

    public ResumeService(IUser userRepository, IProfessionalInformation professionalInformationRepository, IResume resumeRepository) {
        this.userRepository = userRepository;
        this.professionalInformationRepository = professionalInformationRepository;
        this.resumeRepository = resumeRepository;
    }


    private byte[] validateAndProcessPDF(MultipartFile pdf) {
        if (pdf == null || pdf.isEmpty()) {
            throw new CustomException("PDF file is required", HttpStatus.BAD_REQUEST);
        }
        String contentType = pdf.getContentType();
        if (!"application/pdf".equals(contentType)) {
            throw new CustomException("Only PDF format is allowed", HttpStatus.BAD_REQUEST);
        }
        long maxSize = 5 * 1024 * 1024;
        if (pdf.getSize() > maxSize) {
            throw new CustomException("PDF size must not exceed 5MB", HttpStatus.BAD_REQUEST);
        }
        try {
            return pdf.getBytes();
        } catch (IOException e) {
            throw new CustomException("Error processing PDF file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public BeanResume uploadPDF(RequestResumeDTO resumeDTO) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        BeanProfessionalInformation professionalInformation = professionalInformationRepository.findByUser(user)
                .orElseGet(() -> {

                    BeanProfessionalInformation newProfessionalInformation = new BeanProfessionalInformation();
                    newProfessionalInformation.setUser(user);
                    return professionalInformationRepository.save(newProfessionalInformation);
                });

        byte[] pdfBytes = validateAndProcessPDF(resumeDTO.getPdf());

        BeanResume resume = resumeRepository.findByProfessionalInformation(professionalInformation)
                .orElse(new BeanResume());

        resume.setPdf(pdfBytes);
        resume.setProfessionalInformation(professionalInformation);

        return resumeRepository.save(resume);
    }

    @Transactional
    public void deleteResume() {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        BeanUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));


        BeanProfessionalInformation professionalInfo = professionalInformationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Professional information not found", HttpStatus.NOT_FOUND));


        if (professionalInfo.getResume() == null) {
            throw new CustomException("No resume found to delete", HttpStatus.BAD_REQUEST);
        }


        professionalInfo.setResume(null);
        professionalInformationRepository.save(professionalInfo);
    }



}
