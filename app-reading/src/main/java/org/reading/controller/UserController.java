package org.reading.controller;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.reading.entity.UserEntity;
import org.reading.service.UserService;

import java.util.List;
import java.util.UUID;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GET
    public Response findAll(@QueryParam("page") @DefaultValue("0") Integer page,
                                    @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        var users = userService.findAll(page, pageSize);
        return Response.ok(users).build();
    }

    @GET
    @Path("/all")
    public  Response findAllUsers(){
        var users = userService.findAllUsers();
        return Response.ok(users).build();
    }

    @GET
    @Path("/{id}")
    public Response findByID(@PathParam("id") UUID userID){
        return Response.ok(userService.findByID(userID)).build();
    }

    @POST
    public Response createUser(UserEntity userEntity) {
        return Response.ok(userService.createUser(userEntity)).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response updateUser(@PathParam("id") UUID userID, UserEntity userEntity) {
        return Response.ok(userService.updateUser(userID, userEntity)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteUser(@PathParam("id") UUID userID) {
        userService.deleteUser(userID);
        return Response.noContent().build();
    }
}
