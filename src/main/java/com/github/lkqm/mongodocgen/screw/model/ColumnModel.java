package com.github.lkqm.mongodocgen.screw.model;

import lombok.Data;

@Data
public class ColumnModel {

    /**
     * 表中的列的索引（从 1 开始）
     */
    private String            ordinalPosition;

    /**
     * 名称
     */
    private String            columnName;

    /**
     * SQL 数据类型带长度
     */
    private String            columnType;

    /**
     * SQL 数据类型 名称
     */
    private String            typeName;

    /**
     * 列长度
     */
    private String            columnLength;

    /**
     * 列大小
     */
    private String            columnSize;

    /**
     * 小数位
     */
    private String            decimalDigits;

    /**
     * 可为空
     */
    private String            nullable;

    /**
     * 是否主键
     */
    private String            primaryKey;

    /**
     * 默认值
     */
    private String            columnDef;

    /**
     * 说明
     */
    private String            remarks;

    /**
     * 嵌套数据信息（用于文档数据库）
     */
    private TableModel        nestedTable;

    /**
     * 是否弃用
     */
    private Boolean           deprecated;
}
