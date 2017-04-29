package banking.app.entities;

import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    //@Column(nullable = false)
    private Person sender;
    
    @ManyToOne
    //@Column(nullable = false)
    private Person receiver;
    
    @Column(nullable = false)
    private BigDecimal amount;
    
    @Column(length=100)
    private String messageToSender;
    
    @Column(length = 100)
    private String messageToReceiver;
    
    @Column(nullable=false)
    private Date dueDate;

    public Transaction() {
    }

    public Transaction(Person sender, Person receiver, BigDecimal amount, Date dueDate) {
        this(sender, receiver, amount, "", "", dueDate);
    }

    public Transaction(Person sender, Person receiver, BigDecimal amount, String messageToSender, String messageToReceiver, Date dueDate) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.messageToSender = messageToSender;
        this.messageToReceiver = messageToReceiver;
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    
    
    
}
