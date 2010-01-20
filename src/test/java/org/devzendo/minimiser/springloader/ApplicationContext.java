/**
 * 
 */
package org.devzendo.minimiser.springloader;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation set at a type level that declares a set of Spring Application
 * Context XML files to be used when creating the SpringLoader.
 * 
 * @author matt
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ApplicationContext {
    String[] value();
}
