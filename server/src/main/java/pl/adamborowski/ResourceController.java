package pl.adamborowski;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/resources")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    private String getUser(String auth) {
        return new String(Base64.getDecoder().decode(auth), StandardCharsets.UTF_8).split(":")[0];
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Resource> resources(@RequestHeader(value = "Authorization") String auth) {

        return resourceService.getResources(getUser(auth));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Resource addResource(@RequestBody Resource resource, @RequestHeader(value = "Authorization") String auth) {
        resourceService.addResource(resource, getUser(auth));
        return resource;
    }

    @RequestMapping(value = "/{resourceName}", method = RequestMethod.DELETE)
    public void deleteResource(@PathVariable("resourceName") String resourceName, @RequestHeader(value = "Authorization") String auth) {
        resourceService.deleteResource(resourceName, getUser(auth));
    }

    @RequestMapping(value = "/{resourceName}", method = RequestMethod.PUT)
    public void updateResource(@PathVariable("resourceName") String resourceName, @RequestBody Resource resource, @RequestHeader(value = "Authorization") String auth) {
        resourceService.updateResource(resourceName, resource, getUser(auth));
    }
}
