package ucr.algoritmos.tarea01;

/**
 * A class for numeric progressions.
 */
public abstract class Progression {
    protected long current;

    public Progression() {
        this(0);
    }

    public Progression(long start) {
        current = start;
    }

    /**
     * Returns the next value of the progression.
     */
    public long nextValue() {
        long answer = current;
        advance(); // this is a template method
        return answer;
    }

    /**
     * Advances the current value to the next value of the progression.
     */
    protected abstract void advance();

    /**
     * Resets the progression to the first value and returns it.
     */
    public abstract void reset();
}
