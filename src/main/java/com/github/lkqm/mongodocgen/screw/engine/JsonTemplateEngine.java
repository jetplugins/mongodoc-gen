package com.github.lkqm.mongodocgen.screw.engine;

import com.github.lkqm.mongodocgen.screw.model.DataModel;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;

/**
 * 输出到Json文件的模板
 */
public class JsonTemplateEngine extends AbstractTemplateEngine {

    public JsonTemplateEngine(EngineConfig engineConfig) {
        super(engineConfig);
    }

    @Override
    public void produce(DataModel info, String docName) {
        String json = new Gson().toJson(info);
        File file = getFile(docName);
        try {
            FileUtils.writeStringToFile(file, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Save json file error.", e);
        }
    }
}
