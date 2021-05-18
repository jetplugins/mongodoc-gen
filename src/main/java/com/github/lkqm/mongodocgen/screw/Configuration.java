package com.github.lkqm.mongodocgen.screw;

import com.github.lkqm.mongodocgen.screw.engine.EngineConfig;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

}
