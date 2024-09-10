package rs.oks.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.oks.api.model.Equipment;
import rs.oks.api.repository.EquipmentRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    public List<Equipment> getAllEquipment(){
        return equipmentRepository.findAll();
    }

    public List<Equipment> getOrdersByEmail(String email) {
        try {
            return equipmentRepository.findByEmail(email);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public void addEquipment(Equipment order){
        equipmentRepository.save(order);
    }

    public void updateEquipmentWithExcelData(Equipment order) {
        equipmentRepository.updateEquipmentWithExcelData(
                order.getPaid(),
                order.getEquipmentId()
        );
    }

}
