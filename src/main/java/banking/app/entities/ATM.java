package banking.app.entities;

import java.math.BigDecimal;
import javax.persistence.*;

@Entity
@Table(name = "ATM")
public class ATM {

    @Id
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name="id_payment_place")
    private PaymentPlace paymentPlace;
    
    @Column(nullable = false)
    private BigDecimal balance;
    
    @Column(nullable = false)
    private BigDecimal maxWithdrawal;

    public ATM(){}
    
    public ATM(PaymentPlace paymentPlace, BigDecimal balance, BigDecimal maxWithrdawal) {
        this.paymentPlace = paymentPlace;
        this.balance = balance;
        this.maxWithdrawal = maxWithrdawal;
    }

    public PaymentPlace getPaymentPlace() {
        return paymentPlace;
    }

    public void setPaymentPlace(PaymentPlace paymentPlace) {
        this.paymentPlace = paymentPlace;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getMaxWithdrawal() {
        return maxWithdrawal;
    }

    public void setMaxWithdrawal(BigDecimal maxWithrdawal) {
        this.maxWithdrawal = maxWithrdawal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ATM atm = (ATM) o;

        if (paymentPlace != null ? !paymentPlace.equals(atm.paymentPlace) : atm.paymentPlace != null) return false;
        if (balance != null ? !balance.equals(atm.balance) : atm.balance != null) return false;
        return maxWithdrawal != null ? maxWithdrawal.equals(atm.maxWithdrawal) : atm.maxWithdrawal == null;

    }

    @Override
    public int hashCode() {
        int result = paymentPlace != null ? paymentPlace.hashCode() : 0;
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (maxWithdrawal != null ? maxWithdrawal.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ATM{" +
                "paymentPlace=" + paymentPlace +
                ", balance=" + balance +
                ", maxWithrdawal=" + maxWithdrawal +
                '}';
    }
}
