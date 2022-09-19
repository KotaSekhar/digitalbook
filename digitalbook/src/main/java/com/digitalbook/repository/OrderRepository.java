package com.digitalbook.repository;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.digitalbook.entity.Order;
@Repository
public interface OrderRepository extends CrudRepository<Order, Integer>{

}
