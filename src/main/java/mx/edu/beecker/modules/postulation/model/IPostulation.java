package mx.edu.beecker.modules.postulation.model;

import mx.edu.beecker.modules.users.model.BeanUser;
import mx.edu.beecker.modules.vacants.model.BeanVacant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IPostulation extends JpaRepository<BeanPostulation, Long> {
    boolean existsByUserAndVacant(BeanUser user, BeanVacant vacant);

    @Query("SELECT p FROM BeanPostulation p WHERE (:status IS NULL OR p.status = :status)")
    Page<BeanPostulation> findByStatus(
            @Param("status") EPostulationStatus status,
            Pageable pageable
    );

    @Query("SELECT p FROM BeanPostulation p WHERE p.user = :user " +
            "AND (:status IS NULL OR p.status = :status)")
    Page<BeanPostulation> findByUserAndStatus(
            @Param("user") BeanUser user,
            @Param("status") EPostulationStatus status,
            Pageable pageable);


    //for get one postulation by user in session
    @Query("SELECT p FROM BeanPostulation p WHERE p.id = :postulationId AND p.user = :user")
    Optional<BeanPostulation> findByIdAndUser(@Param("postulationId") Long postulationId, @Param("user") BeanUser user);







}
