package br.com.cerd.oncow.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ContributionListDto(@NotBlank String name,
                                  @NotNull BigDecimal amount) {
}
