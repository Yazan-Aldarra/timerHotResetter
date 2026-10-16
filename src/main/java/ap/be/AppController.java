package ap.be;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/test")
public class AppController {

    public AppController() {

    }

    @GET
    public Response test() {
        return Response.ok(App.names).build();
    }

    @GET
    @Path("test/{name}")
    public Response potato(@PathParam("name") String name) {
        return Response.ok(name).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(String name) {
        App.names.add(name);
        return Response.ok(App.names).build();
    }
}
