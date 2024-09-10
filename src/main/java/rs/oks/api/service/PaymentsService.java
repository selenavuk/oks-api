package rs.oks.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.oks.api.model.MembershipFees;
import rs.oks.api.model.Payments;
import rs.oks.api.repository.MembershipFeesRepository;
import rs.oks.api.repository.PaymentsRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentsService {

    @Autowired
    private PaymentsRepository paymentsRepository;


    public void addPayments(Payments payments) {
        paymentsRepository.save(payments);
    }

    public void updatePayments(Payments payments) {
        paymentsRepository.updatePayments(
                payments.getFirstName(),
                payments.getLastName(),
                payments.getEmail(),
                payments.getMonthYear(),
                payments.getPayments()
        );
    }

    public List<Payments> getAllPayments() {
        return paymentsRepository.findAll();
    }

    public Payments getPaymentsForSingleMonthForUserByFullNameAndEmail(String firstName, String lastName, String email, String month_year) {
        try {
            Optional<Payments> paymentsOptional = paymentsRepository.findByMonthFullNameAndEmail(firstName, lastName, email, month_year);
            return paymentsOptional.orElse(null);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public List<Payments> getPaymentsForUserByFullNameAndEmail(String firstName, String lastName, String email) {
        try {
            return paymentsRepository.findByFullNameAndEmail(firstName, lastName, email);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

}

