package rs.oks.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.oks.api.model.MembershipFees;
import rs.oks.api.model.Payments;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments,Integer> {

    @Transactional
    @Modifying
    @Query(
            "UPDATE Payments p " +
            "SET p.payments = :payments " +
            "WHERE p.firstName = :firstName AND p.lastName = :lastName AND p.email = :email AND p.monthYear = :monthYear"
    )
    void updatePayments(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email,
            @Param("monthYear") String monthYear,
            @Param("payments") String payments
    );

    @Query(
            "SELECT p " +
            "FROM Payments p " +
            "WHERE p.firstName = :firstName AND p.lastName = :lastName AND p.email = :email AND p.monthYear = :monthYear"
    )
    Optional<Payments> findByMonthFullNameAndEmail(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email,
            @Param("monthYear") String monthYear
    );

    @Query(
            "SELECT p " +
            "FROM Payments p " +
            "WHERE p.firstName = :firstName AND p.lastName = :lastName AND p.email = :email"
    )
    List<Payments> findByFullNameAndEmail(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email
    );
}
