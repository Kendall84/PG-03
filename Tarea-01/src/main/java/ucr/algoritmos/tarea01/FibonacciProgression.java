package ucr.algoritmos.tarea01;

/**
 * Fibonacci progression.
 */
public class FibonacciProgression extends Progression {
    protected long prev;

    /**
     * Constructs traditional Fibonacci, starting 0, 1, 1, 2, 3, ...
     */
    public FibonacciProgression() {
        this(0, 1);
    }

    /**
     * Constructs Fibonacci with first and second values.
     */
    public FibonacciProgression(long first, long second) {
        super(first);
        prev = second - first; // fictitious value preceding the first
    }

    /**
     * Advances the current value to the next value of the progression.
     */
    @Override
    protected void advance() {
        long temp = prev;
        prev = current;
        current += temp;
    }

    @Override
    public void reset() {
        current = 0;
        prev = 1;
    }
}
