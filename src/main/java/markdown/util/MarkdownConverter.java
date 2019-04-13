package markdown.util;

import markdown.table.Table;
import markdown.table.TableRow;

import java.util.List;

/**
 * Created by halin on 9/4/2018.
 */
public class MarkdownConverter {
    private static final String PIPE = " | ";
    private static final String HYPHENS = "---";
    private static final String LINE_BREAK = "\n";
    private static final String EVENT_CODE_HEADER = "Event code";
    private static final String EVENT_MESSAGE_HEADER = "Event message";

    public static String serializedTable(Table table) {
        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder.append(PIPE).append(EVENT_CODE_HEADER).append(PIPE).append(EVENT_MESSAGE_HEADER).append(PIPE).append(LINE_BREAK)
                .append(PIPE).append(HYPHENS).append(PIPE).append(HYPHENS).append(PIPE).append(LINE_BREAK);
        for (TableRow tableRow : table.getRows()) {
            List columns = tableRow.getColumns();
            assert columns.size() != 2;
            Object eventCode = columns.get(0);
            Object eventMessage = columns.get(1);
            tableBuilder.append(PIPE).append(eventCode).append(PIPE).append(eventMessage).append(PIPE).append(LINE_BREAK);
        }

        return tableBuilder.toString();
    }
}
