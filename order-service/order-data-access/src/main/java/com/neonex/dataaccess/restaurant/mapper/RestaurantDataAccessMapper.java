package com.neonex.dataaccess.restaurant.mapper;


import com.neonex.common.dataaccess.restaurant.entity.RestaurantEntity;
import com.neonex.domain.entity.Product;
import com.neonex.domain.entity.Restaurant;
import com.neonex.domain.exception.RestaurantDataAccessException;
import com.neonex.domain.valueobject.Money;
import com.neonex.domain.valueobject.ProductId;
import com.neonex.domain.valueobject.RestaurantId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantDataAccessMapper {

    public List<UUID> restaurantToRestaurantProducts(Restaurant restaurant) {
        return restaurant.getProducts().stream()
                .map(product -> product.getId().id())
                .collect(Collectors.toList());
    }

    public Restaurant restaurantEntityToRestaurant(List<RestaurantEntity> restaurantEntities) {
        RestaurantEntity restaurantEntity =
                restaurantEntities.stream().findFirst().orElseThrow(() ->
                        new RestaurantDataAccessException("Restaurant could not be found!"));

        List<Product> restaurantProducts = restaurantEntities.stream()
                .map(entity ->
                        new Product(new ProductId(entity.getProductId()),
                                new Money(entity.getProductPrice()),
                                entity.getProductName()
                        )).toList();

        return Restaurant.Builder.builder()
                .id(new RestaurantId(restaurantEntity.getRestaurantId()))
                .products(restaurantProducts)
                .isActive(restaurantEntity.getRestaurantActive())
                .build();
    }
}

