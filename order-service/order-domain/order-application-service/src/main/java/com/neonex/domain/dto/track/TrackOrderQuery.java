package com.neonex.domain.dto.track;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public final class TrackOrderQuery {
    private final @NotNull UUID orderTrackingId;
}
