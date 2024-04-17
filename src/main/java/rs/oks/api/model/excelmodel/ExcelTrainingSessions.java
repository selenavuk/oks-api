package rs.oks.api.model.excelmodel;

import lombok.*;
import rs.oks.api.model.User;

@NoArgsConstructor
@Getter
@Setter
public class ExcelTrainingSessions {

    private User user;
    private int orderIndex;
    private Boolean trainingSession;
}