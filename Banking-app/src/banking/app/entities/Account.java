package banking.app.entities;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Account {
    
    private static final BigDecimal DEFAULT_BALANCE = BigDecimal.ZERO;
    
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @Column(nullable = false)
    private Person owner;
    
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
