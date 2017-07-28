package test;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.Test;

/**
 * Created by wurenzhi on 2016/12/26.
 */
public class TicketServer {

    public static void main(String [] args){
        Server server = new Server(8085);
        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        context.setDescriptor("./ticket-web/src/main/webapp/WEB-INF/web.xml");
        context.setResourceBase("./ticket-web/src/main/webapp");
        context.setParentLoaderPriority(true);
        server.setHandler(context);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test(){
        int count = 0;
        for(int i=0;i <10;i++){
            count = count++;
        }
        System.out.println(count);
    }
}
