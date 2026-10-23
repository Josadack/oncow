package br.com.cerd.oncow.services;

import br.com.cerd.oncow.dtos.ContributionCreatedDto;
import br.com.cerd.oncow.dtos.ContributionListDto;
import br.com.cerd.oncow.dtos.ContributionRequestDto;
import br.com.cerd.oncow.dtos.ContributionResponseDto;
import br.com.cerd.oncow.dtos.asaas.AsaasCreatePaymentRequest;
import br.com.cerd.oncow.enums.ContributionStatus;
import br.com.cerd.oncow.exceptions.ContributionNotFoundException;
import br.com.cerd.oncow.models.ModelContribution;
import br.com.cerd.oncow.repositories.ContributionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContributionService {

    @Value("${asaas.customer.id}")
    private String asaasCustomerId;

    private final ContributionRepository contributionRepository;
    private final AsaasService asaasService;

    public ContributionService(ContributionRepository contributionRepository, AsaasService asaasService) {
        this.contributionRepository = contributionRepository;
        this.asaasService = asaasService;
    }

/** Salvar todos **/
public ContributionCreatedDto criarContribution(
        ContributionRequestDto contributionDto
) {
    var modelContribution = new ModelContribution();
    BeanUtils.copyProperties(contributionDto, modelContribution);

    modelContribution.setStatus(ContributionStatus.PENDING);
    modelContribution.setCreatedAt(LocalDateTime.now());

    // Salva a contribuição para obter o ID
    var entidadeSalva = contributionRepository.save(modelContribution);

    // Cria a cobrança PIX no Asaas
    var requisicaoPagamento = new AsaasCreatePaymentRequest(
            asaasCustomerId,
            "PIX",
            entidadeSalva.getAmount(),
            LocalDate.now().plusDays(1)
    );

    var pagamento = asaasService.criarCobrancaPix(requisicaoPagamento);

    // Salva o ID do pagamento retornado pelo Asaas
    entidadeSalva.setPaymentId(pagamento.id());
    entidadeSalva = contributionRepository.save(entidadeSalva);

    // Busca o QR Code e o código PIX copia e cola
    var qrCode = asaasService.buscarQrCodePix(pagamento.id());

    return new ContributionCreatedDto(
            entidadeSalva.getId(),
            entidadeSalva.getName(),
            entidadeSalva.getAmount(),
            entidadeSalva.getStatus(),
            entidadeSalva.getCreatedAt(),
            entidadeSalva.getPaymentId(),
            qrCode.payload(),
            qrCode.encodedImage(),
            qrCode.expirationDate()
    );
}

 /** Buscar todos **/
    public List<ContributionListDto> buscarContribution(){
           List<ModelContribution> entidades = contributionRepository.findAll();

           return entidades.stream()
                   .map(entidade -> new ContributionListDto(
                           entidade.getId(),
                           entidade.getName(),
                           entidade.getAmount(),
                           entidade.getStatus()
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
               entidade.getPaymentId(),
               entidade.getStatus()
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
