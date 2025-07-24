package cn.isihon.impl.postgres;

import cn.isihon.api.dialect.DialectBase;

/**
 * @author Sihon
 * @version 1.0
 * @since 2025/7/24 17:23
 */
public class PostgreSQLDialect extends DialectBase {
    //    @Override
    //    public String showTablesSql(String schema) {
    //        return "SELECT tablename FROM pg_catalog.pg_tables WHERE schemaname = '" + schema + "'";
    //    }
    //
    //    @Override
    //    public String showColumnsSql(String schema, String tableName) {
    //        return "SELECT column_name, data_type, is_nullable FROM information_schema.columns WHERE
    // table_schema = '"
    //                + schema
    //                + "' AND table_name = '"
    //                + tableName
    //                + "'";
    //    }
    //
    //    @Override
    //    public String showIndexesSql(String schema, String tableName) {
    //        return "SELECT indexname, indexdef FROM pg_indexes WHERE schemaname = '"
    //                + schema
    //                + "' AND tablename = '"
    //                + tableName
    //                + "'";
    //    }
    //
    //    @Override
    //    public String showTriggersSql(String schema) {
    //        return "SELECT trigger_name FROM information_schema.triggers WHERE trigger_schema = '"
    //                + schema
    //                + "'";
    //    }
    //
    //    @Override
    //    public String showSequencesSql(String schema) {
    //        return "SELECT sequence_name FROM information_schema.sequences WHERE sequence_schema =
    // '"
    //                + schema
    //                + "'";
    //    }
}
