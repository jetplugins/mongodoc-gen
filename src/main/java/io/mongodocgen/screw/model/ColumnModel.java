package io.mongodocgen.screw.model;

public class ColumnModel {

    /**
     * 表中的列的索引（从 1 开始）
     */
    private String ordinalPosition;

    /**
     * 名称
     */
    private String columnName;

    /**
     * SQL 数据类型带长度
     */
    private String columnType;

    /**
     * SQL 数据类型 名称
     */
    private String typeName;

    /**
     * 列长度
     */
    private String columnLength;

    /**
     * 列大小
     */
    private String columnSize;

    /**
     * 小数位
     */
    private String decimalDigits;

    /**
     * 可为空
     */
    private String nullable;

    /**
     * 是否主键
     */
    private String primaryKey;

    /**
     * 默认值
     */
    private String columnDef;

    /**
     * 说明
     */
    private String remarks;

    /**
     * 嵌套数据信息（用于文档数据库）
     */
    private TableModel nestedTable;

    /**
     * 是否弃用
     */
    private Boolean deprecated;

    /**
     * 关联表
     */
    private String linkTable;

    private String linkTableText;

    public String getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(String ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getColumnLength() {
        return columnLength;
    }

    public void setColumnLength(String columnLength) {
        this.columnLength = columnLength;
    }

    public String getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(String columnSize) {
        this.columnSize = columnSize;
    }

    public String getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(String decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getColumnDef() {
        return columnDef;
    }

    public void setColumnDef(String columnDef) {
        this.columnDef = columnDef;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public TableModel getNestedTable() {
        return nestedTable;
    }

    public void setNestedTable(TableModel nestedTable) {
        this.nestedTable = nestedTable;
    }

    public Boolean getDeprecated() {
        return deprecated;
    }

    public void setDeprecated(Boolean deprecated) {
        this.deprecated = deprecated;
    }

    public String getLinkTable() {
        return linkTable;
    }

    public void setLinkTable(String linkTable) {
        this.linkTable = linkTable;
    }

    public String getLinkTableText() {
        return linkTableText;
    }

    public void setLinkTableText(String linkTableText) {
        this.linkTableText = linkTableText;
    }
}
