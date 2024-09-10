package rs.oks.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.oks.api.model.Equipment;
import rs.oks.api.service.EquipmentService;

import java.util.List;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping("/")
    public ResponseEntity<String> getOrders(){

        try {
            List<Equipment> orders = equipmentService.getAllEquipment();

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(orders);

            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<String> getOrdersByEmail(@PathVariable String email){
        try {
            List<Equipment> orders = equipmentService.getOrdersByEmail(email);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(orders);

            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addOrder(@RequestBody Equipment order) {
        try {
            equipmentService.addEquipment(order);
            return new ResponseEntity<>("Order added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
