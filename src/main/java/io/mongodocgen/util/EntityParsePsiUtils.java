package io.mongodocgen.util;

import static java.util.Objects.nonNull;

import com.google.common.collect.Lists;
import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiArrayType;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiEnumConstant;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiJavaDocumentedElement;
import com.intellij.psi.PsiJvmModifiersOwner;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.PsiType;
import com.intellij.psi.impl.source.PsiClassReferenceType;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocToken;
import com.intellij.psi.search.GlobalSearchScope;
import io.mongodocgen.constant.CommonConstants;
import io.mongodocgen.screw.model.ColumnModel;
import io.mongodocgen.screw.model.NestedResolveResult;
import io.mongodocgen.screw.model.TableModel;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 * 实体解析工具类.
 */
public final class EntityParsePsiUtils {

    private EntityParsePsiUtils() {}

    /**
     * 解析获取数据表模型
     */
    public static TableModel getTableModel(PsiClass psiClass) {
        return getTableModel(psiClass, Lists.newArrayList());
    }

    public static TableModel getTableModel(PsiClass psiClass, List<PsiClass> fieldsChain) {
        TableModel tableModel = new TableModel();
        tableModel.setTableName(EntityParsePsiUtils.getCollectionName(psiClass));
        tableModel.setRemarks(EntityParsePsiUtils.getCollectionDescription(psiClass));
        tableModel.setDeprecated(isDeprecated(psiClass));
        tableModel.setColumns(EntityParsePsiUtils.getFields(psiClass, fieldsChain));
        return tableModel;
    }

    /**
     * 解析集合名称
     */
    public static String getCollectionName(PsiClass psiClass) {
        String collectionName = null;
        PsiAnnotation documentAnnotation = psiClass.getAnnotation(CommonConstants.DOCUMENT_ANNOTATION);
        if (documentAnnotation != null) {
            collectionName = AnnotationUtil.getStringAttributeValue(documentAnnotation, "collection");
            if (StringUtils.isBlank(collectionName)) {
                collectionName = AnnotationUtil.getStringAttributeValue(documentAnnotation, "value");
            }
        }
        if (StringUtils.isBlank(collectionName)) {
            collectionName = StringUtils.uncapitalize(psiClass.getName());
        }
        return collectionName;
    }

    /**
     * 解析集合描述
     */
    public static String getCollectionDescription(PsiClass psiClass) {
        return getCommentDescription(psiClass);
    }

    /**
     * 是否需要跳过该字段
     */
    public static boolean isFieldSkip(PsiField field) {
        PsiModifierList modifierList = field.getModifierList();
        if (modifierList != null && modifierList.hasExplicitModifier(PsiModifier.STATIC)) {
            return true;
        }
        PsiAnnotation annotation = field.getAnnotation(CommonConstants.TRANSIENT_ANNOTATION);
        if (annotation != null) {
            return true;
        }
        return false;
    }

    /**
     * 解析集合字段
     */
    public static List<ColumnModel> getFields(PsiClass psiClass, List<PsiClass> fieldsChain) {
        List<ColumnModel> columns = Lists.newArrayListWithExpectedSize(psiClass.getAllFields().length);
        for (PsiField field : psiClass.getAllFields()) {
            if (EntityParsePsiUtils.isFieldSkip(field)) {
                continue;
            }
            ColumnModel column = new ColumnModel();
            columns.add(column);
            column.setColumnName(getFieldName(field));
            column.setColumnType(getFieldType(field));
            column.setRemarks(getFieldDescription(field));
            column.setPrimaryKey(isFieldPrimaryKey(field) ? CommonConstants.Y : CommonConstants.N);
            column.setDeprecated(isDeprecated(field));
            NestedResolveResult result = getFieldNestedTable(fieldsChain, field, column.getColumnType());
            column.setNestedTable(result.getTable());
            column.setLinkTable(result.getLinkTable());
            if(StringUtils.isNotEmpty(column.getLinkTable())) {
                column.setLinkTableText(" #" + column.getLinkTable());
            }
        }
        return columns;
    }

    /**
     * 获取字段嵌套数据
     */
    private static NestedResolveResult getFieldNestedTable(List<PsiClass> fieldsChain, PsiField field, String columnType) {
        NestedResolveResult result = new NestedResolveResult();
        result.setNested(false);

        Project project = field.getProject();
        PsiType type = field.getType();

        PsiClass fieldPsiClass = null;
        PsiType componentType = null;
        boolean isBean = CommonConstants.TYPE_NAME_OBJECT.equals(columnType) && !FieldTypeUtils.isMap(type);
        if (isBean) {
            fieldPsiClass = JavaPsiFacade.getInstance(project)
                    .findClass(type.getCanonicalText(), GlobalSearchScope.projectScope(project));
        } else if (FieldTypeUtils.isArray(type)) {
            PsiArrayType arrayType = (PsiArrayType) type;
            componentType = arrayType.getComponentType();
            fieldPsiClass = JavaPsiFacade.getInstance(project)
                    .findClass(componentType.getCanonicalText(), GlobalSearchScope.projectScope(project));
        } else if (FieldTypeUtils.isCollection(type)) {
            PsiClassReferenceType type1 = (PsiClassReferenceType) type;
            componentType = type1.getParameters()[0];
            fieldPsiClass = JavaPsiFacade.getInstance(project)
                    .findClass(componentType.getCanonicalText(), GlobalSearchScope.projectScope(project));
        }

        // 非对象数组
        if(componentType != null) {
            String simpleType = FieldTypeUtils.getFieldSimpleType(componentType);
            if(simpleType != null && !simpleType.equals(CommonConstants.TYPE_NAME_OBJECT)) {
                return result;
            }
        }

        if (fieldPsiClass != null && !fieldsChain.contains(fieldPsiClass)) {
            List<PsiClass> theChain = Lists.newArrayList(fieldsChain);
            theChain.add(fieldPsiClass);
            TableModel tableModel = getTableModel(fieldPsiClass, theChain);
            result.setNested(true);
            result.setTable(tableModel);
            // 嵌套类型是否是集合类
            PsiAnnotation documentAnnotation = fieldPsiClass.getAnnotation(CommonConstants.DOCUMENT_ANNOTATION);
            if(documentAnnotation != null ) {
                result.setLinkTable(getCollectionName(fieldPsiClass));
                result.setLinkClass(fieldPsiClass.getQualifiedName());
            }
        }
        return result;
    }

