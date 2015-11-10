package pl.adamborowski;

import java.util.List;

public interface ResourceService {




    List<Resource> getResources(String user);

    void addResource(Resource resource, String user);

    void deleteResource(String resourceId, String user);


    void updateResource(String resourceName, Resource resource, String user);
}
