package pl.adamborowski;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aborowski on 02.11.2015.
 */
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
    private Map<String,List<Resource>> list;

    public ResourceServiceImpl() {
        list = new HashMap<>();
    }

    private List<Resource> getList(String user){
        if(!list.containsKey(user)){
            list.put(user, new ArrayList<>());
        }
        return list.get(user);
    }

    @Override
    public List<Resource> getResources(String user) {

        return getList(user);
    }

    @Override
    public void addResource(Resource resource, String user) {
        getList(user).add(resource);
    }

    public Resource getResourceByName(String name, String user) {
        for (Resource item : getList(user)) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public void deleteResource(String resourceName, String user) {
        getList(user).remove(getResourceByName(resourceName, user));
    }

    @Override
    public void updateResource(String resourceName, Resource resource, String user) {
        getList(user).remove(getResourceByName(resourceName, user));
        getList(user).add(resource);
    }


}
