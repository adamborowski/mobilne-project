package pl.adamborowski;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.adamborowski.data.BatchSyncData;
import pl.adamborowski.data.BatchSyncResult;

import java.util.ArrayList;

@RestController
@RequestMapping("/resources")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    private String getUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public BatchSyncResult syncResources(@RequestBody BatchSyncData data) {

        return resourceService.syncResources(data, getUser()); // sync result for the browser
    }

    @RequestMapping(method = RequestMethod.GET)
    public BatchSyncResult getResources(@RequestParam String deviceId) {
        return resourceService.syncResources(new BatchSyncData(deviceId, new ArrayList<BatchSyncData.DeviceItemDelta>()), getUser()); // sync result for the browser
    }
}
