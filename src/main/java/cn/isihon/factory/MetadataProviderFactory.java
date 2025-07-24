package cn.isihon.factory;

import javax.sql.DataSource;

import cn.isihon.api.provider.MetadataProvider;
import cn.isihon.common.enums.DBType;
import cn.isihon.impl.mysql.MySQLMetadataProvider;
import cn.isihon.impl.oracle.OracleMetadataProvider;
import cn.isihon.impl.postgres.PostgreSQLMetadataProvider;

/**
 * @author Sihon
 * @version 1.0
 * @since 2025/7/24 17:27
 */
public class MetadataProviderFactory {

    public static MetadataProvider getProvider(DBType dbType, DataSource dataSource) {
        switch (dbType) {
            case MYSQL:
                return new MySQLMetadataProvider(dataSource);
            case POSTGRESQL:
                return new PostgreSQLMetadataProvider(dataSource);
            case ORACLE:
                return new OracleMetadataProvider(dataSource);
            default:
                throw new UnsupportedOperationException("Unsupported database type: " + dbType);
        }
    }
}
