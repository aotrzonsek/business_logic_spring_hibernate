package pl.spring.demo.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.spring.demo.dao.BookDao;
import pl.spring.demo.dao.BookExemplarDao;
import pl.spring.demo.dao.CustomerDao;
import pl.spring.demo.dao.LoanDao;
import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.entity.LoanEntity;
import pl.spring.demo.entity.PaperBookExemplarEntity;
import pl.spring.demo.mapper.BookMapper;
import pl.spring.demo.service.helper.CurrentDateProvider;
import pl.spring.demo.to.*;
import pl.spring.demo.type.BookCover;
import pl.spring.demo.type.PaperSize;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

public class BookServiceImplMockTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private CurrentDateProvider currentDateProvider;

    @Mock
    private BookDao bookDao;

    @Mock
    private BookExemplarDao bookExemplarDao;

    @Mock
    private CustomerDao customerDao;

    @Mock
    private LoanDao loanDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findBooksShouldWork() {
        // given
        List<BookEntity> bookEntities = new ArrayList<>();
        when(bookDao.findBooks(any(BookSearchCriteriaTo.class))).thenReturn(bookEntities);
        List<BookTo> bookTos = new ArrayList<>();
        when(bookMapper.mapSourceCollection(bookEntities)).thenReturn(bookTos);
        // when
        List<BookTo> results = bookService.findBooks(new BookSearchCriteriaTo());
        // then
        assertEquals(bookTos, results);
    }

    @Test
    public void loanBookShouldWork() {
        // given
        when(bookExemplarDao.getOne(anyLong())).thenReturn(new PaperBookExemplarEntity(10L, "serial", 130, PaperSize.A_5, BookCover.SOFT));
        // when
        BookLoanResultTo bookLoanResultTo = bookService.loanBook(new BookLoanRequestTo());
        // then
        assertNotNull(bookLoanResultTo);
        assertEquals(BookLoanStatus.SUCCESS, bookLoanResultTo.getStatus());
    }
}
