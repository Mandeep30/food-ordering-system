package com.neonex.payment.service;

import com.neonex.domain.valueobject.Money;
import com.neonex.domain.valueobject.PaymentStatus;
import com.neonex.payment.service.domain.entity.CreditEntry;
import com.neonex.payment.service.domain.entity.CreditHistory;
import com.neonex.payment.service.domain.entity.Payment;
import com.neonex.payment.service.domain.event.PaymentCancelledEvent;
import com.neonex.payment.service.domain.event.PaymentCompletedEvent;
import com.neonex.payment.service.domain.event.PaymentEvent;
import com.neonex.payment.service.domain.event.PaymentFailedEvent;
import com.neonex.payment.service.domain.valueobject.CreditHistoryId;
import com.neonex.payment.service.domain.valueobject.TransactionType;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
public class PaymentDomainServiceImpl implements PaymentDomainService {
    @Override
    public PaymentEvent validateAndInitiatePayment(Payment payment,
                                                   CreditEntry creditEntry,
                                                   List<CreditHistory> creditHistories,
                                                   List<String> failureMessage) {
        payment.initializePayment();

        payment.validatePayment(failureMessage);

        if (payment.getPrice().isGreaterThan(creditEntry.getTotalCreditAmount())) {
            log.error("Payment amount is greater than credit entry amount");
            failureMessage.add(String.format("Customer with id %s, doesn't have enough credit for payment",
                    payment.getCustomerId()));
        }

        creditEntry.subtractCreditAmount(payment.getPrice());

        updateCreditHistories(payment, creditHistories, TransactionType.DEBIT);

        Money totalDebitedHistory = getTotalAmountOfCreditHistory(creditHistories, TransactionType.DEBIT);
        Money totalCreditedHistory = getTotalAmountOfCreditHistory(creditHistories, TransactionType.CREDIT);

        if (totalDebitedHistory.isGreaterThan(totalCreditedHistory)) {
            log.error("totalDebitedHistory amount is greater than totalCreditedHistory amount");
            failureMessage.add(String.format("Customer with id %s, doesn't have enough credit for payment",
                    payment.getCustomerId()));
        }

        if (!creditEntry.getTotalCreditAmount().subtract(payment.getPrice()).equals(totalCreditedHistory.subtract(totalDebitedHistory))) {
            log.error("credit entry total is not equal to totalCreditHistory amount");
            failureMessage.add(String.format("Customer with id %s, " +
                            "credit entry total is not equal to the total credit history amount",
                    payment.getCustomerId()));
        }

        if (failureMessage.isEmpty()) {
            payment.updateStatus(PaymentStatus.COMPLETED);
            return new PaymentCompletedEvent(payment, ZonedDateTime.now(ZoneId.of("UTC")), List.of());
        } else {
            payment.updateStatus(PaymentStatus.FAILED);
            return new PaymentFailedEvent(payment, ZonedDateTime.now(ZoneId.of("UTC")), List.of());
        }
    }


    @Override
    public PaymentEvent validateAndCancelPayment(Payment payment,
                                                 CreditEntry creditEntry,
                                                 List<CreditHistory> creditHistories,
                                                 List<String> failureMessage) {
        payment.validatePayment(failureMessage);
        creditEntry.addCreditAmount(payment.getPrice());
        updateCreditHistories(payment, creditHistories, TransactionType.CREDIT);
        if (failureMessage.isEmpty()) {
            payment.updateStatus(PaymentStatus.CANCELLED);
            return new PaymentCancelledEvent(payment, ZonedDateTime.now(ZoneId.of("UTC")), List.of());
        } else {
            payment.updateStatus(PaymentStatus.FAILED);
            return new PaymentFailedEvent(payment, ZonedDateTime.now(ZoneId.of("UTC")), List.of());
        }
    }

    private void updateCreditHistories(Payment payment, List<CreditHistory> creditHistories, TransactionType transactionType) {
        creditHistories.add(CreditHistory.Builder
                .builder()
                .creditHistoryId(new CreditHistoryId(UUID.randomUUID()))
                .customerId(payment.getCustomerId())
                .amount(payment.getPrice())
                .transactionType(transactionType)
                .build());
    }

    private Money getTotalAmountOfCreditHistory(List<CreditHistory> creditHistories, TransactionType transactionType) {
        return creditHistories.stream()
                .filter(history -> history.getTransactionType().equals(transactionType))
                .map(CreditHistory::getAmount)
                .reduce(Money.ZERO, Money::add);
    }
}
