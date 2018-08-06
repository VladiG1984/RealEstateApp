package com.realestateapp.realestateapp.controllers;

import com.realestateapp.realestateapp.models.*;
import com.realestateapp.realestateapp.services.base.EstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/estates")
public class EstateController {

    private EstateService service;

    @Autowired
    public EstateController(EstateService service) {
        this.service = service;
    }

    @RequestMapping("/")
    public List<Estate> getAll() {
        List<Estate> allEstates = new ArrayList<>();
        try {
            allEstates = service.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allEstates;
    }

    @GetMapping("/get")
    public Estate getById(@RequestParam("id") String idString) {
        Estate estate = null;
        try {
            estate = service.getById(Long.parseLong(idString));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return estate;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestParam("address") String address,
                                    @RequestParam("material") String material,
                                    @RequestParam("type") String type,
                                    @RequestParam("price") int price,
                                    @RequestParam("baths") int baths,
                                    @RequestParam("bedrooms") int bedrooms) {

        Address address1 = new Address(address);
        Material material1 = new Material(material);
        Type type1 = new Type(type);
        Estate newEstate = new Estate(address1, material1, type1, price, baths, bedrooms);
        try {
            if (service.create(newEstate)) {
                return new ResponseEntity<>("Estate created successfully", HttpStatus.ACCEPTED);
            }

            return new ResponseEntity<>("Estate already exists", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error " + e.getMessage() + "!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}