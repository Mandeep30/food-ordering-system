package com.neonex.domain.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public final class OrderItem {
    private final @NotNull UUID productId;
    private final @NotNull Integer quantity;
    private final @NotNull BigDecimal price;
    private final @NotNull BigDecimal subTotal;
}
