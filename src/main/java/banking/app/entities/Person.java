package banking.app.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Person {
    @Id
    @GeneratedValue(generator="my_seq")
    @SequenceGenerator(name="my_seq",sequenceName="person_id_person_seq", allocationSize=1)
    private Long id_person;
    
    @Column(nullable=false)
    private String first_name;
    
    @Column(nullable=false)
    private String second_name;
    
    @Column(nullable=false)
    private int pin; //personal_identification_number
    
    @Column(nullable=false)
    String adress;
    
    @OneToMany(mappedBy="owner")
    private List<Account> accounts;
    
    @OneToMany(mappedBy="person" )
    private List<Trader> traders;
    
    public Person(){}
    public Person(String firstName, String secondName, int pin, String adress) {
//        this.id_person = id_person;
        this.first_name = firstName;
        this.second_name = secondName;
        this.pin = pin;
        this.adress = adress;
    }

    public Long getId() {
        return id_person;
    }

    public void setId(Long id) {
        this.id_person = id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    public String getSecondName() {
        return second_name;
    }

    public void setSecondName(String secondName) {
        this.second_name = secondName;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
    
    
    
    
}
