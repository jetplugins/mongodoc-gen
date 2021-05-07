package com.github.lkqm.mongodocgen.screw;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineFactory;
import cn.smallbun.screw.core.engine.TemplateEngine;
import cn.smallbun.screw.core.exception.BuilderException;
import cn.smallbun.screw.core.execute.AbstractExecute;
import cn.smallbun.screw.core.metadata.model.DataModel;
import cn.smallbun.screw.core.util.ExceptionUtils;
import cn.smallbun.screw.core.util.StringUtils;
import com.intellij.psi.PsiClass;

import java.util.List;

/**
 * 文档生成
 */
public class MongoDocumentationExecute extends AbstractExecute {

    private final List<PsiClass> psiClasses;

    public MongoDocumentationExecute(Configuration config, List<PsiClass> psiClasses) {
        super(config);
        this.psiClasses = psiClasses;
    }

    @Override
    public void execute() throws BuilderException {
        try {
            //处理数据
            DataModel dataModel = new MongoDataModelProcess(config, psiClasses).process();
            //产生文档
            TemplateEngine produce = new EngineFactory(config.getEngineConfig()).newInstance();
            produce.produce(dataModel, getDocName(dataModel.getDatabase()));
        } catch (Exception e) {
            throw ExceptionUtils.mpe(e);
        }
    }

    /**
     * 获取文档名称
     */
    private String getDocName(String database) {
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