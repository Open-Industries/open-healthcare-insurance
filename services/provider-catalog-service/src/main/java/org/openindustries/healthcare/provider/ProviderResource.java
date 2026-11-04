package org.openindustries.healthcare.provider;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Path("/api/v1/providers")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Providers", description = "Contracted provider network catalogue.")
public class ProviderResource {

    @Inject
    ProviderService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a provider")
    public Response create(@Valid CreateProviderRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.create(request)).build();
    }

    @GET
    @Operation(summary = "List all")
    public List<Provider> list() {
        return service.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public Provider get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/suspend")
    @Operation(summary = "Suspend (lifecycle transition, no body)")
    public Provider suspend(@PathParam("id") UUID id) {
        return service.suspend(id);
    }
}
