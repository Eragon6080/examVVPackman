package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;

/**
 * Een tabel met alle (relevante) botsingen tussen verschillende typen
 * eenheden.
 *
 * @auteur Jeroen Roosen
 */
public interface CollisionMap {

    /**
     * Laat de twee eenheden in botsing komen en handelt het resultaat van de botsing af, wat mogelijk is
     * helemaal niets zijn.
     *
     * @param <C1>   Het botstype.
     * @param <C2>   Het botsende type (eenheid waarin is verplaatst).
     * @param botser De eenheid die de botsing veroorzaakt door een veld te bezetten
     *               er staat al een ander apparaat op.
     * @param botst  De eenheid die zich al op het veld bevindt dat wordt binnengevallen.
     */
    <C1 extends Unit, C2 extends Unit> void collide(C1 botser, C2 botst);

}
