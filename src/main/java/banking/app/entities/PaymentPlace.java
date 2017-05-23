package banking.app.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "payment_place")
public class PaymentPlace {

    @Id
    @GeneratedValue(generator = "payment_place_gen")
    @SequenceGenerator(name = "payment_place_gen", sequenceName = "id_payment_place_seq", allocationSize = 1)
    private long id_payment_place;

    @Column(nullable=false)
    private String address;
    
    @ManyToMany()
    private List<CardType> accepts;
    
    @OneToOne(mappedBy="paymentPlace")
    private ATM atm;
    
    @OneToOne(mappedBy="paymentPlace")
    private Trader trader;

    public PaymentPlace() {
    }

    public PaymentPlace(String address) {
        this.address = address;
    }

    public long getId_payment_place() {
        return id_payment_place;
    }

    public void setId_payment_place(long id_payment_place) {
        this.id_payment_place = id_payment_place;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<CardType> getAccepts() {
        return accepts;
    }

    public void setAccepts(List<CardType> accepts) {
        this.accepts = accepts;
    }

    public ATM getAtm() {
        return atm;
    }

    public void setAtm(ATM atm) {
        this.atm = atm;
    }

    public Trader getTrader() {
        return trader;
    }

    public void setTrader(Trader trader) {
        this.trader = trader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentPlace that = (PaymentPlace) o;

        if (id_payment_place != that.id_payment_place) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (accepts != null ? !accepts.equals(that.accepts) : that.accepts != null) return false;
        if (atm != null ? !atm.equals(that.atm) : that.atm != null) return false;
        return trader != null ? trader.equals(that.trader) : that.trader == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id_payment_place ^ (id_payment_place >>> 32));
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (accepts != null ? accepts.hashCode() : 0);
        result = 31 * result + (atm != null ? atm.hashCode() : 0);
        result = 31 * result + (trader != null ? trader.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PaymentPlace{" +
                "id_payment_place=" + id_payment_place +
                ", address='" + address + '\'' +
                '}';
    }
}
