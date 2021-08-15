package io.mongodocgen.screw.engine;

import java.io.Serializable;

/**
 * 文件类型
 *
 * @author SanLi Created by qinggang.zuo@gmail.com / 2689170096@qq.com on 2020/3/21 21:08
 */

public enum EngineFileType implements Serializable {
    /**
     * HTML
     */
    HTML(".html", "documentation_html", "HTML文件"),
    /**
     * WORD
     */
    WORD(".doc", "documentation_word", "WORD文件"),
    /**
     * MD
     */
    MD(".md", "documentation_md", "Markdown文件"),
    /**
     * MD
     */
    JSON(".json", "documentation_json", "Json文件");

    /**
     * 文件后缀
     */
    private final String fileSuffix;
    /**
     * 模板文件
     */
    private final String templateNamePrefix;
    /**
     * 描述
     */
    private final String desc;

    EngineFileType(String type, String templateFile, String desc) {
        this.fileSuffix = type;
        this.templateNamePrefix = templateFile;
        this.desc = desc;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public String getTemplateNamePrefix() {
        return templateNamePrefix;
    }

    public String getDesc() {
        return desc;
    }
}
