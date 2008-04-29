package uk.me.gumbley.minimiser.springloader;

public interface SpringLoader {
    <T> T getBean(String beanId, Class<T> beanType);
    void close();
}
