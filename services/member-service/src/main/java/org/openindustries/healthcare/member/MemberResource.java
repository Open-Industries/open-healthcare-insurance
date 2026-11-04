package org.openindustries.healthcare.member;

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

@Path("/api/v1/members")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Members", description = "Member registry; the member number is masked at enrolment and never kept in clear.")
public class MemberResource {

    @Inject
    MemberService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a member")
    public Response create(@Valid CreateMemberRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.create(request)).build();
    }

    @GET
    @Operation(summary = "List all")
    public List<Member> list() {
        return service.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public Member get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/suspend")
    @Operation(summary = "Suspend this member (lifecycle transition, no body)")
    public Member suspend(@PathParam("id") UUID id) {
        return service.suspend(id);
    }

    @POST
    @Path("/{id}/terminate")
    @Operation(summary = "Terminate this member (lifecycle transition, no body)")
    public Member terminate(@PathParam("id") UUID id) {
        return service.terminate(id);
    }
}
