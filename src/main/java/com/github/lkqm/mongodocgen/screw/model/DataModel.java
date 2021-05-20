package com.github.lkqm.mongodocgen.screw.model;

import java.util.List;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOrganizationUrl() {
        return organizationUrl;
    }

    public void setOrganizationUrl(String organizationUrl) {
        this.organizationUrl = organizationUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TableModel> getTables() {
        return tables;
    }

    public void setTables(List<TableModel> tables) {
        this.tables = tables;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "title='" + title + '\'' +
                ", organization='" + organization + '\'' +
                ", organizationUrl='" + organizationUrl + '\'' +
                ", version='" + version + '\'' +
                ", description='" + description + '\'' +
                ", tables=" + tables +
                '}';
    }
}
