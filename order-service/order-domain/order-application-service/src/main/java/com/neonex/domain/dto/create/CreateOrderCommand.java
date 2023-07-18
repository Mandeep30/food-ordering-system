package com.neonex.domain.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public final class CreateOrderCommand {
    private final @NotNull UUID customerId;
    private final @NotNull UUID restaurantId;
    private final @NotNull BigDecimal price;
    private final @NotNull List<OrderItem> items;
    private final @NotNull OrderAddress address;
}
