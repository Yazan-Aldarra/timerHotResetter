package ap.be;

import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class JettyServer {

    public void start() {
        int port = 8080;
        Server server = new Server(port);
        try {
            ResourceConfig config = new ResourceConfig();
            config.register(AppController.class);
            // 3. Create Servlet Context with Jersey
            ServletContextHandler handler = new ServletContextHandler();
            handler.addServlet(new ServletHolder(new ServletContainer(config)), "/*");

            // 4. Link Handler to Server
            server.setHandler(handler);

            // start
            server.start();
            System.out.println("Server started on port "+ port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
