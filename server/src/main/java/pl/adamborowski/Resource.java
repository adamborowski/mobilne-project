package pl.adamborowski;

import org.springframework.stereotype.Component;

@Component
public class Resource {
    private String name;
    private int count;

    public Resource() {
    }

    public Resource(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int value) {
        this.count = value;
    }
}
