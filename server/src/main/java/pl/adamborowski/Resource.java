package pl.adamborowski;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Resource {
    private String name;
    private String id;
    private int sum;
    private int delta;
    private int price;
    private String category;
}
