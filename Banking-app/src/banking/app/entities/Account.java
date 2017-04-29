package banking.app.entities;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Account {
    
    private static final BigDecimal DEFAULT_BALANCE = BigDecimal.ZERO;
    
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    //@Column(nullable = false)
    private Person owner;
    
    @OneToMany( targetEntity=Card.class )
    private List cardlist;
    
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
