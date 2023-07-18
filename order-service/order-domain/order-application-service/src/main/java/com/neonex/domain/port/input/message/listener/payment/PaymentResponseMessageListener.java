package com.neonex.domain.port.input.message.listener.payment;


import com.neonex.domain.dto.message.PaymentResponse;

/**
 * Input port used by services which are communicating to order service via the messages
 */
public interface PaymentResponseMessageListener {
    void paymentCompleted(PaymentResponse paymentResponse);

    //when payment service fails because of any invariant or this can be called as part of the compensating transaction of the saga
    void paymentCancelled(PaymentResponse paymentResponse);
}
