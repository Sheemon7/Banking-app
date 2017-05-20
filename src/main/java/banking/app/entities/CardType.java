package banking.app.entities;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "card_type")
public class CardType {
    
    @Id
    @GeneratedValue(generator="my_seq4")
    @SequenceGenerator(name="my_seq4",sequenceName="cardtype_id_card_type_seq", allocationSize=1)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 100)
    private String typeName;
    
    @OneToMany(mappedBy = "card_type", fetch = FetchType.EAGER)
    private List<Card> cards;
    
    @ManyToMany(mappedBy = "accepts", fetch = FetchType.EAGER)
    private List<PaymentPlace> acceptedAt;

    public CardType() {
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<PaymentPlace> getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(List<PaymentPlace> acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardType cardType = (CardType) o;

        if (id != null ? !id.equals(cardType.id) : cardType.id != null) return false;
        if (typeName != null ? !typeName.equals(cardType.typeName) : cardType.typeName != null) return false;
        if (cards != null ? !cards.equals(cardType.cards) : cardType.cards != null) return false;
        return acceptedAt != null ? acceptedAt.equals(cardType.acceptedAt) : cardType.acceptedAt == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        result = 31 * result + (cards != null ? cards.hashCode() : 0);
        result = 31 * result + (acceptedAt != null ? acceptedAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CardType{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
