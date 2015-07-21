package pl.spring.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.spring.demo.dao.CustomerCardDao;
import pl.spring.demo.entity.CustomerCardEntity;
import pl.spring.demo.mapper.CustomerCardMapper;
import pl.spring.demo.service.CustomerCardService;
import pl.spring.demo.to.CustomerCardTo;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerCardServiceImpl implements CustomerCardService {

    private final CustomerCardDao customerCardDao;
    private final CustomerCardMapper customerCardMapper;

    @Autowired
    public CustomerCardServiceImpl(CustomerCardDao customerCardDao, CustomerCardMapper customerCardMapper) {
        this.customerCardDao = customerCardDao;
        this.customerCardMapper = customerCardMapper;
    }

    @Override
    public List<CustomerCardTo> getAllCustomerCards() {
        List<CustomerCardEntity> customerCards = customerCardDao.findAll();
        return new ArrayList<>(customerCardMapper.mapSourceCollection(customerCards));
    }
}
