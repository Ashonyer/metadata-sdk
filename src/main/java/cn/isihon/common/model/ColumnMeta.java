package cn.isihon.common.model;

import lombok.Data;

/**
 * Column（列） 属性：列名、数据类型、是否可为空、默认值、主键/外键标识、是否自增、注释等。
 *
 * @author Sihon
 * @version 1.0
 * @since 2025/7/24 16:54
 */
@Data
public class ColumnMeta {
    private String tableName;
    private String columnName;
    private String dataType;
    private Integer ordinalPosition;
    private boolean isNullable;
    private String defaultValue;
    private boolean isPrimaryKey;
    private boolean isAutoIncrement;
    private String comment;
}
