package com.neonex.domain.dto.track;

import com.neonex.domain.valueobject.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public final class TrackOrderResponse {
    private final @NotNull UUID orderTrackingId;
    private final @NotNull OrderStatus orderStatus;
    private final List<String> failureMessage;
}
