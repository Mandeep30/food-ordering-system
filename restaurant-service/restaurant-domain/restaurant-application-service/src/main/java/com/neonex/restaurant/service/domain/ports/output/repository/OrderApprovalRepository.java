package com.neonex.restaurant.service.domain.ports.output.repository;

import com.neonex.restaurant.service.domain.entity.OrderApproval;

public interface OrderApprovalRepository {
    OrderApproval save(OrderApproval orderApproval);
}
