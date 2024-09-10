package rs.oks.api.misc.classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import rs.oks.api.model.User;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class GoogleSpreadSheetsReadingResult {
    private Map<String, List<User>> users;
    private Map<String, List<String>> sortedGroupedSpreadSheetNames;
}