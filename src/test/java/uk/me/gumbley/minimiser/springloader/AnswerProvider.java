package uk.me.gumbley.minimiser.springloader;

/**
 * Simple test interface for testing overriding by chained
 * app contexts.
 * @author matt
 *
 */
public interface AnswerProvider {
    /**
     * What does it mean, master?
     * @return some answer
     */
    int getTheAnswer();
}
