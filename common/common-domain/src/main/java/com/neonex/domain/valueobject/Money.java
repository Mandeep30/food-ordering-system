package com.neonex.domain.valueobject;

import com.neonex.domain.exception.DomainException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Money(BigDecimal amount) {
    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public boolean isGreaterThanZero() {
        return this.amount != null && this.amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGreaterThan(Money money) {
        return money != null && this.amount.compareTo(money.amount) > 0;
    }

    public Money add(Money money) {
        if (money == null) {
            throw new DomainException("Money value object is null");
        }
        return new Money(setScale(this.amount.add(money.amount)));
    }

    public Money subtract(Money money) {
        if (money == null) {
            throw new DomainException("Money value object is null");
        }
        return new Money(setScale(this.amount.subtract(money.amount)));
    }

    public Money multiply(int multiplier) {
        return new Money(setScale(this.amount.multiply(BigDecimal.valueOf(multiplier))));
    }

    private BigDecimal setScale(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.HALF_EVEN);
    }
}
