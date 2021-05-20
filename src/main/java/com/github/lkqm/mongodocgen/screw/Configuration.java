package com.github.lkqm.mongodocgen.screw;

import com.github.lkqm.mongodocgen.screw.engine.EngineConfig;

public class Configuration {

    /**
     * 组织
     */
    private String organization;

    /**
     * url
     */
    private String organizationUrl;

    /**
     * 标题
     */
    private String title;

    /**
     * 版本号
     */
    private String version;

    /**
     * 描述
     */
    private String description;

    /**
     * 引擎配置，关于数据库文档生成相关配置
     */
    private EngineConfig engineConfig;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public EngineConfig getEngineConfig() {
        return engineConfig;
    }

    public void setEngineConfig(EngineConfig engineConfig) {
        this.engineConfig = engineConfig;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "organization='" + organization + '\'' +
                ", organizationUrl='" + organizationUrl + '\'' +
                ", title='" + title + '\'' +
                ", version='" + version + '\'' +
                ", description='" + description + '\'' +
                ", engineConfig=" + engineConfig +
                '}';
    }
}
