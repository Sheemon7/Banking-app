package banking.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Trader {
    
    @Id
    @JoinColumn(name="id_payment_place")
    private PaymentPlace paymentPlace;
    
    @ManyToOne
    @JoinColumn(name="id_account")
    private Account account;
    
    @ManyToOne
    @JoinColumn(name="id_person")
    private Person person;
    
    
    
    public Trader(){}
    public Trader(PaymentPlace paymentPlace, Account account, Person person) {
        this.paymentPlace = paymentPlace;
        this.account = account;
        this.person = person;
    }

    public PaymentPlace getId_payment_place() {
        return paymentPlace;
    }

    public void setId_payment_place(PaymentPlace paymentPlace) {
        this.paymentPlace = paymentPlace;
    }

    public Account getId_account() {
        return account;
    }

    public void setId_account(Account account) {
        this.account = account;
    }

    public Person getId_person() {
        return person;
    }

    public void setId_person(Person person) {
        this.person = person;
    }
    
    
    
}