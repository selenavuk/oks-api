package rs.oks.api.misc;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.web.multipart.MultipartFile;
import rs.oks.api.misc.classes.IndexRange;
import rs.oks.api.model.User;
import rs.oks.api.model.excelmodel.ExcelUser;
import rs.oks.api.model.mapper.ExcelModelToModelMapper;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

public class ExcelFileMapper {

    private static final String FILE_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String LABEL_NOTE = "Komentar";
    public static final String LABEL_COLOR = "Boja";
    public static final String LABEL_FIRST_NAME = "Ime";
    public static final String LABEL_LAST_NAME = "Prezime";
    public static final String LABEL_DATE = "Datum";
    public static final String LABEL_PAYMENT_METHOD = "Keš/Banka";
    public static final String LABEL_MEMBERSHIP_FEE = "Članarina";
    public static final String LABEL_TRAINING_SESSIONS = "Prisutnost";
    public static final String LABEL_TOTAL_TRAINING_SESSIONS = "Broj treninga";
    public static final String LABEL_PHONE_NUMBER = "Viber broj";
    public static final String LABEL_IN_VIBER_GROUP = "U viber grupi";
    public static final String LABEL_ACCESS_CARD = "Pristupnica";
    public static final String LABEL_HEIGHT = "Visina";
    public static final String LABEL_TABLE_END = "Total";

    public static String SHEET_NAME = "Sheet1";
    public static final String SHEET_ID = "1QdpovMV4e-b3HYCFLCdDJC_jb3bbQQaQ-AeYa6aGXBQ";

//    private static Sheets sheetsService;

//    public static List<User> readUsersFromExcelTable(InputStream inputStream, String tableName) {
//        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
//
//            Sheet sheet = workbook.getSheet(tableName);
//
////            getFileFromGoogleDrive();
//
//            Iterator<Row> rows = sheet.iterator();
//            List<User> users = new ArrayList<User>();
//
//            int rowNumber = 0;
//            while (rows.hasNext()) {
//                Row currentRow = rows.next();
//
//                // skip headers
//                if (rowNumber == 0) {
//                    rowNumber++;
//                } else {
//
//                    Iterator<Cell> cellsInRow = currentRow.iterator();
//                    User user = new User();
//
//                    int cellIdx = 0;
//                    while(cellsInRow.hasNext()) {
//                        Cell currentCell = cellsInRow.next();
//
////                        switch (cellIdx) {
////                            case 0:
////                                user.setName(currentCell.getStringCellValue());
////                                break;
////                            case 1:
////                                user.setUsername(currentCell.getStringCellValue());
////                                break;
////                            case 2:
////                                user.setEmail(currentCell.getStringCellValue());
////                                break;
////                            case 3:
////                                user.setPassword(currentCell.getStringCellValue());
////                                break;
////                            default:
////                                break;
////                        }
//
//                        Date date = new Date();
//                        Timestamp timestamp = new Timestamp(date.getTime());
//
//                        user.setCreated_at(timestamp);
//                        user.setUpdated_at(timestamp);
//
//                        user.setId(UUID.randomUUID().getLeastSignificantBits());
//                        cellIdx++;
//                    }
//                    users.add(user);
//                }
//            }
////            workbook.close();
//            return users;
//        } catch (Exception e) {
//            // TODO: handle error
//            return null;
//        }
//    }

    public static List<User> readUsersFromGoogleSpreadSheets() throws GeneralSecurityException, IOException, ExecutionException, InterruptedException {
        BatchGetValuesResponse content = getFileFromGoogleDrive();
        return ExcelModelToModelMapper.mapUsers(readContent(content));
    }

    private static BatchGetValuesResponse getFileFromGoogleDrive () throws GeneralSecurityException, IOException, ExecutionException, InterruptedException {
        Sheets sheets = GoogleSheetsServiceUtil.getSheets();
        return sheets.spreadsheets().values()
                .batchGet(SHEET_ID)
                .setRanges(List.of("A1:Z1000"))
                .execute();
    }

    private static List<ExcelUser> readContent(BatchGetValuesResponse content) {
        List<ValueRange> valueRanges = content.getValueRanges();
        List<List<Object>> rows = valueRanges.get(0).getValues();

        for (int i = 0; i < rows.size(); i++) {
            if ( rows.get(i).get(2).toString().equals(LABEL_TABLE_END)) {
                rows = rows.subList(0, i);
                break;
            }
        }

        List<String> labels = readLabelsFromRows(rows);
        List<ExcelUser> users = new ArrayList<>(Collections.emptyList());

        // Exclude the first row (labels rows)
        for (List<Object> row : rows.subList(1, rows.size())) {
                users.add(mapUserFromRow(row, labels));
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

    private static ExcelUser mapUserFromRow(List<Object> row, List<String> labels) {
        ExcelUser user = new ExcelUser();

        user.setNote(getFieldValueFromRow(row, labels, LABEL_NOTE));
        user.setColorFlaggedInfo(getFieldValueFromRow(row, labels, LABEL_COLOR));
        user.setFirstName(getFieldValueFromRow(row, labels, LABEL_FIRST_NAME));
        user.setLastName(getFieldValueFromRow(row, labels, LABEL_LAST_NAME));
        user.setDate(getFieldValueFromRow(row, labels, LABEL_DATE));
        user.setPaymentMethod(getFieldValueFromRow(row, labels, LABEL_PAYMENT_METHOD));
        user.setMembershipFee(getFieldValueFromRow(row, labels, LABEL_MEMBERSHIP_FEE));
        user.setTrainingSessions(getTrainingSessionsFromRow(row, labels, LABEL_TRAINING_SESSIONS));
        user.setTotalTrainingSessions(Integer.parseInt(getFieldValueFromRow(row, labels, LABEL_TOTAL_TRAINING_SESSIONS)));
        user.setPhoneNumber(getFieldValueFromRow(row, labels, LABEL_PHONE_NUMBER));
        user.setInViberGroup(getFieldValueFromRow(row, labels, LABEL_IN_VIBER_GROUP));
        user.setAccessCard(getFieldValueFromRow(row, labels, LABEL_ACCESS_CARD));
        user.setHeight(getFieldValueFromRow(row, labels, LABEL_HEIGHT));

        return user;
    }

    private static String getFieldValueFromRow(List<Object> row, List<String> labels, String label) {
        try {
            return row
                    .get(getIndexOfLabel(labels, label))
                    .toString();
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }

    private static List<Boolean> getTrainingSessionsFromRow(List<Object> row, List<String> labels, String label) {
        List<Boolean> sessions = new ArrayList<>(Collections.emptyList());
        IndexRange range = getIndexRangeOfLabel(labels, label);

        try {
            for (int i = range.getStartIndex(); i <= range.getEndIndex(); i++) {
                sessions.add(Boolean.parseBoolean(row.get(i).toString()));
            }
            return sessions;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("T");
            return sessions;
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
        return new IndexRange(7, 20);
    }

    public static boolean hasExcelFormat(MultipartFile file) {
        return FILE_TYPE.equals(file.getContentType());
    }
}
