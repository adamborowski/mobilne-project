package pl.adamborowski;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("resourceService")
public class DefaultResourceService implements ResourceService {
    @EqualsAndHashCode(of = "name")
    private static class ItemStore {
        @Getter
        private String name;
        @Getter
        private int sum;
        private Map<String, Integer> deltas;

        public ItemStore(String name) {
            this.name = name;
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
    }

    private static class UserStore {
        private Map<String, ItemStore> itemsByName = new HashMap<>();
        private List<ItemStore> items = new ArrayList<>();

        private ItemStore getItemStore(String name) {

            if (itemsByName.containsKey(name)) {
                return itemsByName.get(name);
            }
            ItemStore newStore = new ItemStore(name);
            items.add(newStore);
            itemsByName.put(name, newStore);
            return newStore;
        }

        private void removeItemStore(String name) {
            ItemStore itemStore = itemsByName.get(name);
            if (itemStore != null) {
                itemsByName.remove(name);
                items.remove(itemStore);
            }
        }

        public List<ItemStore> getItems() {
            return items;
        }

        public ItemStore updateItemDeviceDelta(String name, String deviceId, Integer delta) {
            ItemStore itemStore = getItemStore(name);
            itemStore.updateDeviceDelta(deviceId, delta);
//            if (itemStore.getSum() <= 0) {//synchronization error
//                removeItemStore(name);
//                return null;
//            }
            return itemStore;
        }
    }

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
    public List<Resource> syncResources(BatchSyncData data, String user) {
        final UserStore store = getStoreForUser(user);
        final String deviceId = data.getDeviceId();
        for (BatchSyncData.DeviceItemDelta item : data.getDeltas()) {
            store.updateItemDeviceDelta(item.getName(), deviceId, item.getDelta());
        }
        final List<Resource> result = new ArrayList<>();
        for (ItemStore item : store.getItems()) {
            Resource resource = new Resource();
            resource.setName(item.name);
            resource.setDelta(item.getDeviceDelta(deviceId));
            resource.setSum(item.getSum());
            result.add(resource);
        }
        return result;
    }
}
