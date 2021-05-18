package com.github.lkqm.mongodocgen.screw.model;

import java.util.List;
import lombok.Data;

@Data
public class TableModel {

    /**
     * 表名
     */
    private String            tableName;

    /**
     * 备注
     */
    private String            remarks;

    /**
     * 表列
     */
    private List<ColumnModel> columns;

    /**
     * 是否弃用
     */
    private Boolean           deprecated;

}
