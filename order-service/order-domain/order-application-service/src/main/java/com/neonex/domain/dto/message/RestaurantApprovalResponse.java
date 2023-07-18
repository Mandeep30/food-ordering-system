package com.neonex.domain.dto.message;

import com.neonex.domain.valueobject.OrderApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class RestaurantApprovalResponse {
    private String id;
    private String sagaId;
    private String restaurantId;
    private String orderId;
    private Instant createdAt;
    private OrderApprovalStatus orderApprovalStatus;
    private List<String> failureMessages;
}
