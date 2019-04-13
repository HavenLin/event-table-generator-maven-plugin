package markdown.table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by halin on 9/4/2018.
 */
public class Table {
    private List<TableRow> rows;

    public Table() {
    }

    public Table setRows(List<TableRow> tableRows) {
        this.rows = tableRows;
        return this;
    }

    public Table addRow(TableRow tableRow) {
        this.getRows().add(tableRow);
        return this;
    }

    public List<TableRow> getRows() {
        if (rows == null) {
            rows = new ArrayList<>();
        }
        return rows;
    }
}
