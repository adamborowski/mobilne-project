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
}
