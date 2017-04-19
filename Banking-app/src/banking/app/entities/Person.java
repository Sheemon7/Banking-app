package banking.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable=false)
    private String firstName;
    
    @Column(nullable=false)
    private String secondName;
    
    @Column(nullable=false)
    private int pin; //personal_identification_number
    
    @Column(nullable=false)
    String adress;

    public Person(String firstName, String secondName, int pin, String adress) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.pin = pin;
        this.adress = adress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getPid() {
        return pin;
    }

    public void setPid(int pin) {
        this.pin = pin;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
    
    
    
    
}
