package com.github.lkqm.mongodocgen.screw;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.metadata.model.DataModel;
import cn.smallbun.screw.core.metadata.model.TableModel;
import cn.smallbun.screw.core.process.AbstractProcess;
import com.github.lkqm.mongodocgen.util.EntityParsePsiUtils;
import com.google.common.collect.Lists;
import com.intellij.psi.PsiClass;
import java.util.Comparator;
import java.util.List;

/**
 * 数据模型处理
 */
public class MongoDataModelProcess extends AbstractProcess {

    private final List<PsiClass> psiClasses;

    public MongoDataModelProcess(Configuration configuration, List<PsiClass> psiClasses) {
        super(configuration);
        this.psiClasses = psiClasses;
    }

    @Override
    public DataModel process() {
        DataModel model = new DataModel();
        model.setTitle(config.getTitle());
        model.setOrganization(config.getOrganization());
        model.setOrganizationUrl(config.getOrganizationUrl());
        model.setVersion(config.getVersion());
        model.setDescription(config.getDescription());
        model.setTables(Lists.newArrayListWithExpectedSize(psiClasses.size()));
        for (PsiClass psiClass : psiClasses) {
            TableModel tableModel = EntityParsePsiUtils.getTableModel(psiClass);
            model.getTables().add(tableModel);
        }
        model.getTables().sort(Comparator.comparing(TableModel::getTableName));
        return model;
    }


}
