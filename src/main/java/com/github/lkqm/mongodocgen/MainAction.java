package com.github.lkqm.mongodocgen;

import com.github.lkqm.mongodocgen.screw.Configuration;
import com.github.lkqm.mongodocgen.screw.engine.EngineConfig;
import com.github.lkqm.mongodocgen.screw.engine.EngineFileType;
import com.github.lkqm.mongodocgen.screw.DocumentationExecute;
import com.github.lkqm.mongodocgen.util.NotificationUtils;
import com.github.lkqm.mongodocgen.util.PsiUtils;
import com.google.common.io.Resources;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

/**
 * The main action handler.
 */
public class MainAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        VirtualFile vf = e.getRequiredData(CommonDataKeys.VIRTUAL_FILE);
        Module module = ModuleUtil.findModuleForFile(vf, project);
        if (module == null) {
            return;
        }

        String outputDir =
                ModuleRootManager.getInstance(module).getContentRoots()[0].getPath() + "/target/mongodoc-gent";

        Configuration config = new Configuration();
        config.setTitle(module.getName());
        config.setEngineConfig(EngineConfig.builder()
                .fileOutputDir(outputDir)
                .fileType(EngineFileType.MD)
                .templateContent(getTemplateContent())
                .fileName(config.getTitle())
                .build()
        );

        List<PsiJavaFile> psiFiles = PsiUtils.listModulePsiJavaFiles(module);
        List<PsiClass> psiClassList = PsiUtils.getPsiClassByJavaFile(psiFiles);
        new DocumentationExecute(config, psiClassList)
                .execute();
        NotificationUtils.notifyInfo("Generate mongodb doc successful.");
    }

    @SneakyThrows
    private String getTemplateContent() {
        URL resource = Resources.getResource(this.getClass(), "/template.md.vm");
        return Resources.toString(resource, StandardCharsets.UTF_8);
    }


}
