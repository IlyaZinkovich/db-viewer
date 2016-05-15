package by.bsu.zinkovich;

import java.util.List;

public class Session
{
    private String schema;
    private String table;
    private List<String> selected;
    private String query;

    public String getSchema()
    {
        return schema;
    }

    public void setSchema(String schema)
    {
        this.schema = schema;
    }

    public String getTable()
    {
        return table;
    }

    public void setTable(String table)
    {
        this.table = table;
    }

    public List<String> getSelected()
    {
        return selected;
    }

    public void setSelected(List<String> selected)
    {
        this.selected = selected;
    }

    public String getQuery()
    {
        return query;
    }

    public void setQuery(String query)
    {
        this.query = query;
    }
}
