package org.openindustries.healthcare.coverage;

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

@Path("/api/v1/coverage")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Coverage", description = "Guarded coverage-limit accounts per member and the claim payments against them.")
public class CoverageResource {

    @Inject
    CoverageService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Open a coverageaccount")
    public Response open(@Valid OpenCoverageAccountRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.open(request)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public CoverageAccount get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/payments")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Draw from the account (rejects if it would overdraw, 422)")
    public ClaimPayment consume(@PathParam("id") UUID id, @Valid CreateClaimPaymentRequest request) {
        return service.consume(id, request);
    }

    @GET
    @Path("/{id}/payments")
    @Operation(summary = "List consumption records of an account")
    public List<ClaimPayment> records(@PathParam("id") UUID id) {
        return service.recordsOf(id);
    }
}
