package rs.oks.api.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import rs.oks.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(
            "SELECT u " +
            "FROM User u " +
            "WHERE u.firstName = :firstName AND u.lastName = :lastName AND u.email = :email"
    )
    Optional<User> findByFullNameAndEmail(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email
    );

    @Query(
            "SELECT u " +
            "FROM User u " +
            "WHERE u.email = :email"
    )
    List<User> findByEmail(
            @Param("email") String email
    );

    // TODO: implement updating of training_sessions column
    @Transactional
    @Modifying
    @Query(
            "UPDATE User u " +
            "SET u.firstName = :firstName, " +
                "u.lastName = :lastName, " +
                "u.date = :date, " +
                "u.dateOfBirth = :dateOfBirth, " +
                "u.dateDoctorReview = :dateDoctorReview, " +
                "u.paymentMethod = :paymentMethod, " +
                "u.membershipFee = :membershipFee, " +
                "u.trainingSessions = :trainingSessions, " +
                "u.totalTrainingSessions = :totalTrainingSessions, " +
                "u.phoneNumber = :phoneNumber, " +
                "u.inViberGroup = :inViberGroup, " +
                "u.accessCard = :accessCard, " +
                "u.height = :height, " +
                "u.note = :note, " +
                "u.ageGroup = :ageGroup, " +
                "u.email = :email, " +
                "u.password = :password " +
            "WHERE u.firstName = :firstName AND u.lastName = :lastName"
    )
    void updateUserWithExcelData(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("date") String date,
            @Param("dateOfBirth") String dateOfBirth,
            @Param("dateDoctorReview") String dateDoctorReview,
            @Param("paymentMethod") String paymentMethod,
            @Param("membershipFee") String membershipFee,
            @Param("trainingSessions") String trainingSessions,
            @Param("totalTrainingSessions") String totalTrainingSessions,
            @Param("phoneNumber") String phoneNumber,
            @Param("inViberGroup") String inViberGroup,
            @Param("accessCard") String accessCard,
            @Param("height") String height,
            @Param("note") String note,
            @Param("ageGroup") String ageGroup,
            @Param("email") String email,
            @Param("password") String password
    );

    @Transactional
    @Modifying
    @Query(
            "UPDATE User u " +
            "SET u.last_login = :lastLogin " +
            "WHERE u.email = :email"
    )
    void updateUserLastLoginTime(
            @Param("lastLogin") Timestamp lastLogin,
            @Param("email") String email
    );

    @Transactional
    @Modifying
    @Query(
            "UPDATE User u " +
            "SET u.confirmed = :confirmed " +
            "WHERE u.email = :email"
    )
    void updateConfirmation(
            @Param("confirmed") Boolean confirmed,
            @Param("email") String email
    );
}
