package com.github.lkqm.mongodocgen.screw;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.lkqm.mongodocgen.screw.engine.TemplateEngine;
import com.github.lkqm.mongodocgen.screw.engine.VelocityTemplateEngine;
import com.github.lkqm.mongodocgen.screw.model.DataModel;
import com.intellij.psi.PsiClass;
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
        TemplateEngine produce = new VelocityTemplateEngine(config.getEngineConfig());
        produce.produce(dataModel, getDocName());
    }

    private String getDocName() {
        if (StringUtils.isNotBlank(config.getEngineConfig().getFileName())) {
            return config.getEngineConfig().getFileName();
        }
        String version = config.getVersion();
        if (StringUtils.isNotBlank(version)) {
            return config.getTitle() + "_" + version;
        }
        return config.getTitle();
    }
}
