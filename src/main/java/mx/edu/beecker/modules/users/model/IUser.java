package mx.edu.beecker.modules.users.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.ModelAttribute;


import java.util.Optional;

public interface IUser extends JpaRepository<BeanUser, Long> {
    Optional<BeanUser> findByEmail(String email); //get a user by email

    boolean existsByEmail(String email); //check if an email already exists

    //for get users and filter postulants

    @Query("SELECT u FROM BeanUser u WHERE u.role = :role " +
            "AND (:status IS NULL OR u.status = :status) " +
            "AND (:search IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.personalInformation.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.personalInformation.firstLastName) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<BeanUser> findByRoleAndFilters(
            @Param("role") ERole role,
            @Param("status") Boolean status,
            @Param("search") String search,
            Pageable pageable
    );

    //for toggle status of postulants
    @Modifying
    @Query("UPDATE BeanUser u SET u.status = :status WHERE u.userId = :userId")
    void updateUserStatus(@Param("userId") Long userId, @Param("status") boolean status);


}
