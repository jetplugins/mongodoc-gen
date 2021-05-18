package com.github.lkqm.mongodocgen.screw.engine;

import static com.github.lkqm.mongodocgen.constant.CommonConstants.DEFAULT_ENCODING;
import static com.github.lkqm.mongodocgen.screw.engine.EngineTemplateType.velocity;
import static com.google.common.base.Preconditions.checkNotNull;

import com.github.lkqm.mongodocgen.screw.model.DataModel;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.log.NullLogChute;

public class VelocityTemplateEngine extends AbstractTemplateEngine {
    /**
     * DATA
     */
    private static final String DATA = "_data";

    /**
     * 构造函数
     *
     * @param templateConfig {@link EngineConfig }
     */
    public VelocityTemplateEngine(EngineConfig templateConfig) {
        super(templateConfig);
    }

    /**
     * VelocityEngine
     */
    private static VelocityEngine velocityEngine;

    {
        // 初始化模板引擎
        velocityEngine = new VelocityEngine();
        // 如果存在自定义模板
        if (StringUtils.isNotBlank(getEngineConfig().getCustomTemplate())) {
            velocityEngine.setProperty("string.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.StringResourceLoader");
        } else {
            velocityEngine.setProperty("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        }
        velocityEngine.setProperty("runtime.log.logsystem.class", NullLogChute.class.getName());
        velocityEngine.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "");
        velocityEngine.setProperty(Velocity.ENCODING_DEFAULT, DEFAULT_ENCODING);
        velocityEngine.setProperty(Velocity.INPUT_ENCODING, DEFAULT_ENCODING);
        velocityEngine.setProperty("file.resource.loader.unicode", "true");
    }

    /**
     * 生成文档
     */
    @Override
    public void produce(DataModel info, String docName) {
        checkNotNull(info, "DataModel can not be empty!");
        Template template = null;
        try {
            String templateContent = getEngineConfig().getTemplateContent();
            if (templateContent == null) {
                // get template path
                String path = getEngineConfig().getCustomTemplate();
                //如果自定义了模板
                if (StringUtils.isNotBlank(path)) {
                    template = velocityEngine.getTemplate(path, DEFAULT_ENCODING);
                }
                //没有自定义模板，使用核心包自带
                else {
                    template = velocityEngine
                        .getTemplate(velocity.getTemplateDir()
                                     + getEngineConfig().getFileType().getTemplateNamePrefix()
                                     + velocity.getSuffix(),
                            DEFAULT_ENCODING);
                }
            }
            // output
            try (FileOutputStream outStream = new FileOutputStream(getFile(docName));
                    OutputStreamWriter writer = new OutputStreamWriter(outStream, DEFAULT_ENCODING);
                    BufferedWriter sw = new BufferedWriter(writer)) {
                //put data
                VelocityContext context = new VelocityContext();
                context.put(DATA, info);
                context.put("markdown", new HashMap<String, String>() {
                    {
                        put("h1", "#");
                        put("h2", "##");
                        put("h3", "###");
                        put("h4", "####");
                        put("h5", "#####");
                    }
                });
                //generate
                if (template != null) {
                    template.merge(context, sw);
                } else {
                    velocityEngine.evaluate(context, sw, "test", templateContent);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
