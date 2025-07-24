package cn.isihon;

import java.util.List;
import javax.sql.DataSource;

import cn.isihon.api.provider.MetadataProvider;
import cn.isihon.common.enums.DBType;
import cn.isihon.common.model.ColumnMeta;
import cn.isihon.common.model.TableMeta;
import cn.isihon.factory.MetadataProviderFactory;

/**
 * @author Sihon
 * @version 1.0
 * @since 2025/7/24 17:40
 */
public class MetadataClient {

    private final MetadataProvider provider;

    public MetadataClient(DBType dbType, DataSource dataSource) {
        this.provider = MetadataProviderFactory.getProvider(dbType, dataSource);
    }

    public List<TableMeta> getTables(String catalog, String schema) {
        return provider.listTables(catalog, schema);
    }

    public List<ColumnMeta> getColumns(String catalog, String schema, String table) {
        return provider.listColumns(catalog, schema, table);
    }

    public List<String> getIndexes(String catalog, String schema, String table) {
        //        return provider.listIndexes(schema, table);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // TODO 可继续扩展获取 triggers、sequences、views 等方法
}
