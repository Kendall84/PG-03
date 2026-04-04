package ucr.algoritmos.tarea01;

/**
 * Arithmetic progression.
 */
public class ArithmeticProgression extends Progression {
    protected long increment;

    public ArithmeticProgression() {
        this(1, 0);
    }

    public ArithmeticProgression(long stepsize) {
        this(stepsize, 0);
    }

    public ArithmeticProgression(long stepsize, long start) {
        super(start);
        increment = stepsize;
    }

    @Override
    protected void advance() {
        current += increment;
    }

    @Override
    public void reset() {
        current = 0; // Or starting value if we stored it
    }
}
