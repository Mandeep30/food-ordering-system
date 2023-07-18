package com.neonex.domain.port.output.repository;

import com.neonex.domain.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {
    //given the product id, return the full detail of the product, name and price from the restaurant
    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}
