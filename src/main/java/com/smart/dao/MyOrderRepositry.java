package com.smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.entity.Myorder;

public interface MyOrderRepositry extends JpaRepository<Myorder, Long> {
 public Myorder findByOrderId(String orderId);
}
