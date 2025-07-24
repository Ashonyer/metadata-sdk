package cn.isihon.common.model;

import lombok.Data;

/**
 * Schema（模式） 对应于逻辑数据库空间，支持按用户隔离。 属性：名称、所属 catalog、所属用户。
 *
 * @author Sihon
 * @version 1.0
 * @since 2025/7/24 16:55
 */
@Data
public class SchemaMeta {
    private String schemaName;
    private String catalogName;
    private String owner;
}
