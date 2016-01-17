package pl.adamborowski.store;

import pl.adamborowski.exception.ItemException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserStore {
    private Map<String, ItemStore> itemsByName = new HashMap<>();
    private Map<String, ItemStore> itemsById = new HashMap<>();
    private List<ItemStore> items = new ArrayList<>();

    private ItemStore getItemStore(String id) {
        if (itemsById.containsKey(id)) {
            return itemsById.get(id);
        }
        return null;
    }

    private ItemStore getItemStoreByName(String name) {
        if (itemsByName.containsKey(name)) {
            return itemsByName.get(name);
        }
        return null;
    }

    private ItemStore createItemStore(String id, String name, int price) {
        ItemStore newStore = new ItemStore(id, name, price);
        items.add(newStore);
        itemsById.put(id, newStore);
        itemsByName.put(name, newStore);
        return newStore;
    }

    private void removeItemStore(String id) {
        ItemStore itemStore = itemsById.get(id);
        itemsById.remove(id);
        itemsByName.remove(itemStore.getName());
        items.remove(itemStore);
    }

    public List<ItemStore> getItems() {
        return items;
    }

    public ItemStore updateItemDeviceDelta(String id, String deviceId, Integer delta) throws ItemException {
        ItemStore itemStore = getItemStore(id);
        if (itemStore == null) {
            throw new ItemException(ItemException.Cause.GUID_NOT_FOUND, "Cannot update item, the item was deleted on another device");
        }
        itemStore.updateDeviceDelta(deviceId, delta);
        return itemStore;
    }

    public void requestDeleteItem(String id, String name, String deviceId) throws ItemException {
        // find the requested item
        ItemStore itemStore = getItemStore(id);
        if (itemStore != null) {
            // if the item exists, just delete it
            removeItemStore(id);
        } else {
            ItemStore anotherItem = getItemStoreByName(name);
            if (anotherItem != null) {
                // wanted to delete item but another device has already deleted that item on servier and recreated.
                throw new ItemException(ItemException.Cause.GUID_ALREADY_DELETED_NAME_RECREATED, "Cannot delete item, the item was deleted and recreated on another device");
            }
            throw new ItemException(ItemException.Cause.GUID_ALREADY_DELETED, "Cannot delete item, the item was deleted on another device");
        }
    }

    public void requestCreateItem(String id, String name, String deviceId, int delta, int price) throws ItemException {
        ItemStore itemStore = getItemStore(id);
        if (itemStore != null) {
            // very rare situation, guid should be generated strong randomly
            throw new ItemException(ItemException.Cause.GUID_ALREADY_EXISTS, "Cannot create item, the same GUID exists");
        } else {
            ItemStore anotherItem = getItemStoreByName(name);
            if (anotherItem != null) {
                throw new ItemException(ItemException.Cause.NAME_ALREADY_EXISTS, "Cannot create item, the item of that name was created on another device");
            }
            // no other item of that name
            createItemStore(id, name, price).updateDeviceDelta(deviceId, delta);
        }
    }

    public ItemStore updateItemPrice(String id, int price) throws ItemException {
        ItemStore itemStore = getItemStore(id);
        if (itemStore == null) {
            throw new ItemException(ItemException.Cause.GUID_NOT_FOUND, "Cannot update item, the item was deleted on another device");
        }
        itemStore.updatePrice(price);
        return itemStore;
    }
}
