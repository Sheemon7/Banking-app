package banking.app.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class CardType {
    
    @Id
    @GeneratedValue(generator="my_seq4")
    @SequenceGenerator(name="my_seq4",sequenceName="cardtype_id_card_type_seq", allocationSize=1)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 100)
    private String typeName;
    
    @OneToMany(mappedBy="card_type")
    private List<Card> cards;
    
    @OneToMany(mappedBy="card_type")
    private List<Accepts> acceptedAt;

    public CardType() {
    }
    
    public CardType(String typeName) {
        this.typeName = typeName;
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


    
}
