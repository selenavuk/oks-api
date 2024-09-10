package rs.oks.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.oks.api.model.MembershipFees;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipFeesRepository extends JpaRepository<MembershipFees,Integer> {

    @Transactional
    @Modifying
    @Query(
            "UPDATE MembershipFees mf " +
            "SET mf.membershipFees = :membershipFees " +
            "WHERE mf.firstName = :firstName AND mf.lastName = :lastName AND mf.email = :email AND mf.monthYear = :monthYear"
    )
    void updateMembershipFees(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email,
            @Param("monthYear") String monthYear,
            @Param("membershipFees") String membershipFees
    );

    @Query(
            "SELECT mf " +
            "FROM MembershipFees mf " +
            "WHERE mf.firstName = :firstName AND mf.lastName = :lastName AND mf.email = :email AND mf.monthYear = :monthYear"
    )
    Optional<MembershipFees> findByMonthFullNameAndEmail(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email,
            @Param("monthYear") String monthYear
    );

    @Query(
            "SELECT mf " +
            "FROM MembershipFees mf " +
            "WHERE mf.firstName = :firstName AND mf.lastName = :lastName AND mf.email = :email"
    )
    List<MembershipFees> findByFullNameAndEmail(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email
    );
}
