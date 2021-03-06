package io.mongodocgen.constant;

import java.nio.charset.StandardCharsets;

/**
 * 公共默认常量集合.
 */
public interface CommonConstants {

    /**
     * 插件名称
     */
    String NAME = "Mongodoc Gen";

    String DEFAULT_ENCODING = StandardCharsets.UTF_8.name();
    String N = "N";
    String Y = "Y";

    String TYPE_NAME_OBJECT = "object";

    String TYPE_NAME_ENUM = "enum";

    String FIELD_TYPE_PROPERTIES_FILE = "types.properties";

    String DOCUMENT_ANNOTATION = "org.springframework.data.mongodb.core.mapping.Document";

    String TRANSIENT_ANNOTATION = "org.springframework.data.annotation.Transient";

    String FIELD_ANNOTATION = "org.springframework.data.mongodb.core.mapping.Field";

    String ID_ANNOTATION = "org.springframework.data.annotation.Id";

    String PERSISTENT_ID_ANNOTATION = "javax.persistence.Id";

    String DEPRECATED_ANNOTATION = "java.lang.Deprecated";

    String COLLECTION_CLASS = "java.util.Collection";

    String MAP_CLASS = "java.util.Map";

    String OBJECT_CLASS = "java.lang.Object";

    String ENUM_CLASS = "java.lang.Enum";

}
