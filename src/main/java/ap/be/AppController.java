package ap.be;

import java.util.Map;

import org.glassfish.jersey.inject.hk2.JerseyClassAnalyzer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;

@Path("/test")
public class AppController {

    private final TemplateEngine engine;

    /**
     * 
     * HK2's {@link JerseyClassAnalyzer#getConstructor(Class)} only accepts one of
     * two things:
     * 1. A constructor annotated with {@link Inject} (jakarta.inject.Inject), or
     * 2. A no-arg (default) constructor.
     * 
     */
    @Inject
    public AppController(TemplateEngine engine) {
        this.engine = engine;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String home() {
        String html = engine.process(Template.HOME.getFileName(), new Context());
        return html;
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public String timer(MultivaluedMap<String, String> fromParam) {

        Context context = new Context();

        context.setVariable("names", fromParam.get("name"));
        context.setVariable("timeInSec", fromParam.get("timeInSec").get(0));
        String html = engine.process(Template.TIMER.getFileName(), context);

        return html;
    }
}
