package banking.app.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class CardType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 100)
    private String typeName;
    
    @OneToMany(mappedBy = "cardType")
    private List<Card> cards;
    
    @ManyToMany
    private List<PaymentPlace> acceptedAt;

    public CardType() {
    }
    
    public CardType(String typeName) {
        this.typeName = typeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }    

    public List<PaymentPlace> getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(List<PaymentPlace> acceptedAt) {
        this.acceptedAt = acceptedAt;
    }
    
    
}
