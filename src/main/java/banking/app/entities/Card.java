package banking.app.entities;

import java.math.BigDecimal;
import javax.persistence.*;

@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(generator="card_gen")
    @SequenceGenerator(name="card_gen", sequenceName="id_card_seq", allocationSize = 1)
    private Long id_card;

    @ManyToOne (cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_card_type")
    private CardType card_type;

    @ManyToOne (cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name="id_account")
    private Account account;
    
    @Column(nullable = false)
    private BigDecimal withdrawalLimit;

    public Card(CardType cardType, Account account, BigDecimal withdrawalLimit) {
        this.card_type = cardType;
        this.account = account;
        this.withdrawalLimit = withdrawalLimit;
    }

    public Card() {
    }

    public Long getId_card() {
        return id_card;
    }

    public void setId_card(Long id_card) {
        this.id_card = id_card;
    }

    public CardType getCard_type() {
        return card_type;
    }

    public void setCard_type(CardType card_type) {
        this.card_type = card_type;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (id_card != null ? !id_card.equals(card.id_card) : card.id_card != null) return false;
        if (card_type != null ? !card_type.equals(card.card_type) : card.card_type != null) return false;
        return withdrawalLimit != null ? withdrawalLimit.equals(card.withdrawalLimit) : card.withdrawalLimit == null;

    }

    @Override
    public int hashCode() {
        int result = id_card != null ? id_card.hashCode() : 0;
        result = 31 * result + (card_type != null ? card_type.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (withdrawalLimit != null ? withdrawalLimit.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id_card +
                ", card_type=" + card_type +
                ", account=" + account +
                ", withdrawalLimit=" + withdrawalLimit +
                '}';
    }
}
