package banking.app.entities;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(generator = "transaction_gen")
    @SequenceGenerator(name = "transaction_gen", sequenceName = "id_transaction_seq", allocationSize = 1)
    private Long id_transaction;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_card")
    private Card sender;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_account")
    private Account receiver;
    
    @Column(nullable = false)
    private BigDecimal amount;
    
    @Column(length = 100)
    private String messageToSender;
    
    @Column(length = 100)
    private String messageToReceiver;
    
    @Column(nullable=false)
    private Date date;

    public Transaction() {
    }

    public Transaction(Card sender, Account receiver, BigDecimal amount, Date date) {
        this(sender, receiver, amount, "", "", date);
    }

    public Transaction(Card sender, Account receiver, BigDecimal amount, String messageToSender, String messageToReceiver, Date date) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.messageToSender = messageToSender;
        this.messageToReceiver = messageToReceiver;
        this.date = date;
    }

    public SimpleStringProperty getSSSenderCardId(){
//        return new SimpleStringProperty(sender.getCard_type().getTypeName().toString());
        return new SimpleStringProperty(date.toString());
    }

    public SimpleLongProperty getSSAmount(){
        return new SimpleLongProperty(amount.longValue());
    }

    public SimpleStringProperty getSSACCID(){
        String tmp;
        if(receiver == null){
            tmp = "ATM";
        } else {
//            tmp = sender.getAccount().getIban();
            tmp = receiver.getIban();
        }
        return new SimpleStringProperty(tmp);
    }

    public SimpleStringProperty getSSAcountId(){

        return new SimpleStringProperty(sender.getAccount().getIban());
//        return new SimpleStringProperty(receiver.getIban());
    }


    public SimpleStringProperty getSSmessageToSender(){
        return new SimpleStringProperty(messageToSender);
    }

    public Long getId_transaction() {
        return id_transaction;
    }

    public void setId_transaction(Long id_transaction) {
        this.id_transaction = id_transaction;
    }

    public Card getSender() {
        return sender;
    }

    public void setSender(Card sender) {
        this.sender = sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMessageToSender() {
        return messageToSender;
    }

    public void setMessageToSender(String messageToSender) {
        this.messageToSender = messageToSender;
    }

    public String getMessageToReceiver() {
        return messageToReceiver;
    }

    public void setMessageToReceiver(String messageToReceiver) {
        this.messageToReceiver = messageToReceiver;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date dueDate) {
        this.date = dueDate;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (id_transaction != null ? !id_transaction.equals(that.id_transaction) : that.id_transaction != null)
            return false;
        if (sender != null ? !sender.equals(that.sender) : that.sender != null) return false;
        if (receiver != null ? !receiver.equals(that.receiver) : that.receiver != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (messageToSender != null ? !messageToSender.equals(that.messageToSender) : that.messageToSender != null)
            return false;
        if (messageToReceiver != null ? !messageToReceiver.equals(that.messageToReceiver) : that.messageToReceiver != null)
            return false;
        return date != null ? date.equals(that.date) : that.date == null;

    }

    @Override
    public int hashCode() {
        int result = id_transaction != null ? id_transaction.hashCode() : 0;
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (receiver != null ? receiver.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (messageToSender != null ? messageToSender.hashCode() : 0);
        result = 31 * result + (messageToReceiver != null ? messageToReceiver.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id_transaction=" + id_transaction +
                ", sender=" + sender.getId_card() +
                ", receiver=" + receiver.getId_account() +
                ", amount=" + amount +
                ", messageToSender='" + messageToSender + '\'' +
                ", messageToReceiver='" + messageToReceiver + '\'' +
                ", dueDate=" + date +
                '}';
    }
}
