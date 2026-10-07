package br.com.cerd.oncow.controllers;

import br.com.cerd.oncow.dtos.ContributionCreatedDto;
import br.com.cerd.oncow.dtos.ContributionListDto;
import br.com.cerd.oncow.dtos.ContributionRequestDto;
import br.com.cerd.oncow.dtos.ContributionResponseDto;
import br.com.cerd.oncow.services.ContributionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin( "*" )
@RestController
@RequestMapping("/contributions")

public class ContributionController {

    private final ContributionService contributionService;

    public ContributionController(ContributionService contributionService) {
        this.contributionService = contributionService;
    }

    @PostMapping("/createded")
    public ResponseEntity<ContributionCreatedDto> criar(@RequestBody @Valid ContributionRequestDto requestDto){
        ContributionCreatedDto contributionDto = contributionService.criarContribution(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(contributionDto);
    }

    @GetMapping()
    public ResponseEntity<List<ContributionListDto>> listar(){
        List<ContributionListDto> contribution = contributionService.buscarContribution();
        return ResponseEntity.ok(contribution);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContributionResponseDto> buscarPorId(@PathVariable Long id){
        ContributionResponseDto contribution = contributionService.buscarContributionPorId(id);
        return ResponseEntity.ok(contribution);
    }

}
