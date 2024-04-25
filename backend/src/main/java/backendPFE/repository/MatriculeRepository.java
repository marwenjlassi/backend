package backendPFE.repository;

import backendPFE.models.Matricule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatriculeRepository extends JpaRepository<Matricule,Integer> {
}
