package com.example.service;
 
import java.util.List;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
 
import com.example.repo.CustomerRepository;
import com.example.model.Customer;
@Service
public class CustomerService {
	@Autowired
	CustomerRepository cr;
	public List<Customer> read(){
		return cr.findAll();
	}
	public ResponseEntity<Customer> readOne(int id) {
		Customer c= cr.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid account ID"));
		return ResponseEntity.ok(c);
	}
	public void post(@RequestBody Customer c) {
		cr.save(c);
	}
	public ResponseEntity<Customer> update(Customer cnew,int id) {
		Customer cold=cr.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid account ID"));
		//cold.setId(cnew.getId());
		cold.setName(cnew.getName());
		cold.setUsername(cnew.getUsername());
		cold.setPassword(cnew.getPassword());
		cr.save(cold);
		return ResponseEntity.ok(cold);
	}
	public ResponseEntity<Customer> delete(int id) {
		Customer c=cr.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid account ID"));
		cr.delete(c);
		return ResponseEntity.ok(c);
	}
	public  ResponseEntity<Customer> deposit(int id, double amount) {
		Customer c=cr.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid account ID"));
		System.out.println(c.getBalance());
        c.setBalance(c.getBalance() + amount);
        System.out.println(c.getBalance());
        System.out.println(c.toString());
        cr.save(c);
        return ResponseEntity.ok(c); 
    }
	public  ResponseEntity<Customer> withdraw(int id, double amount) {
		Customer c=cr.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid account ID"));
		System.out.println(c.getBalance());
		  if (c.getBalance() < amount) {
	            throw new IllegalArgumentException("Insufficient balance");
	        }
        c.setBalance(c.getBalance() - amount);
        System.out.println(c.getBalance());
        cr.save(c);
        return ResponseEntity.ok(c);
    }

}
