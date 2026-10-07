package br.com.cerd.oncow.repositories;

import br.com.cerd.oncow.models.ModelContribution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContributionRepository extends JpaRepository<ModelContribution, Long> {
    Optional<ModelContribution> findByPaymentId(String paymentId);
}
