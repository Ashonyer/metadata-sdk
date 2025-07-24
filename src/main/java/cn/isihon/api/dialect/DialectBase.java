package cn.isihon.api.dialect;

/**
 * @author Sihon
 * @version 1.0
 * @since 2025/7/24 18:40
 */
public class DialectBase implements Dialect {
    @Override
    public String showTablesSql(String schema) {
        return "SELECT table_name, table_type "
                + "FROM information_schema.tables "
                + "WHERE table_schema = ?";
    }

    @Override
    public String showColumnsSql(String schema, String tableName) {
        return "SELECT column_name, data_type, ordinal_position, is_nullable, column_default "
                + "FROM information_schema.columns "
                + "WHERE table_schema = ? AND table_name = ?";
    }

    @Override
    public String showIndexesSql(String schema, String tableName) {
        return "";
    }

    @Override
    public String showTriggersSql(String schema) {
        return "";
    }

    @Override
    public String showSequencesSql(String schema) {
        return "";
    }

    @Override
    public String showSchemasSql() {
        return "SELECT schema_name FROM information_schema.schemata";
    }
}
