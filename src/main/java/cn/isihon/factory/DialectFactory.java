package cn.isihon.factory;

import cn.isihon.api.dialect.Dialect;
import cn.isihon.common.enums.DBType;
import cn.isihon.impl.mysql.MySQLDialect;
import cn.isihon.impl.oracle.OracleDialect;
import cn.isihon.impl.postgres.PostgreSQLDialect;

/**
 * @author Sihon
 * @version 1.0
 * @since 2025/7/24 17:23
 */
public class DialectFactory {
    public static Dialect getDialect(DBType dbType) {
        switch (dbType) {
            case MYSQL:
                return new MySQLDialect();
            case POSTGRESQL:
                return new PostgreSQLDialect();
            case ORACLE:
                return new OracleDialect();
                // ...
            default:
                throw new UnsupportedOperationException("Unsupported db type: " + dbType);
        }
    }
}
