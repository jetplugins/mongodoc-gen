package com.github.lkqm.mongodocgen.screw;

import com.github.lkqm.mongodocgen.screw.model.DataModel;
import com.github.lkqm.mongodocgen.screw.model.TableModel;
import com.github.lkqm.mongodocgen.util.EntityParsePsiUtils;
import com.google.common.collect.Lists;
import com.intellij.psi.PsiClass;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NonNull;

/**
 * 数据模型处理
 */
@AllArgsConstructor
public class DataModelProcess {

    @NonNull
    private final Configuration config;

    @NonNull
    private final List<PsiClass> psiClasses;

    /**
     * 获取数据
     */
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
