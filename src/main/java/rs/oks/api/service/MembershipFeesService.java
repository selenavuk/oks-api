package rs.oks.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.oks.api.model.MembershipFees;
import rs.oks.api.repository.MembershipFeesRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MembershipFeesService {

    @Autowired
    private MembershipFeesRepository membershipFeesRepository;


    public void addMembershipFees(MembershipFees membershipFees) {
        membershipFeesRepository.save(membershipFees);
    }

    public void updateMembershipFees(MembershipFees membershipFees) {
        membershipFeesRepository.updateMembershipFees(
                membershipFees.getFirstName(),
                membershipFees.getLastName(),
                membershipFees.getEmail(),
                membershipFees.getMonthYear(),
                membershipFees.getMembershipFees()
        );
    }

    public List<MembershipFees> getAllMembershipFees() {
        return membershipFeesRepository.findAll();
    }

    public MembershipFees getMembershipFeesForSingleMonthForUserByFullNameAndEmail(String firstName, String lastName, String email, String month_year) {
        try {
            Optional<MembershipFees> membershipFeesOptional = membershipFeesRepository.findByMonthFullNameAndEmail(firstName, lastName, email, month_year);
            return membershipFeesOptional.orElse(null);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public List<MembershipFees> getMembershipFeesForUserByFullNameAndEmail(String firstName, String lastName, String email) {
        try {
            return membershipFeesRepository.findByFullNameAndEmail(firstName, lastName, email);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

}

