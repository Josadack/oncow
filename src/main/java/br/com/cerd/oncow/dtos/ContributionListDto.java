package br.com.cerd.oncow.dtos;

import br.com.cerd.oncow.enums.ContributionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ContributionListDto(
        Long id,
        @NotBlank String name,
        @NotBlank BigDecimal amount,
        ContributionStatus status) {
}
