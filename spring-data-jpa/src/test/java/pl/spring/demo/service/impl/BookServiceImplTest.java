package pl.spring.demo.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.*;
import pl.spring.demo.type.AudioBookFormat;
import pl.spring.demo.type.BookCover;
import pl.spring.demo.type.PaperSize;

import javax.persistence.OptimisticLockException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class BookServiceImplTest extends AbstractDatabaseTest {

    @Autowired
    private BookService bookService;

    @Test
    public void findBooksShouldFindBooksByTitle() {
        // given
        String title = "Wszyscy mamy";
        BookSearchCriteriaTo searchCriteria = new BookSearchCriteriaTo();
        searchCriteria.setTitle(title);
        // when
        List<BookTo> books = bookService.findBooks(searchCriteria);
        // then
        assertEquals(1, books.size());
    }

    @Test
    public void findBooksShouldNotFindBooksByNotExistingTitle() {
        // given
        String title = "NotExistingTitle123";
        BookSearchCriteriaTo searchCriteria = new BookSearchCriteriaTo();
        searchCriteria.setTitle(title);
        // when
        List<BookTo> books = bookService.findBooks(searchCriteria);
        // then
        assertEquals(0, books.size());
    }

    @Test
    public void findBooksShouldFindBooksByAuthor() {
        // given
        String author = "Popuelin";
        BookSearchCriteriaTo searchCriteria = new BookSearchCriteriaTo();
        searchCriteria.setAuthor(author);
        // when
        List<BookTo> books = bookService.findBooks(searchCriteria);
        // then
        assertEquals(4, books.size());
    }

    @Test
    public void findBooksShouldFindBooksWithSpoiler() {
        // given
        BookSearchCriteriaTo searchCriteria = new BookSearchCriteriaTo();
        searchCriteria.setHasSpoiler(true);
        // when
        List<BookTo> books = bookService.findBooks(searchCriteria);
        // then
        assertEquals(1, books.size());
    }

    @Test
    public void findBooksShouldFindBooksWithoutSpoiler() {
        // given
        BookSearchCriteriaTo searchCriteria = new BookSearchCriteriaTo();
        searchCriteria.setHasSpoiler(false);
        // when
        List<BookTo> books = bookService.findBooks(searchCriteria);
        // then
        assertEquals(7, books.size());
    }

    @Test
    public void findBooksShouldFindBooksAvailable() {
        // given
        BookSearchCriteriaTo searchCriteria = new BookSearchCriteriaTo();
        searchCriteria.setAvailable(true);

        BookLoanRequestTo loadDetails1 = new BookLoanRequestTo();
        loadDetails1.setBookExemplarId(26);
        loadDetails1.setCustomerId(1);
        bookService.loanBook(loadDetails1);

        BookLoanRequestTo loadDetails2 = new BookLoanRequestTo();
        loadDetails2.setBookExemplarId(14);
        loadDetails2.setCustomerId(1);
        bookService.loanBook(loadDetails2);

        // when
        List<BookTo> books = bookService.findBooks(searchCriteria);
        // then
        assertEquals(7, books.size());
    }

    @Test
    public void findBooksShouldFindBooksUnavailable() {
        // given
        BookSearchCriteriaTo searchCriteria = new BookSearchCriteriaTo();
        searchCriteria.setAvailable(false);

        BookLoanRequestTo loadDetails = new BookLoanRequestTo();
        loadDetails.setBookExemplarId(26);
        loadDetails.setCustomerId(1);
        bookService.loanBook(loadDetails);

        BookLoanRequestTo loadDetails2 = new BookLoanRequestTo();
        loadDetails2.setBookExemplarId(14);
        loadDetails2.setCustomerId(1);
        bookService.loanBook(loadDetails2);

        // when
        List<BookTo> books = bookService.findBooks(searchCriteria);
        // then
        assertEquals(1, books.size());
    }

    @Test
    public void loaningAlreadyLoanedBookShouldNotBePossible() {
        // given
        BookLoanRequestTo bookLoanRequestTo = new BookLoanRequestTo();
        bookLoanRequestTo.setBookExemplarId(26);
        bookLoanRequestTo.setCustomerId(1);
        // when first loan try
        BookLoanResultTo firstBookLoanResultTo = bookService.loanBook(bookLoanRequestTo);
        // then
        assertEquals(BookLoanStatus.SUCCESS, firstBookLoanResultTo.getStatus());
        // when second loan try
        BookLoanResultTo secondBookLoanResultTo = bookService.loanBook(bookLoanRequestTo);
        // then
        assertEquals(BookLoanStatus.ALREADY_BORROWED, secondBookLoanResultTo.getStatus());
    }

    @Test
    public void testShouldCreateNewBook() {
        // given
        NewBookTo bookToSave = new NewBookTo();
        bookToSave.setTitle("Title of new book");
        // when
        BookTo alreadySavedBook = bookService.createBook(bookToSave);
        // then
        assertNotNull(alreadySavedBook.getId());
        BookTo bookTo = bookService.findBookById(alreadySavedBook.getId());
        assertNotNull(bookTo);
        assertTrue(bookTo.getAuthors().isEmpty());
    }

    @Test
    public void testShouldCreateNewBookWithAuthor() {
        // given
        NewBookTo bookToSave = new NewBookTo();
        bookToSave.setTitle("Title of new book");
        bookToSave.setAuthors(new HashSet<>(Arrays.asList(1L)));
        // when
        BookTo alreadySavedBook = bookService.createBook(bookToSave);
        // then
        assertNotNull(alreadySavedBook.getId());
        BookTo bookTo = bookService.findBookById(alreadySavedBook.getId());
        assertNotNull(bookTo);
        assertEquals(1, bookTo.getAuthors().size());
    }

    @Test(expected = javax.persistence.EntityNotFoundException.class)
    public void testShouldCreateNewBookWithAuthor2() {
        // given
        final long idOfNonExistentAuthor = 222L;
        NewBookTo bookToSave = new NewBookTo();
        bookToSave.setTitle("Title of new book");
        bookToSave.setAuthors(new HashSet<>(Arrays.asList(idOfNonExistentAuthor)));
        // when
        bookService.createBook(bookToSave);
        // then
        fail("Should end with EntityNotFoundException");
    }

    @Test
    public void testShouldCreateNewBookWithSpoiler() {
        // given
        NewBookTo bookToSave = new NewBookTo();
        bookToSave.setTitle("Title of new book");
        bookToSave.setSpoiler("Spoiler sample");
        // when
        BookTo alreadySavedBook = bookService.createBook(bookToSave);
        // then
        assertNotNull(alreadySavedBook.getId());
        assertEquals(bookToSave.getSpoiler(), bookService.findBookSpoiler(alreadySavedBook.getId()));
    }

    @Test
    public void testShouldCreateNewBookWithExemplars() {
        // given
        NewBookTo bookToSave = new NewBookTo();
        bookToSave.setTitle("Title of new book");

        PaperBookExemplarTo paperBookExemplar1 = new PaperBookExemplarTo("123", 333, PaperSize.A_4, BookCover.HARD);
        PaperBookExemplarTo paperBookExemplar2 = new PaperBookExemplarTo("234", 222, PaperSize.B_5, BookCover.SOFT);
        AudioBookExemplarTo audioBookExemplar = new AudioBookExemplarTo("321", AudioBookFormat.AUDIO);
        bookToSave.setExemplars(new HashSet<>(Arrays.asList(paperBookExemplar1, paperBookExemplar2, audioBookExemplar)));
        // when
        BookTo alreadySavedBook = bookService.createBook(bookToSave);
        // then
        assertNotNull(alreadySavedBook.getId());
        assertEquals(3, bookService.findBookExemplars(alreadySavedBook.getId()).size());
    }

    @Test
    public void updateBookShouldSaveChangesToTheBook() {
        // given
        NewBookTo bookToSave = new NewBookTo();
        bookToSave.setTitle("Title of new book");
        bookToSave.setSpoiler("Spoiler sample");
        BookTo createdBook = bookService.createBook(bookToSave);

        String updatedTitle = "Updated title";
        createdBook.setTitle(updatedTitle);
        // when
        BookTo savedBook = bookService.updateBook(createdBook);
        // then
        assertEquals(updatedTitle, savedBook.getTitle());
        assertTrue(savedBook.getVersion() > createdBook.getVersion());
    }

    @Test(expected = OptimisticLockException.class)
    public void updateBookShouldThrowOptimisticLockingExceptionWhenUpdatedVersionLowerThanExisting() {
        // given
        NewBookTo bookToSave = new NewBookTo();
        bookToSave.setTitle("Title of new book");
        bookToSave.setSpoiler("Spoiler sample");
        BookTo createdBook = bookService.createBook(bookToSave);

        createdBook.setVersion(createdBook.getVersion() - 1);
        //when
        bookService.updateBook(createdBook);
    }

}
