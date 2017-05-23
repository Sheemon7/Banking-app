package banking.app.entities;

import banking.app.jpadatabase.PaymentPlaceDAO;
import banking.app.util.*;

import java.math.BigDecimal;
import javax.persistence.*;

@Entity
@Table(name = "ATM")
@SqlResultSetMapping(name="ATMResult", classes = {
        @ConstructorResult(targetClass = ATM.class,
                columns = {@ColumnResult(name="id_payment_place"), @ColumnResult(name="balance"), @ColumnResult(name="maxwithdrawal")})
})
public class ATM {

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal("100000");
    private static final BigDecimal DEFAULT_MAX_WITHDRAWAL = new BigDecimal("5000");

    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_payment_place")
    private PaymentPlace paymentPlace;

    @Column(nullable = false)
    private BigDecimal balance;
    
    @Column(nullable = false)
    private BigDecimal maxWithdrawal;

    public ATM() {}

    public ATM(String address) {
        this(new PaymentPlace(address), DEFAULT_BALANCE, DEFAULT_MAX_WITHDRAWAL);
    }

    public ATM(String address, BigDecimal balance, BigDecimal maxWithdrawal) {
        this(new PaymentPlace(address), balance, maxWithdrawal);
    }

    public ATM(PaymentPlace paymentPlace, BigDecimal balance, BigDecimal maxWithdrawal) {
        this.paymentPlace = paymentPlace;
        this.balance = balance;
        this.maxWithdrawal = maxWithdrawal;
    }

    public ATM(Long idPaymentPlace, BigDecimal balance, BigDecimal maxWithdrawal) {
        try {
            this.paymentPlace = PaymentPlaceDAO.getInstance().getEntity(idPaymentPlace);
        } catch (banking.app.util.EntityNotFoundException e) {
            e.printStackTrace();
        }
        this.balance = balance;
        this.maxWithdrawal = maxWithdrawal;
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
                ", maxWithdrawal=" + maxWithdrawal +
                '}';
    }
}
