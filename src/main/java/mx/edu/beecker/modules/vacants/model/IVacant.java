package mx.edu.beecker.modules.vacants.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface IVacant extends JpaRepository<BeanVacant, Long> {
    @Query("SELECT v FROM BeanVacant v WHERE v.status = :status " +
            "AND (:search IS NULL OR LOWER(v.location) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(v.positionName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(v.area) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<BeanVacant> findByStatusAndFilters(
            @Param("status") Boolean status,
            @Param("search") String search,
            Pageable pageable
    );

    @Query("SELECT v FROM BeanVacant v WHERE v.status = :status " +
            "AND (:search IS NULL OR LOWER(v.location) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(v.positionName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(v.area) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<BeanVacant> findByStatusAndFiltersForAdmin(
            @Param("status") Boolean status,
            @Param("search") String search,
            Pageable pageable
    );








}
