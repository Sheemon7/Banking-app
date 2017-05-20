package banking.app.entities;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="person")
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
    private String address;

    @OneToMany(mappedBy="owner", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Account> accounts;

    @OneToMany(mappedBy="person", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Trader> traders;

    public Person() {}

    public Person(String firstName, String secondName, int pin, String adress) {
//        this.id_person = id_person;
        this.first_name = firstName;
        this.second_name = secondName;
        this.pin = pin;
        this.address = adress;
    }

    public Long getId_person() {
        return id_person;
    }

    public void setId_person(Long id_person) {
        this.id_person = id_person;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Trader> getTraders() {
        return traders;
    }

    public void setTraders(List<Trader> traders) {
        this.traders = traders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (pin != person.pin) return false;
        if (id_person != null ? !id_person.equals(person.id_person) : person.id_person != null) return false;
        if (first_name != null ? !first_name.equals(person.first_name) : person.first_name != null) return false;
        if (second_name != null ? !second_name.equals(person.second_name) : person.second_name != null) return false;
        if (address != null ? !address.equals(person.address) : person.address != null) return false;
        if (accounts != null ? !accounts.equals(person.accounts) : person.accounts != null) return false;
        return traders != null ? traders.equals(person.traders) : person.traders == null;

    }

    @Override
    public int hashCode() {
        int result = id_person != null ? id_person.hashCode() : 0;
        result = 31 * result + (first_name != null ? first_name.hashCode() : 0);
        result = 31 * result + (second_name != null ? second_name.hashCode() : 0);
        result = 31 * result + pin;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id_person=" + id_person +
                ", first_name='" + first_name + '\'' +
                ", second_name='" + second_name + '\'' +
                ", pin=" + pin +
                ", adress='" + address + '\'' +
                '}';
    }
}
