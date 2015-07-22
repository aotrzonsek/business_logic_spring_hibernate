package pl.spring.demo.cache.impl;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.spring.demo.cache.BookCache;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookSearchCriteriaTo;
import pl.spring.demo.to.BookTo;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class BookCacheImplTest {

    @Autowired
    private BookCache bookCache;

    @Autowired
    private BookService bookService;

    @Before
    public void setUp() {
        Mockito.reset(bookService);
    }

    @Test
    public void findBooksShouldCallServiceOnlyForFirstCall() throws Exception {
        // given
        BookSearchCriteriaTo searchCriteria = new BookSearchCriteriaTo();
        searchCriteria.setAuthor("Sienkiewicz");
        searchCriteria.setAvailable(false);
        List<BookTo> books = Lists.newArrayList(new BookTo(), new BookTo());
        when(bookService.findBooks(searchCriteria)).thenReturn(books);
        // when
        List<BookTo> firstResult = bookCache.findBooks(searchCriteria);
        List<BookTo> secondResult = bookCache.findBooks(searchCriteria);
        // then
        verify(bookService, times(1)).findBooks(searchCriteria);
        assertEquals(books, firstResult);
        assertEquals(books, secondResult);
    }
}