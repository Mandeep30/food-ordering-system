package com.neonex.payment.service.dataaccess.credithistory.mapper;

import com.neonex.domain.valueobject.CustomerId;
import com.neonex.domain.valueobject.Money;
import com.neonex.payment.service.dataaccess.credithistory.entity.CreditHistoryEntity;
import com.neonex.payment.service.domain.entity.CreditHistory;
import com.neonex.payment.service.domain.valueobject.CreditHistoryId;
import org.springframework.stereotype.Component;

@Component
public class CreditHistoryDataAccessMapper {

    public CreditHistory creditHistoryEntityToCreditHistory(CreditHistoryEntity creditHistoryEntity) {
        return CreditHistory.Builder.builder()
                .creditHistoryId(new CreditHistoryId(creditHistoryEntity.getId()))
                .customerId(new CustomerId(creditHistoryEntity.getCustomerId()))
                .amount(new Money(creditHistoryEntity.getAmount()))
                .transactionType(creditHistoryEntity.getType())
                .build();
    }

    public CreditHistoryEntity creditHistoryToCreditHistoryEntity(CreditHistory creditHistory) {
        return CreditHistoryEntity.builder()
                .id(creditHistory.getId().id())
                .customerId(creditHistory.getCustomerId().id())
                .amount(creditHistory.getAmount().amount())
                .type(creditHistory.getTransactionType())
                .build();
    }

}
