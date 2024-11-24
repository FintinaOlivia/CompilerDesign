package DataStructures;

import java.util.Objects;

public class Pair<TFirst, TSecond> {
    private final TFirst firstTerm;
    private final TSecond secondTerm;

    public Pair(TFirst first, TSecond second) {
        this.firstTerm = first;
        this.secondTerm = second;
    }

    public TFirst getFirstTerm() {
        return this.firstTerm;
    }

    public TSecond getSecondTerm() {
        return this.secondTerm;
    }

    @Override
    public String toString() {
        return "{"+ firstTerm
                + ", " + secondTerm
                + '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()){
            return false;
        }

        Pair<?, ?> pair = (Pair<?, ?>) object;
        return firstTerm.equals(pair.firstTerm) && secondTerm.equals(pair.secondTerm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstTerm, secondTerm);
    }
}
