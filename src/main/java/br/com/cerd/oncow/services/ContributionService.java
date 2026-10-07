package br.com.cerd.oncow.services;

import br.com.cerd.oncow.dtos.ContributionCreatedDto;
import br.com.cerd.oncow.dtos.ContributionListDto;
import br.com.cerd.oncow.dtos.ContributionRequestDto;
import br.com.cerd.oncow.dtos.ContributionResponseDto;
import br.com.cerd.oncow.enums.ContributionStatus;
import br.com.cerd.oncow.exceptions.ContributionNotFoundException;
import br.com.cerd.oncow.models.ModelContribution;
import br.com.cerd.oncow.repositories.ContributionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContributionService {

    private final ContributionRepository contributionRepository;

    public ContributionService(ContributionRepository contributionRepository) {
        this.contributionRepository = contributionRepository;
    }

/** Salvar todos **/
    public ContributionCreatedDto criarContribution(ContributionRequestDto contributionDto){
        // 1. Converte o DTO recebido para a Entidade
        var modelContribution = new ModelContribution();
        BeanUtils.copyProperties(contributionDto, modelContribution);

        // define o status
        modelContribution.setStatus(ContributionStatus.PENDING);

        // criar
        modelContribution.setCreatedAt(LocalDateTime.now());

        // 2. Salva a Entidade no banco e guarda o resultado retornado
        var entidadeSalva = contributionRepository.save(modelContribution);

        return new ContributionCreatedDto(
                entidadeSalva.getName(),
                entidadeSalva.getAmount(),
                entidadeSalva.getStatus(),
                entidadeSalva.getCreatedAt()
        );
    }

 /** Buscar todos **/
    public List<ContributionListDto> buscarContribution(){
           List<ModelContribution> entidades = contributionRepository.findAll();

           return entidades.stream()
                   .map(entidade -> new ContributionListDto(
                           entidade.getName(),
                           entidade.getAmount()
                   ))
                   .collect(Collectors.toList());
    }

 /** Buscar por ID **/
   public ContributionResponseDto buscarContributionPorId(Long id){
       var entidade = contributionRepository.findById(id)
               .orElseThrow(() -> new ContributionNotFoundException(
                       "Contribuição não encontrada com o ID: " + id
               ));
       return new ContributionResponseDto(
               entidade.getId(),
               entidade.getName(),
               entidade.getAmount(),
               entidade.getPaymentId()
       );
   }

    /** Processar pagamento recebido pelo webhook **/
    public void processarPagamentoRecebido(String paymentId){
        var contribution = contributionRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new ContributionNotFoundException(
                        "Contribuição não encontrada para o paymentId: " + paymentId
                ));
        // Idempotência: se já foi pago, não processa novamente
        if (contribution.getStatus() == ContributionStatus.PAID) {
            return;
        }

        contribution.setStatus(ContributionStatus.PAID);
        contribution.setPaidAt(LocalDateTime.now());

        contributionRepository.save(contribution);
    }
}
