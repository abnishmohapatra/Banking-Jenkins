package com.example.controller;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
 
import com.example.model.Customer;
import com.example.repo.CustomerRepository;
import com.example.service.CustomerService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/cust")
public class CustomerController {
	@Autowired
	CustomerService cc;
 
	//read
	@RequestMapping(value="/read",method=RequestMethod.GET) //OR @GetMapping("/read")
	public List<Customer> read() {
		return cc.read();
	}
	//readOne
	@RequestMapping(value="/readOne/{id}",method=RequestMethod.GET) //OR @GetMapping("/read")
	public ResponseEntity<Customer> readOne(@PathVariable int id){
		return cc.readOne(id);
	}
	//add
	@RequestMapping(value="/add",method=RequestMethod.POST) //OR @PostMapping("/read")
	public void post(@RequestBody Customer c) {
		cc.post(c);
	}
	//update
		@RequestMapping(value="/update/{id}",method=RequestMethod.PUT) //OR @PutMapping("/read")
		public ResponseEntity<Customer> update(@RequestBody Customer cnew,@PathVariable int id){
			return cc.update(cnew, id);
		}
		//delete
		@RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE) //OR @DeleteMapping("/read")
		public ResponseEntity<Customer> delete(@PathVariable int id){
			return cc.delete(id);
		}
		//deposit
		@RequestMapping(value="/deposit/{id}/{amount}",method=RequestMethod.PUT) //OR @PutMapping("/read")
		public ResponseEntity<Customer> deposit(@PathVariable int id,@PathVariable double amount){
			return cc.deposit(id, amount);
		}
		@RequestMapping(value="/withdraw/{id}/{amount}",method=RequestMethod.PUT)
		//@PostMapping("/withdraw/{id}/{amount}")
		public ResponseEntity<Customer> withdraw(@PathVariable int id,@PathVariable double amount){
			return cc.withdraw(id, amount);
		}
}
