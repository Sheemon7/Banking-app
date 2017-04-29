package banking.app.entities;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ATM {
    
    @Id
    @OneToOne
    private PaymentPlace paymentPlace;
    
    @Column(nullable = false)
    private BigDecimal balance;
    
    @Column(nullable = false)
    private BigDecimal maxWithrdawal;
    public ATM(){}
    
    public ATM(PaymentPlace paymentPlace, BigDecimal balance, BigDecimal maxWithrdawal) {
        this.paymentPlace = paymentPlace;
        this.balance = balance;
        this.maxWithrdawal = maxWithrdawal;
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

    public BigDecimal getMaxWithrdawal() {
        return maxWithrdawal;
    }

    public void setMaxWithrdawal(BigDecimal maxWithrdawal) {
        this.maxWithrdawal = maxWithrdawal;
    }
    
    
}
