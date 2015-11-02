package pl.adamborowski;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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


}
