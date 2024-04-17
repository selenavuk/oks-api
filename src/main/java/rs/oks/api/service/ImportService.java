package rs.oks.api.service;

import com.google.api.client.auth.oauth2.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.oks.api.misc.ExcelFileMapper;
import rs.oks.api.misc.GoogleAuthorizeUtil;
import rs.oks.api.model.User;
import rs.oks.api.repository.TrainingSessionsRepository;
import rs.oks.api.repository.UserRepository;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ImportService {

    @Autowired
    private UserService userService;

    @Autowired
    private TrainingSessionsRepository trainingSessionsRepository;

//    public void updateUsersFromLocalExcelFile(MultipartFile file) {
//        try {
//            List<User> users = ExcelFileMapper.readUsersFromExcelTable(file.getInputStream(), SHEET_NAME);
//            userRepository.saveAll(Objects.requireNonNull(users));
//        } catch (IOException e) {
//            // TODO: handle error
//            throw new RuntimeException("fail to store excel data: " + e.getMessage());
//        }
//    }

    public void updateUsersFromSpreadSheetsFile() throws GeneralSecurityException, IOException, ExecutionException, InterruptedException {
        CompletableFuture<Credential> credentialFuture = authorizeAndFetchCredentialAsync();
        credentialFuture.get(); // TODO: This will block until the Credential is available - try to find solutions for this

        updateUsers(ExcelFileMapper.readUsersFromGoogleSpreadSheets());
    }

    private void updateUsers(List<User> updatedUsers) {
        for (User user : updatedUsers) {
            User existingUser = userService.getUserByName(user.getFirstName(), user.getLastName());
            if (existingUser == null) {
                userService.addUser(user);
            } else {
                userService.updateUserWithExcelData(user);
            }
        }
    }

    public CompletableFuture<Credential> authorizeAndFetchCredentialAsync() throws GeneralSecurityException, IOException {
        return GoogleAuthorizeUtil.authorizeAsync();
    }
}
