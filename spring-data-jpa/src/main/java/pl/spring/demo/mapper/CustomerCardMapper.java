package pl.spring.demo.mapper;

import org.springframework.stereotype.Component;
import pl.spring.demo.entity.CustomerCardEntity;
import pl.spring.demo.to.CustomerCardTo;

@Component
public class CustomerCardMapper extends AbstractMapper<CustomerCardEntity, CustomerCardTo> {

    @Override
    public CustomerCardTo mapSource(CustomerCardEntity source) {
        CustomerCardTo customerCardTo = new CustomerCardTo();
        customerCardTo.setId(source.getId());
        customerCardTo.setSerialNumber(source.getSerialNumber());
        customerCardTo.setExpirationDate(source.getExpirationDate());
        return customerCardTo;
    }

    @Override
    public CustomerCardEntity mapTarget(CustomerCardTo target) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}