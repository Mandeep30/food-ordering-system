package com.neonex.domain.dto.create;

import com.neonex.domain.valueobject.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public final class CreateOrderResponse {
    private final @NotNull UUID orderTrackingId;
    private final @NotNull OrderStatus orderStatus;
    private final @NotNull String message;
}
