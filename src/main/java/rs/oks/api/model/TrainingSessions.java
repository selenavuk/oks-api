package rs.oks.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "training_sessions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingSessions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @Column(name = "order_index")
//    private int orderIndex;

    @Column(name = "training_session")
    private Boolean trainingSession;


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