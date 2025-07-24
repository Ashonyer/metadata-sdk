package cn.isihon.api.provider;

import java.util.List;
import java.util.Optional;

import cn.isihon.common.model.CatalogMeta;
import cn.isihon.common.model.ColumnMeta;
import cn.isihon.common.model.SchemaMeta;
import cn.isihon.common.model.TableMeta;

/**
 * @author Sihon
 * @version 1.0
 * @since 2025/7/24 16:53
 */
public interface MetadataProvider {
    List<TableMeta> listTables(String catalog, String schema);

    Optional<TableMeta> getTable(String catalog, String schema, String tableName);

    List<ColumnMeta> listColumns(String catalog, String schema, String tableName);

    Optional<ColumnMeta> getColumn(
            String catalog, String schema, String tableName, String columnName);

    List<SchemaMeta> listSchemas();

    List<CatalogMeta> listCatalogs();
}
