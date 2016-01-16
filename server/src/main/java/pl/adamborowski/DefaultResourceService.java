package pl.adamborowski;

import org.springframework.stereotype.Service;
import pl.adamborowski.data.BatchSyncData;
import pl.adamborowski.data.BatchSyncResult;
import pl.adamborowski.exception.ItemException;
import pl.adamborowski.store.ItemStore;
import pl.adamborowski.store.UserStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//todo wyzerowanie na zero a nie na deltach, usuwanie: idempotentne
//oddać przy kolejnym etapie
//następny etap: ceny dla produktów (najnowsza cena wygrywa), kategorie produktów i możliwość, które kategorie widzimy per device (na tablecie tylko rtv i spozywcze)


@Service("resourceService")
public class DefaultResourceService implements ResourceService {

    private Map<String, UserStore> perUserStore = new HashMap<>();


    private UserStore getStoreForUser(String user) {
        UserStore userStore = perUserStore.get(user);
        if (userStore == null) {
            userStore = new UserStore();
            perUserStore.put(user, userStore);
        }
        return userStore;
    }







    private Map<String,List<Resource>> list;

    public DefaultResourceService() {
        list = new HashMap<>();
    }

    private List<Resource> getStore(String user) {
        if(!list.containsKey(user)){
            list.put(user, new ArrayList<>());
        }
        return list.get(user);
    }

    @Override
    public List<Resource> getResources(String user) {

        return getStore(user);
    }

    @Override
    public void addResource(Resource resource, String user) {
        getStore(user).add(resource);
    }

    public Resource getResourceByName(String name, String user) {
        for (Resource item : getStore(user)) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public void deleteResource(String resourceName, String user) {
        getStore(user).remove(getResourceByName(resourceName, user));
    }

    @Override
    public void updateResource(String resourceName, Resource resource, String user) {
        getStore(user).remove(getResourceByName(resourceName, user));
        getStore(user).add(resource);
    }

    @Override
    public BatchSyncResult syncResources(BatchSyncData data, String user){
        final UserStore store = getStoreForUser(user);
        final String deviceId = data.getDeviceId();
        BatchSyncResult result = new BatchSyncResult();
        for (BatchSyncData.DeviceItemDelta item : data.getDeltas()) {
            try {
                if (item.isCreateRequested()) {
                    store.requestCreateItem(item.getId(), item.getName(), deviceId, item.getDelta());
                } else if (item.isDeleteRequested()) {
                    store.requestDeleteItem(item.getId(), item.getName(), deviceId);
                } else {
                    store.updateItemDeviceDelta(item.getId(), deviceId, item.getDelta());
                }
            } catch (ItemException e) {
                result.getErrors().add(new BatchSyncResult.SingleOperationException(item.getId(), e.getErrorCode(), e.getMessage()));
            }
        }
        for (ItemStore item : store.getItems()) {
            Resource resource = new Resource();
            resource.setName(item.getName());
            resource.setDelta(item.getDeviceDelta(deviceId));
            resource.setSum(item.getSum());
            result.getResources().add(resource);
        }
        return result;
    }
}
