package banking.app.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class PaymentPlace {   
    @Id
    @GeneratedValue(generator="my_seq2")
    @SequenceGenerator(name="my_seq2",sequenceName="paymentplace_id_paymnet_place_seq", allocationSize=1)
    private long id_payment_place;
    
    @OneToMany(mappedBy="payment_place")
    private List<Accepts> acceptedAt;
    
    @OneToOne(mappedBy="paymentPlace") 
    private ATM atm;
    
    @OneToOne(mappedBy="paymentPlace") 
    private Trader trader;

    public PaymentPlace() {
    }
    
   
    
    
    
    
    
  
    
}
