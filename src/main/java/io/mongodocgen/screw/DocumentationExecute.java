package io.mongodocgen.screw;

import static com.google.common.base.Preconditions.checkNotNull;

import com.intellij.psi.PsiClass;
import io.mongodocgen.screw.engine.EngineConfig;
import io.mongodocgen.screw.engine.JsonTemplateEngine;
import io.mongodocgen.screw.engine.TemplateEngine;
import io.mongodocgen.screw.engine.VelocityTemplateEngine;
import io.mongodocgen.screw.model.DataModel;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * 文档生成
 */
public class DocumentationExecute {

    private final Configuration config;
    private final List<PsiClass> psiClasses;

    public DocumentationExecute(Configuration config, List<PsiClass> psiClasses) {
        checkNotNull(config, "Config can't be null");
        checkNotNull(psiClasses, "PsiClasses can't be null");
        this.config = config;
        this.psiClasses = psiClasses;
    }

    public void execute() {
        DataModel dataModel = new DataModelProcess(config, psiClasses).process();
        for (EngineConfig engineConfig : config.getEngineConfigs()) {
            TemplateEngine produce = null;
            switch (engineConfig.getFileType()) {
                case MD:
                    produce = new VelocityTemplateEngine(engineConfig);
                    break;
                case JSON:
                    produce = new JsonTemplateEngine(engineConfig);
                    break;
                default:
                    continue;
            }
            produce.produce(dataModel, getDocName(engineConfig));
        }
    }

    private String getDocName(EngineConfig engineConfig) {
        if (StringUtils.isNotBlank(engineConfig.getFileName())) {
            return engineConfig.getFileName();
        }
        String version = config.getVersion();
        if (StringUtils.isNotBlank(version)) {
            return config.getTitle() + "_" + version;
        }
        return config.getTitle();
    }
}
