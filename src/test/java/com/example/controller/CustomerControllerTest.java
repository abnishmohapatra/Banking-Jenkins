package com.example.controller;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.example.model.Customer;
import com.example.service.CustomerService;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private Customer customer1;
    private Customer customer2;

    @BeforeEach
    void setUp() {
        customer1 = new Customer();
        customer1.setId(1);
        customer1.setName("Customer A");
        customer1.setBalance(100.0);

        customer2 = new Customer();
        customer2.setId(2);
        customer2.setName("Customer B");
        customer2.setBalance(200.0);
    }

    @Test
    void testRead() {
        List<Customer> customers = Arrays.asList(customer1, customer2);
        when(customerService.read()).thenReturn(customers);

        List<Customer> result = customerController.read();
        assertEquals(2, result.size());
        assertEquals("Customer A", result.get(0).getName());
        assertEquals("Customer B", result.get(1).getName());
    }

    @Test
    void testReadOne() {
        when(customerService.readOne(1)).thenReturn(ResponseEntity.of(Optional.of(customer1)));

        ResponseEntity<Customer> result = customerController.readOne(1);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Customer A", result.getBody().getName());
    }

    @Test
    void testAdd() {
        customerController.post(customer1);
        verify(customerService).post(customer1);
    }

    @Test
    void testUpdate() {
        when(customerService.update(customer1, 1)).thenReturn(ResponseEntity.of(Optional.of(customer1)));

        ResponseEntity<Customer> result = customerController.update(customer1, 1);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Customer A", result.getBody().getName());
    }

    @Test
    void testDelete() {
        when(customerService.delete(1)).thenReturn(ResponseEntity.of(Optional.of(customer1)));

        ResponseEntity<Customer> result = customerController.delete(1);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Customer A", result.getBody().getName());
    }

    @Test
    void testDeposit() {
        when(customerService.deposit(1, 50.0)).thenReturn(ResponseEntity.of(Optional.of(customer1)));

        ResponseEntity<Customer> result = customerController.deposit(1, 50.0);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Customer A", result.getBody().getName());
    }

    @Test
    void testWithdraw() {
        when(customerService.withdraw(1, 50.0)).thenReturn(ResponseEntity.of(Optional.of(customer1)));

        ResponseEntity<Customer> result = customerController.withdraw(1, 50.0);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Customer A", result.getBody().getName());
    }
}

