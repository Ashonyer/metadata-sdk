package cn.isihon.common.model;

import lombok.Data;

/**
 * Table（表） 属性：表名、表类型（BASE/TEMP/VIEW）、创建时间、备注、所属 schema。
 *
 * @author Sihon
 * @version 1.0
 * @since 2025/7/24 16:54
 */
@Data
public class TableMeta {
    private String catalog;
    private String schema;
    private String tableName;
    private String tableType; // BASE TABLE / VIEW
    private String comment;
}
