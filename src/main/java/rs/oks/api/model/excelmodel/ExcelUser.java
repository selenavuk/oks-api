package rs.oks.api.model.excelmodel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ExcelUser {

    private String firstName;
    private String lastName;
    private String date;
    private String paymentMethod;
    private String membershipFee;
    private List<Boolean> trainingSessions;
    private Integer totalTrainingSessions;
    private String phoneNumber;
    private String inViberGroup;
    private String accessCard;
    private String height;
    private String note;
    private String colorFlaggedInfo;
}