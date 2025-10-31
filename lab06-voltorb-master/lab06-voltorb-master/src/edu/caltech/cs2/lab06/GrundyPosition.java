package edu.caltech.cs2.lab06;

import java.util.*;

public class GrundyPosition {
    /*
     * Stores a mapping from the height of a pile to how many of those piles exist.
     * Does not include piles of size less than three.
     */
    private SortedMap<Integer, Integer> heapCounts;
    public static HashMap<GrundyPosition, Boolean> memo = new HashMap<>();

    /**
     * Initializes a GrundyPosition with a single heap of height heapHeight.
     **/
    public GrundyPosition(int heapHeight) {
        heapCounts = new TreeMap<>();
        heapCounts.put(heapHeight, 1);
        heapCounts.remove(0);
        heapCounts.remove(1);
        heapCounts.remove(2);
    }

    /**
     * Returns a list of legal GrundyPositions that a single move of Grundy's Game
     * can get to.
     **/
    public List<GrundyPosition> getMoves() {
        List<GrundyPosition> poss = new ArrayList<>();
        for(int height : this.heapCounts.keySet()) {
            for(int i = 1; i < (height + 1) / 2; i++) {
                GrundyPosition opt = new GrundyPosition(height);
                opt.heapCounts = new TreeMap<>(this.heapCounts);
                if (opt.heapCounts.get(height) - 1 > 0) {
                    opt.heapCounts.put(height, heapCounts.get(height) - 1);
                } else opt.heapCounts.remove(height);
                if (height - 1 > 2) {
                    if (heapCounts.get(height - i) != null) {
                        opt.heapCounts.put(height - i, heapCounts.get(height - i) + 1);
                    } else opt.heapCounts.put(height - i, 1);
                }
                if (i > 2) {
                    if (heapCounts.get(i) != null) {
                        opt.heapCounts.put(i, heapCounts.get(i) + 1);
                    }
                    else opt.heapCounts.put(i, 1);
            }
                poss.add(opt);
            }
        }
        return poss;
    }

    public boolean isTerminalPosition() {
        return heapCounts.isEmpty();
    }

    public boolean PPHelper(GrundyPosition curr) {
        for(GrundyPosition next : curr.getMoves()) {
            boolean isN = next.isNPosition();
            if(!isN) {
                memo.put(next, true);
                memo.put(curr, false);
                return false;
            }
        }
        memo.put(curr, false);
        return true;
    }

    public boolean NHelper(GrundyPosition curr) {
        for(GrundyPosition next : curr.getMoves()) {
            boolean yurt = next.isPPosition();
            if(yurt) {
                memo.put(next, true);
                memo.put(curr, false);
                return true;
            }
        }
        return false;
    }

    public boolean isPPosition() {
        if(memo.containsKey(this)) return memo.get(this);
        if(isTerminalPosition()) {
            memo.put(this, true);
            return true;
        }
        else return PPHelper(this);

    }

    public boolean isNPosition()  {
        if(isTerminalPosition()) return false;
        if(memo.containsKey(this)) return !memo.get(this);
        else return NHelper(this);

    }

    /**
     * Ignore everything below this point.
     **/

    @Override
    public int hashCode() {
       return this.heapCounts.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GrundyPosition)) {
            return false;
        }
        return this.heapCounts.equals(((GrundyPosition) o).heapCounts);
    }

    @Override
    public String toString() {
        return this.heapCounts.toString();
    }
}