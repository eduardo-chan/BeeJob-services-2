package mx.edu.beecker.modules.users.controller.dto.admin;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationRequestDTO {
    @NotNull(message = "Page number needed")
    @Min(0)
    private int page;

    @NotNull(message = "Size needed")
    @Min(1)
    private int size;


    private Boolean status;
    private String search;
    private String sortBy; //asc-desc
    public Sort.Direction getSortDirection() {
        return "DESC".equalsIgnoreCase(sortBy) ? Sort.Direction.DESC : Sort.Direction.ASC;
    }
}
