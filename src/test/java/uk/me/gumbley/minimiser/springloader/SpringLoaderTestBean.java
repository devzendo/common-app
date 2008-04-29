package uk.me.gumbley.minimiser.springloader;

import org.apache.log4j.Logger;

public class SpringLoaderTestBean {
    private static final Logger LOGGER = Logger.getLogger(SpringLoaderTestBean.class);
    public SpringLoaderTestBean() {
        LOGGER.info("Hello from SpringLoaderTestBean");
    }
}
