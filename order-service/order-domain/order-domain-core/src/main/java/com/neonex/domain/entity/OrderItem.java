package com.neonex.domain.entity;

import com.neonex.domain.valueobject.Money;
import com.neonex.domain.valueobject.OrderId;
import com.neonex.domain.valueobject.OrderItemId;

public class OrderItem extends BaseEntity<OrderItemId> {
    private OrderId orderId;
    private final Product product;
    private final Integer quantity;
    private final Money price;
    private final Money subTotal;

    private OrderItem(Builder builder) {
        super.setId(builder.orderItemId);
        product = builder.productId;
        quantity = builder.quantity;
        price = builder.price;
        subTotal = builder.subTotal;
    }

    boolean validateProductPrice() {
        return (price.isGreaterThanZero() && product != null &&
                price.equals(product.getPrice()) &&
                price.multiply(quantity).equals(subTotal));
    }

    void initialiseOrderItem(OrderId orderId, OrderItemId orderItemId) {
        this.orderId = orderId;
        super.setId(orderItemId);

    }

    public OrderId getOrderId() {
        return orderId;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Money getPrice() {
        return price;
    }

    public Money getSubTotal() {
        return subTotal;
    }

    public static final class Builder {
        private OrderItemId orderItemId;
        private Product productId;
        private Integer quantity;
        private Money price;
        private Money subTotal;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder orderItemId(OrderItemId val) {
            orderItemId = val;
            return this;
        }

        public Builder productId(Product val) {
            productId = val;
            return this;
        }

        public Builder quantity(Integer val) {
            quantity = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder subTotal(Money val) {
            subTotal = val;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
