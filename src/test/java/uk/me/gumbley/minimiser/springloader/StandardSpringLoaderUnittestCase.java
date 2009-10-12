package uk.me.gumbley.minimiser.springloader;

import java.util.Arrays;
import java.util.List;

import uk.me.gumbley.minimiser.MiniMiserApplicationContexts;

/**
 * A SpringLoader unit test case that uses the MiniMiser
 * framework application contexts.
 * 
 * @author matt
 *
 */
public abstract class StandardSpringLoaderUnittestCase extends SpringLoaderUnittestCase {
    /**
     * {@inheritDoc}
     */
    @Override
    protected final List<String> getRuntimeApplicationContexts() {
        return Arrays.asList(MiniMiserApplicationContexts.getApplicationContexts());
    }
}
