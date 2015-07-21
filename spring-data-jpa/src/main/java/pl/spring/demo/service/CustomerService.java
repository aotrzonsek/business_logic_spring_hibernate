package pl.spring.demo.service;

import pl.spring.demo.to.CustomerTo;

import java.util.List;

public interface CustomerService {

    CustomerTo createCustomer(CustomerTo customer);

    List<CustomerTo> findAllCustomers();

    void deleteCustomer(long customerId);
}
