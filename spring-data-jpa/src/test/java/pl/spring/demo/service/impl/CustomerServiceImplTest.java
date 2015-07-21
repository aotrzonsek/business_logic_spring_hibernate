package pl.spring.demo.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.spring.demo.dao.BookExemplarDao;
import pl.spring.demo.service.CustomerCardService;
import pl.spring.demo.service.CustomerService;
import pl.spring.demo.to.CustomerCardTo;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CustomerServiceImplTest extends AbstractDatabaseTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerCardService customerCardService;

    @Autowired
    private BookExemplarDao bookExemplarDao;

    @Test
    public void deleteCustomerShouldRemoveCustomerCardsAndLoansButNotBookExemplars() throws Exception {
        // given
        long customerId = 1L;
        long countOfBookExemplarsBeforeDelete = bookExemplarDao.count();
        // when
        customerService.deleteCustomer(customerId);
        // then
        assertEquals(0, customerService.findAllCustomers().size());

        List<CustomerCardTo> allCustomerCards = customerCardService.getAllCustomerCards();
        assertEquals(1, allCustomerCards.size());
        assertEquals(3L, (long)allCustomerCards.get(0).getId()); // the only card which didn't belong to the deleted customer

        long countOfBookExemplarsAfterDelete = bookExemplarDao.count();
        assertEquals(countOfBookExemplarsBeforeDelete, countOfBookExemplarsAfterDelete);
    }
}