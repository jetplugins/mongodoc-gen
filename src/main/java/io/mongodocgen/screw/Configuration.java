package io.mongodocgen.screw;

import io.mongodocgen.screw.engine.EngineConfig;
import java.util.List;

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
    private List<EngineConfig> engineConfigs;

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

    public List<EngineConfig> getEngineConfigs() {
        return engineConfigs;
    }

    public void setEngineConfigs(List<EngineConfig> engineConfigs) {
        this.engineConfigs = engineConfigs;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "organization='" + organization + '\'' +
                ", organizationUrl='" + organizationUrl + '\'' +
                ", title='" + title + '\'' +
                ", version='" + version + '\'' +
                ", description='" + description + '\'' +
                ", engineConfigs=" + engineConfigs +
                '}';
    }
}
