package com.github.lkqm.mongodocgen;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import com.github.lkqm.mongodocgen.screw.MongoDocumentationExecute;
import com.github.lkqm.mongodocgen.util.NotificationUtils;
import com.github.lkqm.mongodocgen.util.PsiUtils;
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
                .produceType(EngineTemplateType.velocity)
                .templateContent(getTemplateContent())
                .fileName(config.getTitle())
                .build()
        );

        List<PsiJavaFile> psiFiles = PsiUtils.listModulePsiJavaFiles(module);
        List<PsiClass> psiClassList = PsiUtils.getPsiClassByJavaFile(psiFiles);
        new MongoDocumentationExecute(config, psiClassList)
                .execute();
        NotificationUtils.notifyInfo("Generate mongodb doc successful.");
    }

    @SneakyThrows
    private String getTemplateContent() {
        return "${markdown.h1} ${_data.title}\n"
                + "\n"
                + "#if(${_data.database})\n"
                + "**数据库名：** $!{_data.database}\n"
                + "\n"
                + "#end\n"
                + "#if(${_data.version})\n"
                + "**文档版本：** $!{_data.version}\n"
                + "\n"
                + "#end\n"
                + "#if(${_data.description})\n"
                + "**文档描述：** $!{_data.description}\n"
                + "#end\n"
                + "\n"
                + "| 序号 | 表名                  | 说明       |\n"
                + "| :---: | :--- | :--- |\n"
                + "#foreach($t in $_data.tables)\n"
                + "#if(${t.deprecated})\n"
                + "  #set($deprecatedMarkdown = '~~')\n"
                + "#else\n"
                + "  #set($deprecatedMarkdown = '')\n"
                + "#end\n"
                + "| $!{velocityCount} | $!{deprecatedMarkdown}[$!{t.tableName}](#$!{t.tableName})$!{deprecatedMarkdown} | $!{t.remarks} |\n"
                + "#end\n"
                + "#foreach($t in $_data.tables)\n"
                + "\n"
                + "${markdown.h2} <a id=\"$!{t.tableName}\">$!{t.tableName}</a>\n"
                + "描述：$!{t.remarks}\n"
                + "\n"
                + "| 序号 | 名称 | 数据类型 | 主键 | 说明 |\n"
                + "| :---: | :--- | :---: | :---: | :--- |\n"
                + "#foreach($c in $t.columns)\n"
                + "#if(${c.deprecated})\n"
                + "  #set($deprecatedMarkdown = '~~')\n"
                + "#else\n"
                + "  #set($deprecatedMarkdown = '')\n"
                + "#end\n"
                + "| $!{velocityCount} | $!{deprecatedMarkdown}$!{c.columnName}$!{deprecatedMarkdown} | $!{c.columnType} | $!{c.primaryKey} | $!{c.remarks} |\n"
                + "#set($nt1 = $c.nestedTable)\n"
                + "#foreach($c in $nt1.columns)\n"
                + "#if(${c.deprecated})\n"
                + "  #set($deprecatedMarkdown = '~~')\n"
                + "#else\n"
                + "  #set($deprecatedMarkdown = '')\n"
                + "#end\n"
                + "| | &nbsp;&nbsp;- $!{deprecatedMarkdown}$!{c.columnName}$!{deprecatedMarkdown} | $!{c.columnType} | $!{c.primaryKey} | $!{c.remarks} |\n"
                + "#set($nt2 = $c.nestedTable)\n"
                + "#foreach($c in $nt2.columns)\n"
                + "#if(${c.deprecated})\n"
                + "  #set($deprecatedMarkdown = '~~')\n"
                + "#else\n"
                + "  #set($deprecatedMarkdown = '')\n"
                + "#end\n"
                + "| | &nbsp;&nbsp;&nbsp;&nbsp;- $!{deprecatedMarkdown}$!{c.columnName}$!{deprecatedMarkdown} | $!{c.columnType} | $!{c.primaryKey} | $!{c.remarks} |\n"
                + "#end\n"
                + "#set($nt2 = \"\")\n"
                + "#end\n"
                + "#set($nt1 = \"\")\n"
                + "#end\n"
                + "#end\n";
    }


}
