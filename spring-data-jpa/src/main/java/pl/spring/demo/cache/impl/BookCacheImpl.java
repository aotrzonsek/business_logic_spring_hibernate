package pl.spring.demo.cache.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.spring.demo.cache.BookCache;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookSearchCriteriaTo;
import pl.spring.demo.to.BookTo;

import java.util.List;

@Service
public class BookCacheImpl implements BookCache {

    private final BookService bookService;

    @Autowired
    public BookCacheImpl(BookService bookService) {
        this.bookService = bookService;
    }

    // TODO Use Spring cache
    @Override
    public List<BookTo> findBooks(BookSearchCriteriaTo searchCriteria) {
        return bookService.findBooks(searchCriteria);
    }
}
