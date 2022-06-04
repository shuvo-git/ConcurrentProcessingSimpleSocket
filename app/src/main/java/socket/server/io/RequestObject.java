package socket.server.io;

import java.io.Serializable;
import java.util.Map;

/* *****************
    @Link https://stackoverflow.com/questions/1782598/with-java-reflection-how-to-instantiate-a-new-object-then-call-a-method-on-it
    Example of Invoke Method through Java Reflection
 * *****************/
public class RequestObject implements Serializable
{
    public String managerName;
    public String method;
    public Map<String, String> args;

    @Override
    public String toString() {
        return "RequestObject{" +
                "managerName='" + managerName + '\'' +
                ", method='" + method + '\'' +
                ", args=" + args +
                '}';
    }
}
