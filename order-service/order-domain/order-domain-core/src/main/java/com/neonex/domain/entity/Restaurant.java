package com.neonex.domain.entity;

import com.neonex.domain.exception.OrderDomainException;
import com.neonex.domain.valueobject.RestaurantId;

import java.util.List;

public class Restaurant extends AggregateRoot<RestaurantId> {
    private final Boolean isActive;
    private final List<Product> products;

    public void validateRestaurant() {
        if (!isActive) {
            throw new OrderDomainException("Restaurant is not active");
        }
    }

    private Restaurant(Builder builder) {
        super.setId(builder.restaurantId);
        isActive = builder.isActive;
        products = builder.products;
    }

    public Boolean getActive() {
        return isActive;
    }

    public List<Product> getProducts() {
        return products;
    }

    public static final class Builder {
        private RestaurantId restaurantId;
        private Boolean isActive;
        private List<Product> products;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(RestaurantId val) {
            restaurantId = val;
            return this;
        }

        public Builder isActive(Boolean val) {
            isActive = val;
            return this;
        }

        public Builder products(List<Product> val) {
            products = val;
            return this;
        }

        public Restaurant build() {
            return new Restaurant(this);
        }
    }
}
