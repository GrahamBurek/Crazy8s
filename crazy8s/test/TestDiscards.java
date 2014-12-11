/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazy8s.test;

import crazy8s.card.Card;
import crazy8s.card.Deck;

/**
 *
 * @author Graham
 */
public class TestDiscards {
    public static void main(String[] args){
        Deck deck = new Deck();
        while(true){
            Card card = deck.draw();
            deck.discard(card);
        }
    }
}
