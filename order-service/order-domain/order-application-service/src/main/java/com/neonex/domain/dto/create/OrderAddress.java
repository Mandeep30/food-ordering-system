package com.neonex.domain.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@Getter
public final class OrderAddress {
    private final @NotNull @Max(50) String street;
    private final @NotNull @Max(50) String city;
    private final @NotNull @Max(10) String postalCode;
}
