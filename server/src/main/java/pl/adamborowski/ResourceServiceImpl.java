package pl.adamborowski;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aborowski on 02.11.2015.
 */
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
    private List<Resource> list;

    public ResourceServiceImpl() {
        list = new ArrayList<>();
    }

    @Override
    public List<Resource> getResources() {

        return list;
    }

    @Override
    public void addResource(Resource resource) {
        list.add(resource);
    }

    public Resource getResourceByName(String name) {
        for (Resource item : list) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public void deleteResource(String resourceName) {
        list.remove(getResourceByName(resourceName));
    }

    @Override
    public void updateResource(String resourceName, Resource resource) {
        list.remove(getResourceByName(resourceName));
        list.add(resource);
    }


}
