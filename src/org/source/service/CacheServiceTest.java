package org.source.service;

import static org.junit.jupiter.api.Assertions.*;

class CacheServiceTest {

    @org.junit.jupiter.api.Test
    void getId() {
       int temp =  new CacheService().getId();
       int score=0;
       assertEquals(score,temp);
    }

    @org.junit.jupiter.api.Test
    void getAccessUser() {
        int temp = new CacheService().getAccessUser();
        int score = 0;
        assertEquals(score, temp);
    }

    @org.junit.jupiter.api.Test
    void getIdSelectedProduct() {
        int temp = new CacheService().getIdSelectedProduct();
        int score = 0;
        assertEquals(score, temp);
    }

    @org.junit.jupiter.api.Test
    void getIdSelectedOrder() {
        int temp = new CacheService().getIdSelectedOrder();
        int score = 0;
        assertEquals(score, temp);
    }

    @org.junit.jupiter.api.Test
    void getOrderStatus() {
        int temp = new CacheService().getOrderStatus();
        int score = 0;
        assertEquals(score, temp);
    }

    @org.junit.jupiter.api.Test
    void getIdSelectedUser() {
        int temp = new CacheService().getIdSelectedUser();
        int score = 0;
        assertEquals(score, temp);
    }
}