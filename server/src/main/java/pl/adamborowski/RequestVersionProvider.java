package pl.adamborowski;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class RequestVersionProvider {

    @Autowired
    private HttpServletRequest request;

    public int getRequestVersion()
    {
        if(request.getParameter("v")==null){
            return 1;
        }
        return new Integer(request.getParameter("v"));
    }
    public boolean isVersion(int version){
        return getRequestVersion() == version;
    }
}
