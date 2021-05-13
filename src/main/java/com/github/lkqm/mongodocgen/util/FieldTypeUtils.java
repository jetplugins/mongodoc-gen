package com.github.lkqm.mongodocgen.util;

import com.github.lkqm.mongodocgen.constant.CommonConstants;
import com.intellij.psi.PsiArrayType;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiType;
import com.intellij.psi.impl.source.PsiClassReferenceType;
import java.io.InputStream;
import java.util.Properties;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FieldTypeUtils {

    private static volatile Properties typeProperties = null;

    public static boolean isArrayOrCollection(PsiType type) {
        if (type instanceof PsiArrayType) {
            return true;
        }
        return isTargetType(type, CommonConstants.COLLECTION_CLASS);
    }

    /**
     * 是否是数组类型
     */
    public static boolean isArray(PsiType type) {
        return type instanceof PsiArrayType;
    }

    /**
     * 是否是集合类型或其子类型
     */
    public static boolean isCollection(PsiType type) {
        return isTargetType(type, CommonConstants.COLLECTION_CLASS);
    }

    /**
     * 是否是Map，以及其子类型
     *
     * @param type
     * @return
     */
    public static boolean isMap(PsiType type) {
        return isTargetType(type, CommonConstants.MAP_CLASS);
    }

    /**
     * 是否是枚举类型
     */
    public static boolean isEnum(PsiType type) {
        return isTargetType(type, CommonConstants.ENUM_CLASS);
    }

    /**
     * 是否是集合类型
     */
    private static boolean isTargetType(PsiType type, String targetType) {
        String canonicalText = type.getCanonicalText();
        if (canonicalText.equals(CommonConstants.OBJECT_CLASS)) {
            return false;
        }
        if (canonicalText.startsWith(targetType)) {
            return true;
        }
        PsiType[] superTypes = type.getSuperTypes();
        for (PsiType superType : superTypes) {
            if (isTargetType(superType, targetType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取字段类型
     */
    public static String getFieldType(PsiField field) {
        PsiType type = field.getType();
        // 数组类型处理
        if (FieldTypeUtils.isArray(type)) {
            PsiArrayType arrayType = (PsiArrayType) type;
            PsiType componentType = arrayType.getComponentType();
            return getSimpleType(componentType) + "[]";
        } else if (FieldTypeUtils.isCollection(type)) {
            PsiClassReferenceType type1 = (PsiClassReferenceType) type;
            PsiType componentType = type1.getParameters().length > 0?type1.getParameters()[0]:null;
            return getSimpleType(componentType) + "[]";
        }
        return getSimpleType(type);
    }

    /**
     * 获取定义的简单类型(非数组，非嵌套对象)
     */
    private static String getSimpleType(PsiType type) {
        boolean isEnum = FieldTypeUtils.isEnum(type);
        if (isEnum) {
            return CommonConstants.TYPE_NAME_ENUM;
        }
        Properties typeProperties = getTypeProperties();
        return typeProperties.getProperty(type.getCanonicalText(), CommonConstants.TYPE_NAME_OBJECT);
    }


    /**
     * 获取字段类型
     */
    public static String getFieldType(String fieldJavaType) {
        Properties typeProperties = getTypeProperties();
        return typeProperties.getProperty(fieldJavaType);
    }

    @SneakyThrows
    public static Properties getTypeProperties() {
        if (typeProperties == null) {
            synchronized (FieldTypeUtils.class) {
                InputStream is = FieldTypeUtils.class.getClassLoader().getResourceAsStream(CommonConstants.FIELD_TYPE_PROPERTIES_FILE);
                Properties properties = new Properties();
                properties.load(is);
                FieldTypeUtils.typeProperties = properties;
            }
        }
        return typeProperties;
    }

}
