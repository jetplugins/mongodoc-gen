package com.github.lkqm.mongodocgen.screw.model;

import java.util.List;
import lombok.Data;

@Data
public class DataModel {

    /**
     * 标题
     */
    private String title;

    /**
     * 组织
     */
    private String organization;

    /**
     * url
     */
    private String organizationUrl;

    /**
     * 版本号
     */
    private String version;

    /**
     * 描述
     */
    private String description;

    /**
     * 表
     */
    private List<TableModel> tables;

}
