package frog.learn.spring.property;

import org.springframework.core.env.PropertySource;

import java.io.IOException;
import java.util.Properties;

public class FrogPropertySource extends PropertySource<Properties> {

    private static final String prefix = "frog.sch.";

    public FrogPropertySource() {
        super(prefix, new Properties());
        try {
            super.source.load(FrogPropertySource.class.getClassLoader().getResourceAsStream("frog-sch.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getProperty(String name) {
        if(!name.startsWith(prefix)){
            return null;
        }
        return this.source.getProperty(name);
    }
}
