package io.mongodocgen.screw.model;

public class NestedResolveResult {

    /** 是否嵌套 */
    private Boolean nested;

    /** 嵌套的集合名 */
    private String linkTable;

    /** 嵌套类全名 */
    private String linkClass;

    /** 嵌套数据模型 */
    private TableModel table;

    public Boolean getNested() {
        return nested;
    }

    public void setNested(Boolean nested) {
        this.nested = nested;
    }

    public String getLinkTable() {
        return linkTable;
    }

    public void setLinkTable(String linkTable) {
        this.linkTable = linkTable;
    }

    public String getLinkClass() {
        return linkClass;
    }

    public void setLinkClass(String linkClass) {
        this.linkClass = linkClass;
    }

    public TableModel getTable() {
        return table;
    }

    public void setTable(TableModel table) {
        this.table = table;
    }
}
