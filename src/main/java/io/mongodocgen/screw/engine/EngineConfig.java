package io.mongodocgen.screw.engine;

import java.io.Serializable;

/**
 * 文件生成配置
 */
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



    public String getFileOutputDir() {
        return fileOutputDir;
    }

    public void setFileOutputDir(String fileOutputDir) {
        this.fileOutputDir = fileOutputDir;
    }

    public EngineFileType getFileType() {
        return fileType;
    }

    public void setFileType(EngineFileType fileType) {
        this.fileType = fileType;
    }

    public String getCustomTemplate() {
        return customTemplate;
    }

    public void setCustomTemplate(String customTemplate) {
        this.customTemplate = customTemplate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    @Override
    public String toString() {
        return "EngineConfig{" +
                "fileOutputDir='" + fileOutputDir + '\'' +
                ", fileType=" + fileType +
                ", customTemplate='" + customTemplate + '\'' +
                ", fileName='" + fileName + '\'' +
                ", templateContent='" + templateContent + '\'' +
                '}';
    }
}
