package cn.isihon.api.dialect;

/**
 * @author Sihon
 * @version 1.0
 * @since 2025/7/24 17:20
 */
public interface Dialect {
    String showTablesSql(String schema);

    String showColumnsSql(String schema, String tableName);

    String showIndexesSql(String schema, String tableName);

    String showTriggersSql(String schema);

    String showSequencesSql(String schema);

    String showSchemasSql();
}
