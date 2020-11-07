package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Treat;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Treat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TreatRepository extends JpaRepository<Treat, Long> {

    @Query("select treat from Treat treat where treat.user.login = ?#{principal.username}")
    List<Treat> findByUserIsCurrentUser();
}
