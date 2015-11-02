package pl.adamborowski;

import org.springframework.stereotype.Component;

@Component
public class Resource {
    private String name;
    private int value;

    public Resource() {
    }

    public Resource(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
