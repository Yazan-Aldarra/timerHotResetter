package ap.be;

import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.thymeleaf.TemplateEngine;

public class JettyServer {

    public void start() {
        int port = 8080;
        Server server = new Server(port);
        try {
            ResourceConfig config = new ResourceConfig();
            config.register(AppController.class);

            TemplateEngine engine = ThymeleafConfig.createEngine();
            // Inserting a subclass of AbstractBinder allow us to handle depency injection through out our registerd controllers.
            // Note: the sytanx bellow is called Anonymous class it allow us to extend a AbstractBinder without creating new class.
            config.register(new AbstractBinder() {
                @Override
                protected void configure() {
                    bind(engine).to(TemplateEngine.class);
                }
            });

            // 3. Create Servlet Context with Jersey
            ServletContextHandler handler = new ServletContextHandler();
            handler.addServlet(new ServletHolder(new ServletContainer(config)), "/*");

            // 4. Link Handler to Server
            server.setHandler(handler);

            // start
            server.start();
            System.out.println("Server started on port " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
