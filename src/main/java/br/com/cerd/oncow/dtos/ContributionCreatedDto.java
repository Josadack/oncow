package br.com.cerd.oncow.dtos;

import br.com.cerd.oncow.enums.ContributionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ContributionCreatedDto(
                                     @NotNull String name,
                                     @NotNull BigDecimal amount,
                                     @NotNull ContributionStatus status,
                                     @NotNull LocalDateTime createdAt) {
}
