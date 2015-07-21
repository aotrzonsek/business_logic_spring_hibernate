package pl.spring.demo.to;

import pl.spring.demo.type.PersonalData;

import java.util.List;

public class CustomerTo {

    private Long id;
    private PersonalData personalData;
    private String email;
    private String phone;
    private List<BookExemplarTo> loanedBooks;
    private List<CustomerCardTo> customerCards;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<BookExemplarTo> getLoanedBooks() {
        return loanedBooks;
    }

    public void setLoanedBookExemplars(List<BookExemplarTo> loanedBooks) {
        this.loanedBooks = loanedBooks;
    }

    public void setLoanedBooks(List<BookExemplarTo> loanedBooks) {
        this.loanedBooks = loanedBooks;
    }

    public List<CustomerCardTo> getCustomerCards() {
        return customerCards;
    }

    public void setCustomerCards(List<CustomerCardTo> customerCards) {
        this.customerCards = customerCards;
    }
}
