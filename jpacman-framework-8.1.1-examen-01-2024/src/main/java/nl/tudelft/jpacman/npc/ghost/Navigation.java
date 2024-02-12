package nl.tudelft.jpacman.npc.ghost;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;

public final class Navigation {

    private Navigation() {
    }

    public static List<Direction> shortestPath(Square a, Square b,
                                               Unit c) {
        if (a.equals(b)) {
            return new ArrayList<>();
        }

        List<Node> d = new ArrayList<>();
        Set<Square> e = new HashSet<>();
        d.add(new Node(null, a, null));
        while (!d.isEmpty()) {
            Node f = d.remove(0);
            Square g = f.getSquare();
            if (g.equals(b)) {
                return f.getPath();
            }
            e.add(g);
            // Add new targets
            for (Direction h : Direction.values()) {
                Square i = g.getSquareAt(h);
                if (!e.contains(i)
                    && (c == null || i.isAccessibleTo(c))) {
                    d.add(new Node(h, i, f));
                }
            }
        }
        return null;
    }

    public static Unit findNearest(Class<? extends Unit> a,
                                   Square b) {
        List<Square> c = new ArrayList<>();
        Set<Square> d = new HashSet<>();

        c.add(b);

        while (!c.isEmpty()) {
            Square e = c.remove(0);
            // Find units
            Unit f = null;
            for (Unit g : e.getOccupants()) {
                if (a.isInstance(g)) {
                    assert g.hasSquare();
                    f = g;
                }
            }

            if (f != null) {
                assert f.hasSquare();
                return f;
            }
            d.add(e);
            for (Direction h : Direction.values()) {
                Square i = e.getSquareAt(h);
                if (!d.contains(i) && !c.contains(i)) {
                    c.add(i);
                }
            }
        }
        return null;
    }

    private static final class Node {

        private final Direction x;
        private final Node y;
        private final Square z;

        Node(Direction x, Square y, Node z) {
            this.x = x;
            this.z = y;
            this.y = z;
        }

        private Direction getDirection() {
            return x;
        }

        private Square getSquare() {
            return z;
        }

        private Node getParent() {
            return y;
        }

        private List<Direction> getPath() {
            if (y == null) {
                return new ArrayList<>();
            }
            List<Direction> a = y.getPath();
            a.add(getDirection());
            return a;
        }
    }
}
