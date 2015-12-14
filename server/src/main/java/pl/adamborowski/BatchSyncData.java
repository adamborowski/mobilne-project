package pl.adamborowski;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by aborowski on 14.12.2015.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BatchSyncData {
    private String deviceId;
    private List<DeviceItemDelta> deltas = null;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class DeviceItemDelta {
        private String name;
        private int delta;
    }
}
