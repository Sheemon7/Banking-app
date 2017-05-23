package banking.app.entities;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "Account.getAverageBalance",
                query = "SELECT AVG(a.balance) FROM Account a"
        ),
        @NamedQuery(
                name = "Account.getNumberOfRich",
                query = "SELECT COUNT(a) FROM Account a WHERE a.balance > :threshold"
        )
})
@Table(name = "account")
public class Account {

    private static final BigDecimal DEFAULT_BALANCE = BigDecimal.ZERO;

    @Id
    @GeneratedValue(generator = "account_gen")
    @SequenceGenerator(name = "account_gen", sequenceName = "id_account_seq", allocationSize = 1)
    private Long id_account;

    @Column
    private String iban;

    @Column(nullable = false)
    private String salt;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_owner")
    private Person owner;

    @OneToMany(mappedBy = "account")
    private List<Card> cards;

    @Column(nullable = false)
    private BigDecimal balance;

    public Account() {
    }

    public Account(String password, Person owner) {
        this(password, owner, DEFAULT_BALANCE);
    }

    public Account(String password, Person owner, BigDecimal balance) {
        final Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt);
        // POSTGRES DOESN'T ALLOW INSERTION OF \0000 - REPLACE IT
        this.salt = replaceAllNulls(new String(salt, StandardCharsets.UTF_8));
        this.password = getSaltyPasswordHash(password);
        this.owner = owner;
        this.balance = balance;
    }

    public String getSaltyPasswordHash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest((password + this.salt).getBytes("UTF-8"));

            String hash = new String(bytes, StandardCharsets.UTF_8);
            // POSTGRES DOESN'T ALLOW INSERTION OF \0000 - REPLACE IT
            return replaceAllNulls(hash);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            return null;
        }
    }

    public Long getId_account() {
        return id_account;
    }

    public void setId_account(Long id_account) {
        this.id_account = id_account;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (id_account != null ? !id_account.equals(account.id_account) : account.id_account != null) return false;
        if (iban != null ? !iban.equals(account.iban) : account.iban != null) return false;
        if (salt != null ? !salt.equals(account.salt) : account.salt != null) return false;
        if (password != null ? !password.equals(account.password) : account.password != null) return false;
        if (owner != null ? !owner.equals(account.owner) : account.owner != null) return false;
        if (cards != null ? !cards.equals(account.cards) : account.cards != null) return false;
        return balance != null ? balance.equals(account.balance) : account.balance == null;

    }

    @Override
    public int hashCode() {
        int result = id_account != null ? id_account.hashCode() : 0;
        result = 31 * result + (iban != null ? iban.hashCode() : 0);
        result = 31 * result + (salt != null ? salt.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (cards != null ? cards.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id_account=" + id_account +
                ", iban='" + iban + '\'' +
                ", owner=" + owner +
                ", balance=" + balance +
                '}';
    }

    private static String replaceAllNulls(String s) {
        StringBuilder result = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c == 0x00) {
                result.append("a");
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
