package com.neonex.payment.service.domain.entity;

import com.neonex.domain.entity.BaseEntity;
import com.neonex.domain.valueobject.CustomerId;
import com.neonex.domain.valueobject.Money;
import com.neonex.payment.service.domain.valueobject.CreditHistoryId;
import com.neonex.payment.service.domain.valueobject.TransactionType;

public class CreditHistory extends BaseEntity<CreditHistoryId> {
    private final Money amount;
    private final CustomerId customerId;
    private final TransactionType transactionType;

    private CreditHistory(Builder builder) {
        super.setId(builder.id);
        amount = builder.amount;
        customerId = builder.customerId;
        transactionType = builder.transactionType;
    }

    public Money getAmount() {
        return amount;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public static final class Builder {
        private CreditHistoryId id;
        private Money amount;
        private CustomerId customerId;
        private TransactionType transactionType;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder creditHistoryId(CreditHistoryId val) {
            id = val;
            return this;
        }

        public Builder amount(Money val) {
            amount = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder transactionType(TransactionType val) {
            transactionType = val;
            return this;
        }

        public CreditHistory build() {
            return new CreditHistory(this);
        }
    }
}
