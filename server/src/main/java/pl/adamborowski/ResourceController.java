package pl.adamborowski;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/resources")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Resource> resources() {
        return resourceService.getResources();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Resource addResource(@RequestBody Resource resource) {
        resourceService.addResource(resource);
        return resource;
    }

    @RequestMapping(value = "/{resourceName}", method = RequestMethod.DELETE)
    public void deleteResource(@PathVariable("resourceName") String resourceName) {
        resourceService.deleteResource(resourceName);
    }

    @RequestMapping(value = "/{resourceName}", method = RequestMethod.PUT)
    public void updateResource(@PathVariable("resourceName") String resourceName, @RequestBody Resource resource) {
        resourceService.updateResource(resourceName, resource);
    }
}
