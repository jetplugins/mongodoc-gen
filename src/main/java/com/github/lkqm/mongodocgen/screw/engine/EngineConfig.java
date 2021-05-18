package com.github.lkqm.mongodocgen.screw.engine;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

/**
 * 文件生成配置
 */
@Data
@Builder
public class EngineConfig implements Serializable {

    /**
     * 文件产生位置
     */
    private String fileOutputDir;

    /**
     * 生成文件类型
     */
    private EngineFileType fileType;

    /**
     * 自定义模板
     */
    private String customTemplate;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 模板内容
     */
    private String templateContent;

}
