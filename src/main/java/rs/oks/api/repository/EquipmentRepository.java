package rs.oks.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.oks.api.model.Equipment;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment,Integer> {

    @Query(
            "SELECT o " +
            "FROM Equipment o " +
            "WHERE o.email = :email"
    )
    List<Equipment> findByEmail(
            @Param("email") String email
    );

    @Transactional
    @Modifying
    @Query(
            "UPDATE Equipment o " +
            "SET o.paid = :paid " +
            "WHERE o.equipmentId = :equipmentId"
    )
    void updateEquipmentWithExcelData(
            @Param("paid") Boolean paid,
            @Param("equipmentId") String equipmentId
    );

}
