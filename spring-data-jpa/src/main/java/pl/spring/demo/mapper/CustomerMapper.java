package pl.spring.demo.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.spring.demo.entity.BookExemplarEntity;
import pl.spring.demo.entity.CustomerEntity;
import pl.spring.demo.to.BookExemplarTo;
import pl.spring.demo.to.CustomerTo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CustomerMapper extends AbstractMapper<CustomerEntity, CustomerTo> {

    private final BookExemplarMapper bookExemplarMapper;

    private final CustomerCardMapper customerCardMapper;

    @Autowired
    public CustomerMapper(BookExemplarMapper bookExemplarMapper, CustomerCardMapper customerCardMapper) {
        this.bookExemplarMapper = bookExemplarMapper;
        this.customerCardMapper = customerCardMapper;
    }

    @Override
    public CustomerTo mapSource(CustomerEntity source) {
        CustomerTo customerTo = new CustomerTo();
        customerTo.setId(source.getId());
        customerTo.setEmail(source.getEmail());
        customerTo.setPersonalData(source.getPersonalData());
        customerTo.setPhone(source.getPhoneNumber());
        customerTo.setLoanedBookExemplars(determineLoanedBookExemplars(source));
        customerTo.setCustomerCards(new ArrayList<>(customerCardMapper.mapSourceCollection(source.getCards())));
        return customerTo;
    }

    @Override
    public CustomerEntity mapTarget(CustomerTo target) {
        return new CustomerEntity(target.getId(), target.getPersonalData(), target.getPhone(), target.getEmail());
    }

    private List<BookExemplarTo> determineLoanedBookExemplars(CustomerEntity customerEntity) {
        Set<BookExemplarEntity> bookExemplarEntities = new HashSet<>();
        customerEntity.getLoans().stream().forEach(b -> bookExemplarEntities.addAll(b.getBookExemplars()));
        return new ArrayList<>(bookExemplarMapper.mapSourceCollection(bookExemplarEntities));
    }

}
