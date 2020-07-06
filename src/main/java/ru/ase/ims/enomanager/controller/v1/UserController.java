package ru.ase.ims.enomanager.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ase.ims.enomanager.model.User;
import ru.ase.ims.enomanager.service.DefaultUserService;

import java.util.*;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "${application.v1API}/users")
@Api(value = "/users", tags = {"Users Controller"})
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final DefaultUserService userService;

    /** ________________ Get _______________________ */

    @ApiOperation(value = "Returns list of users and users by role", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Users not found"),
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<User>> users(@RequestParam(name = "role", required = false) String role) {

        List<User> users = userService.getUsers();

        if(users.isEmpty()) {
            log.debug("Users is empty");
            return ResponseEntity.notFound().build();
        }

        if (role != null) {

            if (role.isEmpty()) {
                log.info("Fields role is empty. Get all users");
                return ResponseEntity.ok().body(users);
            } else {
                log.debug("Get users on role: " + role);
                List<User> usersByRole = userService.getUsersOnRole(role);
                return usersByRole.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(users);
            }

        } else {
            return ResponseEntity.ok().body(users);
        }
    }


    @ApiOperation(value = "Returns user by id", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Users not found"),
    })
    @GetMapping(path="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> getUser(@PathVariable(name ="id") Long id) {
        Optional<User> user = userService.getUser(id);
        if (user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        } else {
            log.info("User with id " + id + " didn't found");
            return ResponseEntity.ok().build();
        }
    }


    /** ________________ Post _______________________ */

    @ApiOperation(value = "Create user", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success"),
            @ApiResponse(code = 400, message = "Any error", response = ResponseEntity.class)
    })
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('BUSINESS_ADMIN') OR hasAuthority('ADMIN')")
    public ResponseEntity<User> createUser(@RequestParam(name = "user_name") String userName,
                                           @RequestParam(name = "password") String password,
                                           @RequestParam(name = "role") String role,
                                           @RequestParam(name = "project_id", required = false) Long projectId) {
        log.debug("Create user: " + userName + "with role: " + role);

        if (Stream.of(userName, password, role).anyMatch(String::isBlank)) {
            log.info("Fields userName, password, role cant contain empty value");
            return ResponseEntity.badRequest().build();
        }

        if (userService.checkUserName(userName)) {
            log.info("UserName: " + userName + "already contain");
            return ResponseEntity.badRequest().build();
        }

        if (projectId != null) {


            return userService.createUser(userName, password, role, projectId).map(user -> {
                log.info("Success create user: " + userName);
                return ResponseEntity.ok().body(user);
            }).orElse(ResponseEntity.badRequest().build());
        } else {
            return userService.createUser(userName, password, role).map(user -> {
                log.info("Success create user: " + userName);
                return ResponseEntity.ok().body(user);
            }).orElse(ResponseEntity.badRequest().build());
        }
    }


    /** ________________ Put _______________________ */

    @ApiOperation(value = "Update user role", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Any error", response = ResponseEntity.class)
    })
    @PutMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> update(@PathVariable(name = "id") Long id,
                                       @RequestParam(name = "role") String role) {
        log.debug("Update user: " + id);
        return userService.updateUserRole(id, role).map(user -> {
            log.info("Success role update to user: " + id + "role " + role);
            return ResponseEntity.ok().body(user);
        }).orElse(ResponseEntity.badRequest().build());
    }

    /** ________________ Delete _______________________ */

    @ApiOperation(value = "Delete users")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        log.info("Delete user on id: " + id);
        userService.delete(id);
    }
}
