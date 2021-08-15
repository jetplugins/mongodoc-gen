package io.mongodocgen.screw.model;

import java.util.List;

public class TableModel {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 表列
     */
    private List<ColumnModel> columns;

    /**
     * 是否弃用
     */
    private Boolean deprecated;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }

    public Boolean getDeprecated() {
        return deprecated;
    }

    public void setDeprecated(Boolean deprecated) {
        this.deprecated = deprecated;
    }

    @Override
    public String toString() {
        return "TableModel{" +
                "tableName='" + tableName + '\'' +
                ", remarks='" + remarks + '\'' +
                ", columns=" + columns +
                ", deprecated=" + deprecated +
                '}';
    }
}
