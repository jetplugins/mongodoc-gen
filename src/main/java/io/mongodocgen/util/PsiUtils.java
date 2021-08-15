package io.mongodocgen.util;

import com.google.common.collect.Lists;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleFileIndex;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import io.mongodocgen.constant.CommonConstants;
import java.util.Arrays;
import java.util.List;

/**
 * Psi相关工具类方法
 */
public final class PsiUtils {

    private PsiUtils() {
    }

    /**
     * 获取模块下所有的Java文件
     */
    public static List<PsiJavaFile> listModulePsiJavaFiles(Module module) {
        Project project = module.getProject();
        ModuleFileIndex fileIndex = ModuleRootManager.getInstance(module).getFileIndex();
        PsiManager psiManager = PsiManager.getInstance(project);

        List<PsiJavaFile> psiFiles = Lists.newArrayList();
        fileIndex.iterateContent(file -> {
            if (file.getFileType() == JavaFileType.INSTANCE) {
                PsiJavaFile psiFile = (PsiJavaFile) psiManager.findFile(file);
                if (psiFile != null) {
                    psiFiles.add(psiFile);
                }
            }
            return true;
        });
        return psiFiles;
    }

    /**
     * 获取PsiClass从PsiJavaFile
     */
    public static List<PsiClass> getPsiClassByJavaFile(List<PsiJavaFile> psiJavaFiles) {
        List<PsiClass> psiClassList = Lists.newArrayListWithCapacity(psiJavaFiles.size());
        for (PsiJavaFile psiJavaFile : psiJavaFiles) {
            Arrays.stream(psiJavaFile.getClasses())
                    .filter(o -> o.getAnnotation(CommonConstants.DOCUMENT_ANNOTATION) != null)
                    .forEachOrdered(psiClassList::add);
        }
        return psiClassList;
    }

}
