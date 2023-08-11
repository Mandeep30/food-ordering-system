package com.neonex.payment.service;

import com.neonex.payment.service.domain.entity.CreditEntry;
import com.neonex.payment.service.domain.entity.CreditHistory;
import com.neonex.payment.service.domain.entity.Payment;
import com.neonex.payment.service.domain.event.PaymentEvent;

import java.util.List;

public interface PaymentDomainService {
    PaymentEvent validateAndInitiatePayment(Payment payment,
                                            CreditEntry creditEntry,
                                            List<CreditHistory> creditHistoryList,
                                            List<String> failureMessage);

    PaymentEvent validateAndCancelPayment(Payment payment,
                                            CreditEntry creditEntry,
                                            List<CreditHistory> creditHistoryList,
                                            List<String> failureMessage);
}
