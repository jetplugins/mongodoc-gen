package com.github.lkqm.mongodocgen.screw.engine;

import com.github.lkqm.mongodocgen.screw.model.DataModel;

/**
 * 文档渲染的模板引擎
 */
public interface TemplateEngine {

    /**
     * 生产文档
     */
    void produce(DataModel info, String docName);

}
