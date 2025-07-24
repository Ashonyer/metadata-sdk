package cn.isihon.impl.oracle;

import cn.isihon.api.dialect.DialectBase;

/**
 * @author Sihon
 * @version 1.0
 * @since 2025/7/24 17:25
 */
public class OracleDialect extends DialectBase {

    @Override
    public String showTablesSql(String schema) {
        return "SELECT table_name, 'BASE TABLE' AS table_type, comments "
                + "FROM all_tab_comments WHERE owner = ?";
    }

    @Override
    public String showColumnsSql(String schema, String tableName) {
        return "SELECT col.column_name, col.data_type, col.column_id, col.nullable, col.data_default,\n"
                + "                   CASE WHEN cons.constraint_type = 'P' THEN 'YES' ELSE 'NO' END AS primary_key,\n"
                + "                   comm.comments\n"
                + "              FROM all_tab_columns col\n"
                + "         LEFT JOIN all_col_comments comm\n"
                + "                ON col.owner = comm.owner AND col.table_name = comm.table_name AND col.column_name = comm.column_name\n"
                + "         LEFT JOIN (\n"
                + "                    SELECT acc.column_name, ac.constraint_type, ac.table_name\n"
                + "                      FROM all_constraints ac\n"
                + "                      JOIN all_cons_columns acc\n"
                + "                        ON ac.owner = acc.owner AND ac.constraint_name = acc.constraint_name\n"
                + "                     WHERE ac.constraint_type = 'P' AND ac.owner = ?\n"
                + "                       AND ac.table_name = ?\n"
                + "                   ) cons\n"
                + "                ON col.column_name = cons.column_name AND col.table_name = cons.table_name\n"
                + "             WHERE col.owner = ? AND col.table_name = ?\n"
                + "             ORDER BY col.column_id";
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
        return "SELECT username FROM all_users";
    }
}
