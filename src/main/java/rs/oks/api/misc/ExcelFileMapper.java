package rs.oks.api.misc;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;
import rs.oks.api.misc.classes.GoogleSpreadSheetsReadingResult;
import rs.oks.api.misc.classes.IndexRange;
import rs.oks.api.misc.classes.Labels;
import rs.oks.api.model.User;
import rs.oks.api.model.excelmodel.ExcelUser;
import rs.oks.api.model.mapper.ExcelModelToModelMapper;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import java.util.logging.Logger;

public class ExcelFileMapper {

//    private static final String FILE_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
//    private static final List<String> groupsToImport = Arrays.asList("mladja1", "mladja2", "srednja", "starija");

//    public static final String FILE_ID = "1QdpovMV4e-b3HYCFLCdDJC_jb3bbQQaQ-AeYa6aGXBQ";

    public static final String FILE_ID = "1GOtntCvuntlmqAFMmkPDA3PsmQJt8nXJaiC2Ows_bvE";

    public static GoogleSpreadSheetsReadingResult readContentFromGoogleSpreadSheets(Credential credential) throws GeneralSecurityException, IOException, ExecutionException, InterruptedException {

        // Get all sheets from the spreadsheet
        Sheets file = GoogleSheetsServiceUtil.getSheets();
        Spreadsheet spreadsheet = file.spreadsheets().get(FILE_ID).execute();
        List<Sheet> sheets = spreadsheet.getSheets();

        // Determine which sheets to import
        List<String> googleSpreadSheetNamesToImport = getGoogleSpreadSheetNamesToImport(sheets);

        // Group sheets by age group
        Pattern pattern = Pattern.compile("(mladja1|mladja2|srednja|starija)");
        Map<String, List<String>> groupedSpreadSheetNames = googleSpreadSheetNamesToImport.stream()
                .collect(Collectors.groupingBy(name -> {
                    Matcher matcher = pattern.matcher(name);
                    return matcher.find() ? matcher.group() : "";
                }));

        // Sort sheets in groups by month and year
        Pattern datePattern = Pattern.compile("(\\d{2}_\\d{4})_(mladja1|mladja2|srednja|starija)");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM_yyyy");
        Map<String, List<String>> sortedGroupedSpreadSheetNames = groupedSpreadSheetNames.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .sorted(Comparator.comparing(name -> {
                                    Matcher matcher = datePattern.matcher(name);
                                    return matcher.find() ? YearMonth.parse(matcher.group(1), dateFormatter) : YearMonth.now();
                                }))
                                .collect(Collectors.toList())
                ));

        // Map that will hold sheet name to the list of users from corresponding sheet
        Map<String, List<User>> hashMap = new HashMap<>();

        // Access a single age group at a time
        for (Map.Entry<String, List<String>> entry : sortedGroupedSpreadSheetNames.entrySet()) {
            List<String> sheetNames = entry.getValue();

            // Access a single sheet from an age group at a time
            for (String sheetName : sheetNames) {

                // Get sheet content
                BatchGetValuesResponse sheetContent = file
                        .spreadsheets()
                        .values()
                        .batchGet(FILE_ID)
                        .setRanges(List.of(sheetName + "!A1:AY1000"))
                        .execute();

                // Map sheet content to users
                List<User> sheetUsers = ExcelModelToModelMapper.mapUsers(readContent(sheetContent, sheetName));
                hashMap.put(sheetName, sheetUsers);
            }
        }

        return new GoogleSpreadSheetsReadingResult(hashMap, sortedGroupedSpreadSheetNames);
    }

    public static List<String> getGoogleSpreadSheetNamesToImport(List<Sheet> sheets) {
        List<String> googleSpreadSheetNames = new ArrayList<>();
        String regex = "\\d{2}_\\d{4}_(mladja1|mladja2|srednja|starija)";

        for (Sheet sheet : sheets) {
            String sheetName = sheet.getProperties().getTitle();
            if (sheetName.matches(regex)) {
                googleSpreadSheetNames.add(sheetName);
            }
        }
        return googleSpreadSheetNames;
    }

    private static List<ExcelUser> readContent(BatchGetValuesResponse content, String sheetName) {
        List<ValueRange> valueRanges = content.getValueRanges();
        List<List<Object>> rows = valueRanges.get(0).getValues();

        for (int i = 0; i < rows.size(); i++) {
            if ( rows.get(i).get(2).toString().equals(Labels.TABLE_END)) {
                rows = rows.subList(0, i);
                break;
            }
        }

        List<String> labels = readLabelsFromRows(rows);
        List<ExcelUser> users = new ArrayList<>(Collections.emptyList());

        // Exclude the first row (labels rows)
        for (List<Object> row : rows.subList(1, rows.size())) {
                users.add(mapUserFromRow(row, labels, sheetName));
        }

        return users;
    }

    private static List<String> readLabelsFromRows(List<List<Object>> rows) {
        return rows
                .get(0)
                .stream()
                .map(Object::toString)
                .toList();
    }

    private static ExcelUser mapUserFromRow(List<Object> rawRow, List<String> labels, String sheetName) {

        List<Object> row = new ArrayList<>(Collections.nCopies(51, ""));
        for (int i = 0; i < rawRow.size(); i++) {
            row.set(i, rawRow.get(i));
        }

        ExcelUser user = new ExcelUser();

        user.setNote(getFieldValueFromRow(row, labels, Labels.NOTE));
        user.setAgeGroup(sheetName.substring(sheetName.lastIndexOf("_") + 1));
        user.setFirstName(getFieldValueFromRow(row, labels, Labels.FIRST_NAME));
        user.setLastName(getFieldValueFromRow(row, labels, Labels.LAST_NAME));
        user.setDate(getFieldValueFromRow(row, labels, Labels.DATE));
        user.setDateofBirth(getFieldValueFromRow(row, labels, Labels.DATE_OF_BIRTH));
        user.setDateDoctorReview(getFieldValueFromRow(row, labels, Labels.DATE_DOCTOR_REVIEW));
        user.setPaymentMethod(getFieldValueFromRow(row, labels, Labels.PAYMENT_METHOD));
        user.setMembershipFee(getFieldValueFromRow(row, labels, Labels.MEMBERSHIP_FEE));
        user.setPayments(getFieldValueFromRow(row, labels, Labels.MEMBERSHIP_FEE));
        user.setComments(getCommentsFromRow(row));
        user.setTrainingSessions(getTrainingSessionsFromRow(row, labels, Labels.TRAINING_SESSIONS));
        user.setMembershipFees(getFieldValueFromRow(row, labels, Labels.MEMBERSHIP_FEE_CAMP));
        user.setTotalTrainingSessions(Integer.parseInt(getFieldValueFromRow(row, labels, Labels.TOTAL_TRAINING_SESSIONS)));
        user.setPhoneNumber(getFieldValueFromRow(row, labels, Labels.PHONE_NUMBER));
        user.setInViberGroup(getFieldValueFromRow(row, labels, Labels.IN_VIBER_GROUP));
        user.setAccessCard(getFieldValueFromRow(row, labels, Labels.ACCESS_CARD));
        user.setHeight(getFieldValueFromRow(row, labels, Labels.HEIGHT));
        user.setEmail(getFieldValueFromRow(row, labels, Labels.USERNAME));
        user.setPassword(getFieldValueFromRow(row, labels, Labels.PASSWORD));

        return user;
    }

    private static String getFieldValueFromRow(List<Object> row, List<String> labels, String label) {
        try {
            if (label.equals(Labels.ACCESS_CARD)) {
                return row.get(getIndexOfLabel(labels, label)).toString().equalsIgnoreCase("da") ? "DA" : "NE";
            } else {
                return row.get(getIndexOfLabel(labels, label)).toString();
            }
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }

    private static String getCommentsFromRow(List<Object> row) {
        StringBuilder concatenatedString = new StringBuilder();
        for (int i = 31; i <= 50; i++) {
            if (i < row.size()) {
                if(!row.get(i).toString().isEmpty()) {
                    concatenatedString.append(row.get(i).toString());
                    if (i < 50) {
                        concatenatedString.append("++");
                    }
                }
            }
        }
        return concatenatedString.toString();
    }

    private static String getTrainingSessionsFromRow(List<Object> row, List<String> labels, String label) {
        List<String> sessions = new ArrayList<>(Collections.emptyList());
        IndexRange range = getIndexRangeOfLabel(labels, label);

        try {
            for (int i = range.getStartIndex(); i <= range.getEndIndex(); i++) {
                // Add training session only if the columns is filled
                if(!labels.get(i).isEmpty()) {
                    sessions.add(labels.get(i) + "+" + Boolean.parseBoolean(row.get(i).toString()));
                }
            }

            //.map(String::valueOf)
            return String.join("_", sessions);
//            return sessions;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("T");
            return "";
        }
    }

    private static int getIndexOfLabel(List<String> labels, String label) {
        return IntStream
                .range(0, labels.size())
                .filter(i -> labels.get(i).contains(label))
                .findFirst()
                .orElseThrow();
    }

    private static IndexRange getIndexRangeOfLabel(List<String> labels, String label) {
        // TODO: add login to get this range dynamically
        return new IndexRange(8, 21);
    }

}