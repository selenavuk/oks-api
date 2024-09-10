package rs.oks.api.service;

import com.google.api.client.auth.oauth2.Credential;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.oks.api.misc.ExcelFileMapper;
import rs.oks.api.misc.GoogleAuthorizeUtil;
import rs.oks.api.misc.classes.GoogleSpreadSheetsReadingResult;
import rs.oks.api.model.*;
//import rs.oks.api.repository.TrainingSessionsRepository;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ImportService {

    @Autowired
    private UserService userService;

    @Autowired TrainingSessionsService trainingSessionsService;

    @Autowired MembershipFeesService membershipFeesService;

    @Autowired PaymentsService paymentsService;

    @Autowired CommentsService commentsService;

    public String getAuthorizationUrl() throws GeneralSecurityException, IOException {
        return GoogleAuthorizeUtil.getAuthorizationUrl();
    }

    public void updateDatabaseFromSpreadSheetsFileTestUrl() throws GeneralSecurityException, IOException, ExecutionException, InterruptedException {
        CompletableFuture<Credential> credentialFuture = authorizeAndFetchCredentialAsync();
        credentialFuture.get(); // TODO: This will block until the Credential is available - try to find solutions for this
    }
    public void updateDatabaseFromSpreadSheetsFile(Credential credential) throws GeneralSecurityException, IOException, ExecutionException, InterruptedException {
//        CompletableFuture<Credential> credentialFuture = authorizeAndFetchCredentialAsync();
//        credentialFuture.get(); // TODO: This will block until the Credential is available - try to find solutions for this

        GoogleSpreadSheetsReadingResult readingResult = ExcelFileMapper.readContentFromGoogleSpreadSheets(credential);

        Map<String, List<User>> users = readingResult.getUsers();
        Map<String, List<String>> sortedGroupedSpreadSheetNames = readingResult.getSortedGroupedSpreadSheetNames();

        // Handle each age group separately
        for (Map.Entry<String, List<String>> entry : sortedGroupedSpreadSheetNames.entrySet()) {

            List<User> singleAgeGroupUsers = users.get(entry.getValue().get(entry.getValue().size() - 1));


            // Update user with the newest data only (data from the last month available)
            updateUsers(singleAgeGroupUsers);


            // Update user's other information (that should keep info for all months)
            for (String singleMonthWithinAgeGroup : entry.getValue()) {
                List<User> singleMonthUsers = users.get(singleMonthWithinAgeGroup);

                for (User user: singleMonthUsers) {

                    // Update: training sessions
                    TrainingSessions trainingSessions = new TrainingSessions();
                    trainingSessions.setFirstName(user.getFirstName());
                    trainingSessions.setLastName(user.getLastName());
                    trainingSessions.setEmail(user.getEmail());
                    trainingSessions.setMonthYear(singleMonthWithinAgeGroup.substring(0, 7));
                    trainingSessions.setTrainingSessions(user.getTrainingSessions());

                    TrainingSessions existingTrainingSessions = trainingSessionsService.getTrainingSessionsForSingleMonthForUserByFullNameAndEmail(user.getFirstName(), user.getLastName(), user.getEmail(), singleMonthWithinAgeGroup.substring(0, 7));

                    if (existingTrainingSessions == null) {
                        trainingSessionsService.addTrainingSessions(trainingSessions);
                    } else {
                        trainingSessionsService.updateTrainingSessions(trainingSessions);
                    }

                    // Update: membershipFees (camp)
                    MembershipFees membershipFees = new MembershipFees();
                    membershipFees.setFirstName(user.getFirstName());
                    membershipFees.setLastName(user.getLastName());
                    membershipFees.setEmail(user.getEmail());
                    membershipFees.setMonthYear(singleMonthWithinAgeGroup.substring(0, 7));
                    membershipFees.setMembershipFees(user.getMembershipFees());

                    MembershipFees existingMembershipFees = membershipFeesService.getMembershipFeesForSingleMonthForUserByFullNameAndEmail(user.getFirstName(), user.getLastName(), user.getEmail(), singleMonthWithinAgeGroup.substring(0, 7));

                    if (existingMembershipFees == null) {
                        membershipFeesService.addMembershipFees(membershipFees);
                    } else {
                        membershipFeesService.updateMembershipFees(membershipFees);
                    }

                    // Update: payments (monthly membership fees)
                    Payments payments = new Payments();
                    payments.setFirstName(user.getFirstName());
                    payments.setLastName(user.getLastName());
                    payments.setEmail(user.getEmail());
                    payments.setMonthYear(singleMonthWithinAgeGroup.substring(0, 7));
                    payments.setPayments(user.getPayments());

                    Payments existingPayments = paymentsService.getPaymentsForSingleMonthForUserByFullNameAndEmail(user.getFirstName(), user.getLastName(), user.getEmail(), singleMonthWithinAgeGroup.substring(0, 7));

                    if (existingPayments == null) {
                        paymentsService.addPayments(payments);
                    } else {
                        paymentsService.updatePayments(payments);
                    }

                    // Update: comments
                    Comments comments = new Comments();
                    comments.setFirstName(user.getFirstName());
                    comments.setLastName(user.getLastName());
                    comments.setEmail(user.getEmail());
                    comments.setMonthYear(singleMonthWithinAgeGroup.substring(0, 7));
                    comments.setComments(user.getComments());

                    Comments existingComments = commentsService.getCommentsForSingleMonthForUserByFullNameAndEmail(user.getFirstName(), user.getLastName(), user.getEmail(), singleMonthWithinAgeGroup.substring(0, 7));

                    if (existingComments == null) {
                        commentsService.addComments(comments);
                    } else {
                        commentsService.updateComments(comments);
                    }
                }
            }

        }
    }

    public CompletableFuture<Credential> authorizeAndFetchCredentialAsync() throws GeneralSecurityException, IOException {
        return GoogleAuthorizeUtil.authorizeAsync();
    }

    private void updateUsers(List<User> users) {
        for (User user : users) {

            // Check if user with this full name and email exists
            User existingUser = userService.getUserByFullNameAndEmail(user.getFirstName(), user.getLastName(), user.getEmail());

            if (existingUser == null) {
                userService.addUser(user);
            } else {
                userService.updateUserWithExcelData(user);
            }
        }
    }


}
