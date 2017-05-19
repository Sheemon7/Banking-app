package banking.app.entities;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="account")
public class Account {
    
    private static final BigDecimal DEFAULT_BALANCE = BigDecimal.ZERO;
    
    @Id 
    @GeneratedValue(generator="my_seq1")
    @SequenceGenerator(name="my_seq1",sequenceName="account_id_account_seq", allocationSize=1)
    private Long id_account;
    
    @ManyToOne
    @JoinColumn(name="id_owner")
    private Person owner;
    
    @OneToMany(mappedBy="account")
    private List<Card> cards;
    
    @Column(nullable = false)
    private BigDecimal balance;

    public Account() {
    }

    public Account(Person owner, BigDecimal balance) {
        this.owner = owner;
        this.balance = balance;
    }

    public Account(Person owner) {
        this(owner, DEFAULT_BALANCE);
    }

    public static BigDecimal getDefaultBalance() {
        return DEFAULT_BALANCE;
    }

    public Long getId_account() {
        return id_account;
    }

    public void setId_account(Long id_account) {
        this.id_account = id_account;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (id_account != null ? !id_account.equals(account.id_account) : account.id_account != null) return false;
        if (owner != null ? !owner.equals(account.owner) : account.owner != null) return false;
        if (cards != null ? !cards.equals(account.cards) : account.cards != null) return false;
        return balance != null ? balance.equals(account.balance) : account.balance == null;

    }

    @Override
    public int hashCode() {
        int result = id_account != null ? id_account.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (cards != null ? cards.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id_account=" + id_account +
                ", balance=" + balance +
                '}';
    }
}