    /**
     * 是否标记废弃
     */
    private static Boolean isDeprecated(PsiJvmModifiersOwner target) {
        PsiAnnotation annotation = target.getAnnotation(CommonConstants.DEPRECATED_ANNOTATION);
        return nonNull(annotation);
    }

    /**
     * 获取字段名称
     */
    public static String getFieldName(PsiField psiField) {
        String fieldName = null;
        PsiAnnotation fieldAnnotation = psiField.getAnnotation(CommonConstants.FIELD_ANNOTATION);
        if (fieldAnnotation != null) {
            fieldName = AnnotationUtil.getStringAttributeValue(fieldAnnotation, "name");
            if (StringUtils.isBlank(fieldName)) {
                fieldName = AnnotationUtil.getStringAttributeValue(fieldAnnotation, "value");
            }
        }
        if (StringUtils.isBlank(fieldName)) {
            fieldName = psiField.getName();
        }
        return fieldName;

    }

    /**
     * 获取字段类型
     */
    public static String getFieldType(PsiField psiField) {
        return FieldTypeUtils.getFieldType(psiField);
    }

    /**
     * 获取字段的描述
     */
    public static String getFieldDescription(PsiField field) {
        PsiType type = field.getType();
        Project project = field.getProject();
        String description = getCommentDescription(field);
        description = Objects.nonNull(description)?description :"";

        PsiType componentType = null;
        boolean isEnum = FieldTypeUtils.isEnum(type);
        if (isEnum) {
            PsiClass enumPsiClass = JavaPsiFacade.getInstance(project).findClass(type.getCanonicalText(),
                    GlobalSearchScope.projectScope(project));
            if (enumPsiClass != null) {
                description += (": " + getEnumConstantsDescription(enumPsiClass));
            }
        } else if (FieldTypeUtils.isArray(type)) {
            PsiArrayType arrayType = (PsiArrayType) type;
            componentType = arrayType.getComponentType();
        } else if (FieldTypeUtils.isCollection(type)) {
            PsiClassReferenceType type1 = (PsiClassReferenceType) type;
            componentType = type1.getParameters().length > 0?type1.getParameters()[0]:null;
        }

        // 枚举数组集合
        boolean isEnumArray = componentType != null && FieldTypeUtils.isEnum(componentType);
        if(isEnumArray) {
            PsiClass enumPsiClass = JavaPsiFacade.getInstance(project).findClass(componentType.getCanonicalText(),
                    GlobalSearchScope.projectScope(project));
            if (enumPsiClass != null) {
                description += (": " + getEnumConstantsDescription(enumPsiClass));
            }
        }

        return description;
    }

    /**
     * 获取注释描述
     */
    public static String getCommentDescription(PsiJavaDocumentedElement docElement) {
        PsiDocComment docComment = docElement.getDocComment();
        if (docComment == null) {
            return null;
        }
        return Arrays.stream(docComment.getDescriptionElements())
                .filter(o -> o instanceof PsiDocToken)
                .map(PsiElement::getText)
                .findFirst()
                .map(String::trim)
                .orElse(null);
    }

    /**
     * 是否是主键字段
     */
    public static boolean isFieldPrimaryKey(PsiField field) {
        if ("id".equals(field.getName())) {
            return true;
        }
        PsiAnnotation idAnnotation = field.getAnnotation(CommonConstants.ID_ANNOTATION);
        if (idAnnotation != null) {
            return true;
        }
        idAnnotation = field.getAnnotation(CommonConstants.PERSISTENT_ID_ANNOTATION);
        if (idAnnotation != null) {
            return true;
        }
        return false;
    }

    /**
     * 获取枚举类常量描述, 格式: 字段名(xxx),字段名(xxx),
     */
    public static String getEnumConstantsDescription(PsiClass psiClass) {
        StringBuilder sb = new StringBuilder();
        for (PsiField field : psiClass.getFields()) {
            if (field instanceof PsiEnumConstant) {
                sb.append(field.getName());
                String description = getCommentDescription(field);
                if (StringUtils.isNotEmpty(description)) {
                    sb.append("(").append(description).append(")");
                }
                sb.append(", ");
            }
        }
        if (sb.length() > 0) {
            sb.delete(sb.length() - 2, sb.length());
        }
        return sb.toString();
    }
}
