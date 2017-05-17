/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banking.app.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Tomas
 */
@Entity
public class Accepts {
    @Id
    @ManyToOne
    @JoinColumn(name="id_card_type")
    private CardType card_type;
    
    @Id
    @ManyToOne
    @JoinColumn(name="id_payment_place")
    private PaymentPlace payment_place;
    
    public Accepts(){
        
    }
}
