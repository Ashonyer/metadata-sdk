package cn.isihon.impl.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

import cn.isihon.api.dialect.Dialect;
import cn.isihon.api.provider.MetadataProviderBase;
import cn.isihon.common.model.CatalogMeta;
import cn.isihon.common.model.ColumnMeta;
import cn.isihon.common.model.SchemaMeta;
import cn.isihon.common.model.TableMeta;

/**
 * @author Sihon
 * @version 1.0
 * @since 2025/7/24 16:59
 */
public class MySQLMetadataProvider extends MetadataProviderBase {

    private final Dialect dialect;

    public MySQLMetadataProvider(DataSource dataSource) {
        super(dataSource);
        dialect = new MySQLDialect();
    }

    @Override
    public List<TableMeta> listTables(String catalog, String schema) {
        List<TableMeta> tables = new ArrayList<>();
        String sql = dialect.showTablesSql(schema);
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TableMeta table = new TableMeta();
                table.setCatalog(catalog);
                table.setSchema(schema);
                table.setTableName(rs.getString("TABLE_NAME"));
                table.setTableType(rs.getString("TABLE_TYPE"));
                table.setComment(rs.getString("TABLE_COMMENT"));
                tables.add(table);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to query MySQL tables", e);
        }
        return tables;
    }

    @Override
    public Optional<TableMeta> getTable(String catalog, String schema, String tableName) {
        return listTables(catalog, schema).stream()
                .filter(t -> t.getTableName().equalsIgnoreCase(tableName))
                .findFirst();
    }

    @Override
    public List<ColumnMeta> listColumns(String catalog, String schema, String tableName) {
        List<ColumnMeta> columns = new ArrayList<>();
        String sql = dialect.showColumnsSql(schema, tableName);
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, schema);
            stmt.setString(2, tableName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ColumnMeta column = new ColumnMeta();
                column.setTableName(tableName);
                column.setColumnName(rs.getString("COLUMN_NAME"));
                column.setDataType(rs.getString("DATA_TYPE"));
                column.setOrdinalPosition(rs.getInt("ORDINAL_POSITION"));
                column.setNullable("YES".equalsIgnoreCase(rs.getString("IS_NULLABLE")));
                column.setDefaultValue(rs.getString("COLUMN_DEFAULT"));
                column.setAutoIncrement("auto_increment".equalsIgnoreCase(rs.getString("EXTRA")));
                column.setComment(rs.getString("COLUMN_COMMENT"));
                columns.add(column);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to query MySQL columns", e);
        }
        return columns;
    }

    @Override
    public Optional<ColumnMeta> getColumn(
            String catalog, String schema, String tableName, String columnName) {
        return listColumns(catalog, schema, tableName).stream()
                .filter(c -> c.getColumnName().equalsIgnoreCase(columnName))
                .findFirst();
    }

    @Override
    public List<SchemaMeta> listSchemas() {
        List<SchemaMeta> schemas = new ArrayList<>();
        try (ResultSet rs = getConnection().getMetaData().getSchemas()) {
            SchemaMeta schema = new SchemaMeta();
            while (rs.next()) {
                schema.setCatalogName(rs.getString("TABLE_CAT"));
                schema.setSchemaName(rs.getString("TABLE_SCHEM"));
                schemas.add(schema);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to query schemas", e);
        }
        return schemas;
    }

    @Override
    public List<CatalogMeta> listCatalogs() {
        List<CatalogMeta> catalogs = new ArrayList<>();
        try (ResultSet rs = getConnection().getMetaData().getCatalogs()) {
            CatalogMeta catalog = new CatalogMeta();
            while (rs.next()) {
                catalog.setCatalogName(rs.getString("TABLE_CAT"));
            }
            catalogs.add(catalog);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to query catalogs", e);
        }
        return catalogs;
    }
}
