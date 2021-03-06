package banking.app.entities;

import javax.persistence.*;

@Entity
@Table(name="trader")
@SqlResultSetMapping(name="TraderResult", classes = {
        @ConstructorResult(targetClass = Trader.class,
                columns = {@ColumnResult(name="address"), @ColumnResult(name="balance"), @ColumnResult(name="maxwithdrawal")})
})
public class Trader {
    
    @Id
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="id_payment_place")
    private PaymentPlace paymentPlace;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name="id_account")
    private Account account;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name="id_person")
    private Person person;

    public Trader(){}

    public Trader(PaymentPlace paymentPlace, Account account, Person person) {
        this.paymentPlace = paymentPlace;
        this.account = account;
        this.person = person;
    }

    public PaymentPlace getPaymentPlace() {
        return paymentPlace;
    }

    public void setPaymentPlace(PaymentPlace paymentPlace) {
        this.paymentPlace = paymentPlace;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
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
                ", account=" + account.getId_account() +
                ", person=" + person +
                '}';
    }
}
