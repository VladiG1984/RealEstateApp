package com.realestateapp.realestateapp.controllers;

import com.realestateapp.realestateapp.models.User;
import com.realestateapp.realestateapp.services.base.UserService;
import com.realestateapp.realestateapp.viewModels.PropertySimpleViewModel;
import com.realestateapp.realestateapp.viewModels.UserViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserController {
    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("view", "users/register");
        model.addAttribute("user",new User());
        return "base";
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "base";
        }

        if (service.checkIfUsernameIsPresent(user.getUsername())) {
            model.addAttribute("view", "users/register");
            model.addAttribute("usernameExist", true);

            return "base";
        }
        if (service.checkIfEmailIsPresent(user.getEmail())) {
            model.addAttribute("view", "users/register");
            model.addAttribute("emailExist", true);

            return "base";
        }

        service.create(user);
        model.addAttribute("view", "home/index");
        model.addAttribute("registrationSuccess",true);
        return "base";
    }
//    @PostMapping("/register")
//    public String registerUser(@RequestParam(value = "username",required = false) String username,
//                               @RequestParam(value = "email",required = false) String email,
//                               @RequestParam(value = "password",required = false) String password,
//                               Model model) {
//        if (!service.checkIfUsernameIsPresent(username)) {
//            User user = new User(username, password, email);
//            service.create(user);
//
//            model.addAttribute("view", "home/index");
//            return "base";
//        }
//        return "base";
//    }


    @GetMapping("/login")
    public String userLogin(Model model) {
        model.addAttribute("view", "users/loginTest");

        return "base";
    }

    @GetMapping("/forget")
    public String userForget(Model model) {
        model.addAttribute("view", "users/forget");

        return "base";
    }

    @GetMapping("/account")
    public String userProfile(Model model) {
        model.addAttribute("view", "users/user-profile");

        return "base";
    }

    @GetMapping("/user/favorites")
    public String userFavoriteProperties(Model model) {
        model.addAttribute("view", "users/user-favorite-properties");

        return "base";
    }


    @GetMapping("/users/all")
    public ResponseEntity<List<UserViewModel>> getAllUsers() {
        List<User> users = service.getAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users
                .stream()
                .map(UserViewModel::fromModel)
                .collect(Collectors.toList()), HttpStatus.ACCEPTED);
    }

    @GetMapping("/users/get")
    public ResponseEntity<UserViewModel> findById(@RequestParam("id") String stringID) {
        User user = null;
        try {
            user = service.getById(Integer.parseInt(stringID));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user == null) {
            return new ResponseEntity<>(new UserViewModel(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(UserViewModel.fromModel(user), HttpStatus.ACCEPTED);
    }

    @GetMapping("/users/posts")
    public ResponseEntity<Set<PropertySimpleViewModel>> findPosts(@RequestParam("id") String stringID) {
        User user = null;
        try {
            user = service.getById(Integer.parseInt(stringID));
        } catch (Exception e) {
            e.printStackTrace();

        }
        if (user == null) {
            return new ResponseEntity<>(new HashSet<>(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user.getUserProperties()
                .stream()
                .map(PropertySimpleViewModel::fromModel)
                .collect(Collectors.toSet()),
                HttpStatus.ACCEPTED);
    }

    /**
     * http://localhost:8080/api/users/create?username=TestUser&password=123456&email=testUser@gmail.com&firstName=TestFirstName&lastName=TestSecondName
     */
    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestParam("username") String username,
                                    @RequestParam("password") String password,
                                    @RequestParam("email") String email,
                                    @RequestParam("firstName") String firstName,
                                    @RequestParam("lastName") String lastName) {
        try {
            User user = new User(username, password, email, firstName, lastName);
            if (service.create(user)) {
                return new ResponseEntity<>("User created successfully", HttpStatus.ACCEPTED);
            }

            return new ResponseEntity<>("User already exists", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error " + e.getMessage() + "!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * TEST WITH http://localhost:8080/api/users/update?id=8&firstName=UpdatedFirstName&lastName=UpdatedSecondName
     */
    @RequestMapping(value = "/users/update", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestParam("id") String id,
                                    @RequestParam(value = "email", required = false) String email,
                                    @RequestParam(value = "firstName", required = false) String firstName,
                                    @RequestParam(value = "lastName", required = false) String lastName) {
        try {
            User user = service.getById(Integer.parseInt(id));

            if (user == null) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            if (email != null) {
                user.setEmail(email);
            }

            if (firstName != null) {
                user.setFirstName(firstName);
            }

            if (lastName != null) {
                user.setLastName(lastName);
            }

            service.update(user);
            return new ResponseEntity<>("User updated successfully", HttpStatus.ACCEPTED);

        } catch (Exception e) {
            return new ResponseEntity<>("Error " + e.getMessage() + "!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * TEST WITH http://localhost:8080/api/users/delete?id=9
     */

    @RequestMapping(value = "/users/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@RequestParam("id") String idString) {
        try {
            if (service.deleteById(Integer.parseInt(idString))) {
                return new ResponseEntity<>("User deleted successfully", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("USER NOT FOUND! ID=" + idString, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error " + e.getMessage() + "!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}