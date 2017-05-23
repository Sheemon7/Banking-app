package banking.app.entities;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "card_type")
public class CardType {

    @Id
    @GeneratedValue(generator = "card_type_gen")
    @SequenceGenerator(name = "card_type_gen", sequenceName = "id_card_type_seq", allocationSize = 1)
    private Long id_card_type;
    
    @Column(nullable = false, unique = true, length = 100)
    private String typeName;
    
    @OneToMany(mappedBy = "card_type")
    private List<Card> cards;
    
    @ManyToMany(mappedBy = "accepts")
    private List<PaymentPlace> acceptedAt;

    public CardType() {
    }
    
    public Long getId_card_type() {
        return id_card_type;
    }

    public void setId_card_type(Long id) {
        this.id_card_type = id;
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

        if (id_card_type != null ? !id_card_type.equals(cardType.id_card_type) : cardType.id_card_type != null) return false;
        if (typeName != null ? !typeName.equals(cardType.typeName) : cardType.typeName != null) return false;
        if (cards != null ? !cards.equals(cardType.cards) : cardType.cards != null) return false;
        return acceptedAt != null ? acceptedAt.equals(cardType.acceptedAt) : cardType.acceptedAt == null;

    }

    @Override
    public int hashCode() {
        int result = id_card_type != null ? id_card_type.hashCode() : 0;
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        result = 31 * result + (cards != null ? cards.hashCode() : 0);
        result = 31 * result + (acceptedAt != null ? acceptedAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CardType{" +
                "id=" + id_card_type +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
