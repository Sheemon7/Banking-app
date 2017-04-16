package banking.app.entities;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Card {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @Column(nullable = false)
    private CardType cardType;    
    
    @ManyToOne
    @Column(nullable = false)
    private Account account;
    
    @Column(nullable = false)
    private BigDecimal withdrawalLimit;

    public Card(Long id, CardType cardType, Account account, BigDecimal withdrawalLimit) {
        this.id = id;
        this.cardType = cardType;
        this.account = account;
        this.withdrawalLimit = withdrawalLimit;
    }

    public Card() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getWithdrawalLimit() {
        return withdrawalLimit;
    }

    public void setWithdrawalLimit(BigDecimal withdrawalLimit) {
        this.withdrawalLimit = withdrawalLimit;
    }    
    
}
