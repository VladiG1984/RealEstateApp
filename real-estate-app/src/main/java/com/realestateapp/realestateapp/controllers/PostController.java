package com.realestateapp.realestateapp.controllers;

import com.realestateapp.realestateapp.models.*;

import com.realestateapp.realestateapp.services.base.EstateService;
import com.realestateapp.realestateapp.viewModels.PostSimpleViewModel;
import com.realestateapp.realestateapp.viewModels.PostViewModel;
import org.springframework.beans.factory.annotation.Autowired;

import com.realestateapp.realestateapp.services.base.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService service;
    private EstateService estateService;

    @Autowired
    public PostController(PostService service, EstateService estateService) {
        this.service = service;
        this.estateService = estateService;
    }

    //WORKING
    @GetMapping(value = "/")
    public List<PostViewModel> findAll() {
        return service.findAll()
                .stream()
                .map(PostViewModel::fromModel)
                .collect(Collectors.toList());
    }

    //WORKING
    @GetMapping("/get")
    public Post findById(@RequestParam("id") String stringID) {
        Post ad = null;
        try {
            ad = service.findById((Long.parseLong(stringID)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ad;
    }

    //WORKING TRY WITH POST with http://localhost:8080/api/posts/create?title=AddNum1&description=Test1
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestParam("title") String title,
                                    @RequestParam(value = "description", required = false) String description,
                                    @RequestParam("address") String address,
                                    @RequestParam("material") String material,
                                    @RequestParam("type") String type,
                                    @RequestParam("price") int price,
                                    @RequestParam("baths") int baths,
                                    @RequestParam("bedrooms") int bedrooms) {
        Address address1 = new Address(address);
        Material material1 = new Material(material);
        Type type1 = new Type(type);
        Estate newEstate = new Estate(address1, material1, type1, price, baths, bedrooms);
        estateService.create(newEstate);
        Post post = new Post(title, description, newEstate);

        try {
            if (service.create(post)) {
                return new ResponseEntity<>("Post created successfully", HttpStatus.ACCEPTED);
            }

            return new ResponseEntity<>("Post already exists", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error " + e.getMessage() + "!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //WORKING TRY WITH PUT METHOD with http://localhost:8080/api/posts/update?id=9&title=Updated post&description=Updated Description
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestParam("id") String idString,
                                    @RequestParam(value = "title", required = false) String title,
                                    @RequestParam(value = "description", required = false) String description    ) {

        try {
            Post post = service.findById(Long.parseLong(idString));

            if (post == null) {
                return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
            }

            if (description != null) {
                post.setDescription(description);
                service.update(post);
            }

            return new ResponseEntity<>("Post updated successfully", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error " + e.getMessage() + "!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //WORKING, try with POSTMAN or CURL REQUEST
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@RequestParam("id") String idString) {
        try {
            if (service.deleteById(Long.parseLong(idString))) {
                return new ResponseEntity<>("Post deleted successfully", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("Post NOT FOUND! ID=" + idString, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error " + e.getMessage() + "!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/latest5", method = RequestMethod.GET)
    public List<Post> getLatest5(){
        return service.findLatest5();
    }


}