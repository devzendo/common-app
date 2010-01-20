package org.devzendo.minimiser.springloader;

import java.util.Arrays;
import java.util.List;

import org.devzendo.minimiser.MiniMiserApplicationContexts;


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
