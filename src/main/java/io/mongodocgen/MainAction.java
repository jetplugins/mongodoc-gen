package io.mongodocgen;

import com.google.common.collect.Lists;
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
import io.mongodocgen.screw.Configuration;
import io.mongodocgen.screw.DocumentationExecute;
import io.mongodocgen.screw.engine.EngineConfig;
import io.mongodocgen.screw.engine.EngineFileType;
import io.mongodocgen.util.NotificationUtils;
import io.mongodocgen.util.PsiUtils;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
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
        if (module == null) return;
        // 配置
        Configuration config = new Configuration();
        config.setTitle(module.getName());
        config.setEngineConfigs(getEngineConfigs(module));
        // 解析
        List<PsiJavaFile> psiFiles = PsiUtils.listModulePsiJavaFiles(module);
        List<PsiClass> psiClassList = PsiUtils.getPsiClassByJavaFile(psiFiles);
        // 生成文档
        new DocumentationExecute(config, psiClassList).execute();
        NotificationUtils.notifyInfo("Generate mongodb doc successful.");
    }

    @SuppressWarnings("All")
    private List<EngineConfig> getEngineConfigs(Module module) {
        String outputDir = ModuleRootManager.getInstance(module).getContentRoots()[0].getPath()
                + "/target/mongodoc-gent";
        // markdown
        EngineConfig mdEngineConfig = new EngineConfig();
        mdEngineConfig.setFileOutputDir(outputDir);
        mdEngineConfig.setFileName(module.getName());
        mdEngineConfig.setFileType(EngineFileType.MD);
        URL resource = Resources.getResource(this.getClass(), "/template.md.vm");
        String template = null;
        try {
            template = Resources.toString(resource, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Read document template failed!", e);
        }
        mdEngineConfig.setTemplateContent(template);
        // json
        EngineConfig jsonEngineConfig = new EngineConfig();
        jsonEngineConfig.setFileOutputDir(outputDir);
        jsonEngineConfig.setFileName(module.getName());
        jsonEngineConfig.setFileType(EngineFileType.JSON);
        jsonEngineConfig.setTemplateContent(template);

        return Lists.newArrayList(mdEngineConfig, jsonEngineConfig);
    }


}
