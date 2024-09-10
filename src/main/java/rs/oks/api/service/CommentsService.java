package rs.oks.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.oks.api.model.Comments;
import rs.oks.api.model.Payments;
import rs.oks.api.repository.CommentsRepository;
import rs.oks.api.repository.PaymentsRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;


    public void addComments(Comments comments) {
        commentsRepository.save(comments);
    }

    public void updateComments(Comments comments) {
        commentsRepository.updateComments(
                comments.getFirstName(),
                comments.getLastName(),
                comments.getEmail(),
                comments.getMonthYear(),
                comments.getComments()
        );
    }

    public List<Comments> getAllComments() {
        return commentsRepository.findAll();
    }

    public Comments getCommentsForSingleMonthForUserByFullNameAndEmail(String firstName, String lastName, String email, String month_year) {
        try {
            Optional<Comments> commentsOptional = commentsRepository.findByMonthFullNameAndEmail(firstName, lastName, email, month_year);
            return commentsOptional.orElse(null);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public List<Comments> getCommentsForUserByFullNameAndEmail(String firstName, String lastName, String email) {
        try {
            return commentsRepository.findByFullNameAndEmail(firstName, lastName, email);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

}

