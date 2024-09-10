package rs.oks.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.oks.api.model.MembershipFees;
import rs.oks.api.model.Payments;
import rs.oks.api.service.MembershipFeesService;
import rs.oks.api.service.PaymentsService;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentsController {

    @Autowired
    private PaymentsService paymentsService;

    @PostMapping("/user")
    public ResponseEntity<String> getMembershipFeesForUserByFullNameAndEmail(
            @RequestBody UserRequest userRequest) {
        try {
            List<Payments> payments = paymentsService.getPaymentsForUserByFullNameAndEmail(
                    userRequest.getFirstName(), userRequest.getLastName(), userRequest.getEmail());

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(payments);

            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}