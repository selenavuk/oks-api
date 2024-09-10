package rs.oks.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.oks.api.model.Comments;
import rs.oks.api.model.Payments;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<Comments,Integer> {

    @Transactional
    @Modifying
    @Query(
            "UPDATE Comments c " +
            "SET c.comments = :comments " +
            "WHERE c.firstName = :firstName AND c.lastName = :lastName AND c.email = :email AND c.monthYear = :monthYear"
    )
    void updateComments(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email,
            @Param("monthYear") String monthYear,
            @Param("comments") String comments
    );

    @Query(
            "SELECT c " +
            "FROM Comments c " +
            "WHERE c.firstName = :firstName AND c.lastName = :lastName AND c.email = :email AND c.monthYear = :monthYear"
    )
    Optional<Comments> findByMonthFullNameAndEmail(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email,
            @Param("monthYear") String monthYear
    );

    @Query(
            "SELECT c " +
            "FROM Comments c " +
            "WHERE c.firstName = :firstName AND c.lastName = :lastName AND c.email = :email"
    )
    List<Comments> findByFullNameAndEmail(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email
    );
}
