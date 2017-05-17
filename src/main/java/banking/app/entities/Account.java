package banking.app.entities;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
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
    
    
}
