package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.sprite.PacManSprites;

/**
 * Factory that creates ghosts.
 *
 * @author Jeroen Roosen 
 */
public class GhostFactory {

    /**
     * The sprite store containing the ghost sprites.
     */
    private final PacManSprites sprites;
    private static final String GHOST_RED = "red";
    private static final String GHOST_PINK = "pink";
    private static final String GHOST_CYAN = "cyan";
    private static final String GHOST_ORANGE = "orange";

    /**
     * Creates a new ghost factory.
     *
     * @param spriteStore The sprite provider.
     */
    public GhostFactory(PacManSprites spriteStore) {
        this.sprites = spriteStore;
    }

    /**
     * Creates a new Blinky / Shadow, the red Ghost.
     *
     * @see Blinky
     * @return A new Blinky.
     */
    public Ghost createBlinky() {
        return new Blinky(sprites.getGhostSprite(GHOST_RED));
    }

    /**
     * Creates a new Pinky / Speedy, the pink Ghost.
     *
     * @see Pinky
     * @return A new Pinky.
     */
    public Ghost createPinky() {
        return new Pinky(sprites.getGhostSprite(GHOST_PINK));
    }

    /**
     * Creates a new Inky / Bashful, the cyan Ghost.
     *
     * @see Inky
     * @return A new Inky.
     */
    public Ghost createInky() {
        return new Inky(sprites.getGhostSprite(GHOST_CYAN));
    }

    /**
     * Creates a new Clyde / Pokey, the orange Ghost.
     *
     * @see Clyde
     * @return A new Clyde.
     */
    public Ghost createClyde() {
        return new Clyde(sprites.getGhostSprite(GHOST_ORANGE));
    }
}
