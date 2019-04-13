package markdown.table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by halin on 9/4/2018.
 */
public class TableRow<T> {
    private List columns;

    public TableRow() {
        this.columns = new ArrayList<T>();
    }

    public List getColumns() {
        return columns;
    }

    public void setColumns(List columns) {
        this.columns = columns;
    }
}
