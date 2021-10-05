package com.doncorleone.dondelivery.repositories;

import com.doncorleone.dondelivery.entities.OrderItem;
import com.doncorleone.dondelivery.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
