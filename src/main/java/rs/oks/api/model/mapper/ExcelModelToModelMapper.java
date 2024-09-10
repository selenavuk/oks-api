package rs.oks.api.model.mapper;

import rs.oks.api.model.User;
import rs.oks.api.model.excelmodel.ExcelUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExcelModelToModelMapper {

    public static User mapUser(ExcelUser excelUser) {
        User user = new User();

        user.setFirstName(excelUser.getFirstName());
        user.setLastName(excelUser.getLastName());
        user.setDate(excelUser.getDate());
        user.setDateOfBirth(excelUser.getDateofBirth());
        user.setDateDoctorReview(excelUser.getDateDoctorReview());
        user.setPaymentMethod(excelUser.getPaymentMethod());
        user.setMembershipFee(excelUser.getMembershipFee());
        user.setPayments(excelUser.getPayments());
        user.setComments(excelUser.getComments());
        user.setTrainingSessions(excelUser.getTrainingSessions());
        user.setMembershipFees(excelUser.getMembershipFees());
        user.setTotalTrainingSessions(excelUser.getTotalTrainingSessions());
        user.setPhoneNumber(excelUser.getPhoneNumber());
        user.setInViberGroup(excelUser.getInViberGroup());
        user.setAccessCard(excelUser.getAccessCard());
        user.setHeight(excelUser.getHeight());
        user.setNote(excelUser.getNote());
        user.setEmail(excelUser.getEmail());
        user.setPassword(excelUser.getPassword());
        user.setAgeGroup(excelUser.getAgeGroup());
        return user;
    }

    public static List<User> mapUsers(List<ExcelUser> excelUsers) {
        List<User> users = new ArrayList<>(Collections.emptyList());

        for (ExcelUser excelUser : excelUsers) {
            users.add(mapUser(excelUser));
        }

        return users;
    }
}
