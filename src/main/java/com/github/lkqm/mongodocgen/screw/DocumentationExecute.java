package com.github.lkqm.mongodocgen.screw;

import com.github.lkqm.mongodocgen.screw.engine.TemplateEngine;
import com.github.lkqm.mongodocgen.screw.engine.VelocityTemplateEngine;
import com.github.lkqm.mongodocgen.screw.model.DataModel;
import com.intellij.psi.PsiClass;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

/**
 * 文档生成
 */
@AllArgsConstructor
public class DocumentationExecute {

    @NonNull
    private final Configuration config;

    @NonNull
    private final List<PsiClass> psiClasses;

    public void execute() {
        //处理数据
        DataModel dataModel = new DataModelProcess(config, psiClasses).process();
        //产生文档
        TemplateEngine produce = new VelocityTemplateEngine(config.getEngineConfig());
        produce.produce(dataModel, getDocName());
    }

    /**
     * 获取文档名称
     */
    private String getDocName() {
        //自定义文件名称不为空
        if (StringUtils.isNotBlank(config.getEngineConfig().getFileName())) {
            return config.getEngineConfig().getFileName();
        }
        //版本号
        String version = config.getVersion();
        if (StringUtils.isNotBlank(version)) {
            return config.getTitle() + "_" + version;
        }
        return config.getTitle();
    }
}
