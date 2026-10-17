package ap.be;

import java.util.List;

import org.glassfish.jersey.inject.hk2.JerseyClassAnalyzer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/test")
public class AppController {

    private final TemplateEngine engine;

    /**
     * 
     * HK2's {@link JerseyClassAnalyzer#getConstructor(Class)} only accepts one of two things:
     * 1. A constructor annotated with {@link Inject} (jakarta.inject.Inject), or
     * 2. A no-arg (default) constructor.
     * 
     */
    @Inject
    public AppController(TemplateEngine engine) {
        this.engine = engine;
    }

    @GET
    public String test() {
        Context context = new Context();
        context.setVariable("name", "yazan");
        String html = engine.process(Template.HOME.getFileName(), context);
        return html;
    }

    @GET
    @Path("{potato}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response potato(@PathParam("potato") String name) {
        return Response.ok(name).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public List<?> add(@FormParam("name") String name) {
        App.somethings.add(name);
        return App.somethings;
    }
}
