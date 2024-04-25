package backendPFE.service;

import backendPFE.models.Matricule;
import backendPFE.repository.MatriculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatriculeService {
    private final MatriculeRepository matriculeRepository;

    @Autowired
    public MatriculeService(MatriculeRepository matriculeRepository) {
        this.matriculeRepository = matriculeRepository;
    }

    public List<Matricule> getAllMatricules() {
        return matriculeRepository.findAll();
    }

    public Matricule getMatriculeById(Integer id) {
        Optional<Matricule> matriculeOptional = matriculeRepository.findById(id);
        return matriculeOptional.orElse(null);
    }

    public Matricule addMatricule(String series, String location, String registrationNumber) {
        // Create a new Matricule object
        Matricule matricule = Matricule.builder()
                .series(series)
                .location(location)
                .registrationNumber(registrationNumber)
                .build();
        // Save the matricule to the database
        return matriculeRepository.save(matricule);
    }
}