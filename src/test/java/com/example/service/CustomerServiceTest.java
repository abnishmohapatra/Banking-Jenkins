package com.example.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.example.repo.CustomerRepository;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer1;
    private Customer customer2;

    @BeforeEach
    void setUp() {
        customer1 = new Customer();
        customer1.setId(1);
        customer1.setName("Customer A");
        customer1.setUsername("customerA");
        customer1.setPassword("passwordA");
        customer1.setBalance(100.0);

        customer2 = new Customer();
        customer2.setId(2);
        customer2.setName("Customer B");
        customer2.setUsername("customerB");
        customer2.setPassword("passwordB");
        customer2.setBalance(200.0);
    }

    @Test
    void testRead() {
        List<Customer> customers = Arrays.asList(customer1, customer2);
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.read();
        assertEquals(2, result.size());
        assertEquals("Customer A", result.get(0).getName());
        assertEquals("Customer B", result.get(1).getName());
    }

    @Test
    void testReadOne() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer1));

        ResponseEntity<Customer> result = customerService.readOne(1);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Customer A", result.getBody().getName());
    }

    @Test
    void testReadOneInvalidId() {
        when(customerRepository.findById(3)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.readOne(3);
        });

        assertEquals("Invalid account ID", exception.getMessage());
    }

    @Test
    void testPost() {
        customerService.post(customer1);
        verify(customerRepository).save(customer1);
    }

    @Test
    void testUpdate() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer1));

        Customer updatedCustomer = new Customer();
        updatedCustomer.setName("Updated Customer A");
        updatedCustomer.setUsername("updatedCustomerA");
        updatedCustomer.setPassword("updatedPasswordA");

        ResponseEntity<Customer> result = customerService.update(updatedCustomer, 1);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Updated Customer A", result.getBody().getName());
        assertEquals("updatedCustomerA", result.getBody().getUsername());
        assertEquals("updatedPasswordA", result.getBody().getPassword());
    }

    @Test
    void testUpdateInvalidId() {
        when(customerRepository.findById(3)).thenReturn(Optional.empty());

        Customer updatedCustomer = new Customer();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.update(updatedCustomer, 3);
        });

        assertEquals("Invalid account ID", exception.getMessage());
    }

    @Test
    void testDelete() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer1));

        ResponseEntity<Customer> result = customerService.delete(1);
        assertEquals(200, result.getStatusCodeValue());
        verify(customerRepository).delete(customer1);
    }

    @Test
    void testDeleteInvalidId() {
        when(customerRepository.findById(3)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.delete(3);
        });

        assertEquals("Invalid account ID", exception.getMessage());
    }

    @Test
    void testDeposit() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer1));

        ResponseEntity<Customer> result = customerService.deposit(1, 50.0);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(150.0, result.getBody().getBalance());
        verify(customerRepository).save(customer1);
    }

    @Test
    void testDepositInvalidId() {
        when(customerRepository.findById(3)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.deposit(3, 50.0);
        });

        assertEquals("Invalid account ID", exception.getMessage());
    }

    @Test
    void testWithdraw() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer1));

        ResponseEntity<Customer> result = customerService.withdraw(1, 50.0);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(50.0, result.getBody().getBalance());
        verify(customerRepository).save(customer1);
    }

    @Test
    void testWithdrawInsufficientBalance() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer1));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.withdraw(1, 150.0);
        });

        assertEquals("Insufficient balance", exception.getMessage());
    }

    @Test
    void testWithdrawInvalidId() {
        when(customerRepository.findById(3)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.withdraw(3, 50.0);
        });

        assertEquals("Invalid account ID", exception.getMessage());
    }
}
