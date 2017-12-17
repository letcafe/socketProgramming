import org.junit.Test;

import java.net.*;
import java.util.Enumeration;

public class LocalIpAddress {

    @Test
    public void getIp() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        String ip=addr.getHostAddress().toString();//获得本机IP
        System.out.println(ip);
    }
}
