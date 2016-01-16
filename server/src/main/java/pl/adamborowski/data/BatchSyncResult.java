package pl.adamborowski.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.adamborowski.Resource;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BatchSyncResult {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class SingleOperationException {
        String guid;
        int errorCode;
        String errorMessage;
    }

    public List<SingleOperationException> errors = new ArrayList<>();
    public List<Resource> resources = new ArrayList<>();
}
