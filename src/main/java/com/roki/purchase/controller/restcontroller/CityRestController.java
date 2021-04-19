package com.roki.purchase.controller.restcontroller;


import com.roki.purchase.entity.CityEntity;
import com.roki.purchase.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CityRestController {

    @Autowired
    private CityRepository cityRepository;


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/cities")
    public List<CityEntity> getAllCities() {
        return cityRepository.findAll();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/cities/{cityId}")
    public CityEntity getCityById(@PathVariable Integer cityId) {
        return cityRepository.findById(cityId).orElse(null);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/cities/add")
    public CityEntity addNewCity(@RequestBody CityEntity city){
        return cityRepository.save(city);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/cities/update/{cityId}")
    public CityEntity updateCityById (@PathVariable Integer cityId,@RequestBody CityEntity cityEntity) {

        CityEntity city = cityRepository.findById(cityId).orElse(null);
        if(city == null) {
            return cityEntity;
        } else{

            return cityRepository.save(city);
        }
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/cities/delete/{cityId}")
    public String deleteCity(@PathVariable Integer cityId) {
        Optional<CityEntity> city = cityRepository.findById(cityId);
        if(city.isPresent()) {
            cityRepository.delete(city.get());
            return "Success";
        }
        else {
            return "record not found";
        }
    }
}
