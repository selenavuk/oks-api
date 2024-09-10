package rs.oks.api.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "membership_fees")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MembershipFees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    // Info that will uniquely identify user
    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;


    // Info about training sessions
    @Column(name = "membership_fees")
    private String membershipFees;

    @Column(name = "month_year")
    private String monthYear;


    // Info used to track

    @NonNull
    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "created_at")
    private Timestamp created_at;

    @NonNull
    @UpdateTimestamp(source = SourceType.DB)
    @Column(name = "updated_at")
    private Timestamp updated_at;
}