package pl.adamborowski.store;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(of = "id")
public class ItemStore {
    @Getter
    private String id;
    @Getter
    private String name;
    @Getter
    private int price;
    @Getter
    private int sum;
    private Map<String, Integer> deltas;

    public ItemStore(String id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
        sum = 0;
        deltas = new HashMap<>();
    }


    public void updateDeviceDelta(String deviceId, Integer delta) {
        if (delta == 0) {
            deltas.remove(deviceId);
        } else {
            deltas.put(deviceId, delta);
        }
        sum = deltas.values().stream().mapToInt(i -> i).sum();
    }

    public Integer getDeviceDelta(String deviceId) {
        if (deltas.containsKey(deviceId)) {
            return deltas.get(deviceId);
        }
        return 0; // this device doesn't know anything about this resource
    }

    public void updatePrice(int price) {
        this.price = price;
    }
}
