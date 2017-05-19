package banking.app.entities;

import javax.persistence.*;

@Entity
@Table(name="trader")
public class Trader {
    
    @Id
    @OneToOne
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trader trader = (Trader) o;

        if (paymentPlace != null ? !paymentPlace.equals(trader.paymentPlace) : trader.paymentPlace != null)
            return false;
        if (account != null ? !account.equals(trader.account) : trader.account != null) return false;
        return person != null ? person.equals(trader.person) : trader.person == null;

    }

    @Override
    public int hashCode() {
        int result = paymentPlace != null ? paymentPlace.hashCode() : 0;
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (person != null ? person.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Trader{" +
                "paymentPlace=" + paymentPlace +
                '}';
    }
}
