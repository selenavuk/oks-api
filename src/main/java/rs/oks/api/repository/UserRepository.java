package rs.oks.api.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.oks.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(
            "SELECT u " +
            "FROM User u " +
            "WHERE u.firstName = :firstName AND u.lastName = :lastName"
    )
    Optional<User> findByName(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName
    );

    @Query(
            "SELECT u " +
                    "FROM User u " +
                    "WHERE u.username = :username"
    )
    List<User> findByUsername(
            @Param("username") String username
    );

    // TODO: implement updating of training_sessions column
    @Modifying
    @Query(
            "UPDATE User u " +
            "SET u.firstName = :firstName, " +
                "u.lastName = :lastName, " +
                "u.date = :date, " +
                "u.paymentMethod = :paymentMethod, " +
                "u.membershipFee = :membershipFee, " +
                "u.totalTrainingSessions = :totalTrainingSessions, " +
                "u.phoneNumber = :phoneNumber, " +
                "u.inViberGroup = :inViberGroup, " +
                "u.accessCard = :accessCard, " +
                "u.height = :height, " +
                "u.note = :note, " +
                "u.colorFlaggedInfo = :colorFlaggedInfo " +
            "WHERE u.firstName = :firstName AND u.lastName = :lastName"
    )
    void updateUserWithExcelData(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("date") String date,
            @Param("paymentMethod") String paymentMethod,
            @Param("membershipFee") String membershipFee,
            @Param("totalTrainingSessions") String totalTrainingSessions,
            @Param("phoneNumber") String phoneNumber,
            @Param("inViberGroup") String inViberGroup,
            @Param("accessCard") String accessCard,
            @Param("height") String height,
            @Param("note") String note,
            @Param("colorFlaggedInfo") String colorFlaggedInfo
    );
}
