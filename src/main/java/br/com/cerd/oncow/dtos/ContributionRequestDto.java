package br.com.cerd.oncow.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ContributionRequestDto(@NotBlank String name,
                                     @NotNull @DecimalMin("5.00") BigDecimal amount) {
}
