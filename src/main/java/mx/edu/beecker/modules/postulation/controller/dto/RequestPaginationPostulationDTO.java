package mx.edu.beecker.modules.postulation.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.beecker.modules.postulation.model.EPostulationStatus;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPaginationPostulationDTO {
    @NotNull(message = "Page number needed")
    @Min(0)
    private int page;

    @NotNull(message = "Size needed")
    @Min(1)
    private int size;

    private EPostulationStatus status; // PENDING, ACCEPTED, REJECT

    private String sortBy; // asc - desc

    public Sort.Direction getSortDirection() {
        return "DESC".equalsIgnoreCase(sortBy) ? Sort.Direction.DESC : Sort.Direction.ASC;
    }


}
