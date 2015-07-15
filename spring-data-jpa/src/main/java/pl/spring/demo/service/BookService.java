package pl.spring.demo.service;

import pl.spring.demo.to.*;

import java.util.List;

public interface BookService {

    BookTo findBookById(long bookId);

    List<BookTo> findBooks(BookSearchCriteriaTo searchCriteria);

    BookLoanResultTo loanBook(BookLoanRequestTo bookLoanRequest);

    BookTo createBook(NewBookTo bookToSave);
}
