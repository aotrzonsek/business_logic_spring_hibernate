package pl.spring.demo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "BOOK")
public class BookEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @OneToMany(mappedBy = "book")
    private Set<BookExemplarEntity> bookExemplars = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "BOOK_AUTHOR",
            joinColumns = {@JoinColumn(name = "BOOK_ID", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "AUTHOR_ID", nullable = false, updatable = false)})
    private Set<AuthorEntity> authors = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "book")
    private BookSpoilerEntity bookSpoiler;

    @Version
    private long version;

    // for hibernate
    protected BookEntity() {
    }

    public BookEntity(String title) {
        this.title = title;
    }

    public BookEntity(Long id, String title) {
        this(title);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Set<AuthorEntity> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorEntity> authors) {
        this.authors = authors;
    }

    public Set<BookExemplarEntity> getBookExemplars() {
        return bookExemplars;
    }

    public BookSpoilerEntity getBookSpoiler() {
        return bookSpoiler;
    }

    public void setBookSpoiler(BookSpoilerEntity bookSpoiler) {
        if (bookSpoiler != null) {
            bookSpoiler.setBook(this);
        }
        this.bookSpoiler = bookSpoiler;
    }

    public void addBookExemplar(BookExemplarEntity bookExemplar) {
        bookExemplar.setBook(this);
        bookExemplars.add(bookExemplar);
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
