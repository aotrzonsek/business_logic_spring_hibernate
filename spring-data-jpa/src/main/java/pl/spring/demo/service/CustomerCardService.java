package pl.spring.demo.service;

import pl.spring.demo.to.CustomerCardTo;

import java.util.List;

public interface CustomerCardService {

    List<CustomerCardTo> getAllCustomerCards();
}
