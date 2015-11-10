package pl.adamborowski;

import java.util.List;

public interface ResourceService {


    List<Resource> getResources();

    void addResource(Resource resource);

    void deleteResource(String resourceId);


    void updateResource(String resourceName, Resource resource);
}
