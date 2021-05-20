package com.github.lkqm.mongodocgen.screw;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.lkqm.mongodocgen.screw.model.DataModel;
import com.github.lkqm.mongodocgen.screw.model.TableModel;
import com.github.lkqm.mongodocgen.util.EntityParsePsiUtils;
import com.google.common.collect.Lists;
import com.intellij.psi.PsiClass;
import java.util.Comparator;
import java.util.List;

/**
 * 数据模型处理
 */
public class DataModelProcess {

    private final Configuration config;
    private final List<PsiClass> psiClasses;

    public DataModelProcess(Configuration config, List<PsiClass> psiClasses) {
        checkNotNull(config, "Config can't be null");
        checkNotNull(psiClasses, "PsiClasses can't be null");
        this.config = config;
        this.psiClasses = psiClasses;
    }

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
