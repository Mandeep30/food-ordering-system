package com.neonex.domain.entity;

import com.neonex.domain.exception.OrderDomainException;
import com.neonex.domain.valueobject.CustomerId;
import com.neonex.domain.valueobject.Money;
import com.neonex.domain.valueobject.OrderId;
import com.neonex.domain.valueobject.OrderItemId;
import com.neonex.domain.valueobject.OrderStatus;
import com.neonex.domain.valueobject.RestaurantId;
import com.neonex.domain.valueobject.StreetAddress;
import com.neonex.domain.valueobject.TrackingId;

import java.util.List;
import java.util.UUID;

public class Order extends AggregateRoot<OrderId> {
    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final StreetAddress streetAddress;
    private final Money price;
    private final List<OrderItem> items;

    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;

    //business-logic implementation methods
    public void initialiseOrder() {
        setId(new OrderId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
        initialiseOrderItems();
    }

    public void validateOrder() {
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrice();
    }

    //state changing methods
    public void pay() {
        if (orderStatus != OrderStatus.PENDING) {
            throw new OrderDomainException("Order is not in correct state for pay");
        }
        orderStatus = OrderStatus.PAID;
    }

    public void approve() {
        if (orderStatus != OrderStatus.PAID) {
            throw new OrderDomainException("Order is not in correct state for approve");
        }
        orderStatus = OrderStatus.APPROVED;
    }

    public void initCancel(List<String> errorMessages) {
        if (orderStatus != OrderStatus.PENDING) {
            throw new OrderDomainException("Order is not in correct state for initCancel");
        }
        orderStatus = OrderStatus.CANCELLING;
        updateErrorMessages(errorMessages);
    }

    public void cancel(List<String> errorMessages) {
        if (orderStatus != OrderStatus.PENDING && orderStatus != OrderStatus.CANCELLING) {
            throw new OrderDomainException("Order is not in correct state for cancel");
        }
        orderStatus = OrderStatus.CANCELLED;
        updateErrorMessages(errorMessages);
    }

    private void updateErrorMessages(List<String> errorMessages) {
        if (errorMessages != null && this.failureMessages != null) {
            this.failureMessages.addAll(errorMessages.stream().filter(String::isEmpty).toList());
        } else if (this.failureMessages == null) {
            this.failureMessages = errorMessages;
        }
    }

    private void validateInitialOrder() {
        if (getId() == null || trackingId == null || orderStatus != OrderStatus.PENDING) {
            throw new OrderDomainException("Order is not initialised correctly");
        }
    }

    private void validateTotalPrice() {
        if (!price.isGreaterThanZero()) {
            throw new OrderDomainException("Order total price can't be null or zero " + price);
        }
    }

    private void validateItemsPrice() {
        var total = items.stream().map(orderItem -> {
            validateOrderItem(orderItem);
            return orderItem.getSubTotal();
        }).reduce(Money.ZERO, Money::add);
        if (!total.equals(price)) {
            throw new OrderDomainException("Order total " + price + " is not equal to sum of order item subtotal " + total);
        }
    }

    private void validateOrderItem(OrderItem orderItem) {
        if (!orderItem.validateProductPrice()) {
            throw new OrderDomainException("Invalid order item price");
        }
    }

    private void initialiseOrderItems() {
        long orderItemId = 1;
        for (var item : items) {
            item.initialiseOrderItem(super.getId(), new OrderItemId(orderItemId++));
        }
    }

    private Order(Builder builder) {
        super.setId(builder.orderId);
        customerId = builder.customerId;
        restaurantId = builder.restaurantId;
        streetAddress = builder.streetAddress;
        price = builder.price;
        items = builder.items;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failureMessages = builder.failureMessages;
    }


    public CustomerId getCustomerId() {
        return customerId;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public StreetAddress getStreetAddress() {
        return streetAddress;
    }

    public Money getPrice() {
        return price;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }


    public static final class Builder {
        private OrderId orderId;
        private CustomerId customerId;
        private RestaurantId restaurantId;
        private StreetAddress streetAddress;
        private Money price;
        private List<OrderItem> items;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failureMessages;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder restaurantId(RestaurantId val) {
            restaurantId = val;
            return this;
        }

        public Builder streetAddress(StreetAddress val) {
            streetAddress = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder items(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
