package rs.oks.api.model.excelmodel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ExcelUser {

    private String firstName;
    private String lastName;
    private String date;
    private String dateofBirth;
    private String dateDoctorReview;
    private String paymentMethod;
    private String membershipFee;
    private String payments;
    private String comments;
    private String trainingSessions;
    private String membershipFees;
    private Integer totalTrainingSessions;
    private String phoneNumber;
    private String inViberGroup;
    private String accessCard;
    private String height;
    private String note;
    private String ageGroup;
    private String email;
    private String password;
}