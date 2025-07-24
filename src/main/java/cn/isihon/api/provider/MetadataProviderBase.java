package cn.isihon.api.provider;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * @author Sihon
 * @version 1.0
 * @since 2025/7/24 17:52
 */
public abstract class MetadataProviderBase implements MetadataProvider {
    private final DataSource dataSource;

    protected MetadataProviderBase(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
