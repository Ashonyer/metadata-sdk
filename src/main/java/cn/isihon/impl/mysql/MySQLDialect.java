package cn.isihon.impl.mysql;

import cn.isihon.api.dialect.DialectBase;

/**
 * @author Sihon
 * @version 1.0
 * @since 2025/7/24 17:21
 */
public class MySQLDialect extends DialectBase {
    @Override
    public String showTablesSql(String schema) {
        return "SELECT TABLE_SCHEMA, TABLE_NAME, TABLE_TYPE, TABLE_COMMENT FROM information_schema.tables "
                + "WHERE TABLE_SCHEMA = '"
                + schema
                + "'";
    }

    @Override
    public String showColumnsSql(String schema, String tableName) {
        return "SELECT COLUMN_NAME, DATA_TYPE, ORDINAL_POSITION, IS_NULLABLE, COLUMN_DEFAULT, "
                + "EXTRA, COLUMN_COMMENT FROM information_schema.columns WHERE table_schema = '"
                + schema
                + "' AND table_name = '"
                + tableName
                + "'";
    }

    @Override
    public String showIndexesSql(String schema, String tableName) {
        return "SHOW INDEX FROM `" + schema + "`.`" + tableName + "`";
    }

    @Override
    public String showTriggersSql(String schema) {
        return "SHOW TRIGGERS FROM `" + schema + "`";
    }

    @Override
    public String showSequencesSql(String schema) {
        throw new UnsupportedOperationException("-- MySQL 不支持序列");
    }

    public static class MySQLConstant {}
}
