package cn.isihon.impl.postgres;

import java.sql.*;
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
 * @since 2025/7/24 17:10
 */
public class PostgreSQLMetadataProvider extends MetadataProviderBase {

    private Dialect dialect;

    public PostgreSQLMetadataProvider(DataSource dataSource) {
        super(dataSource);
        dialect = new PostgreSQLDialect();
    }

    @Override
    public List<TableMeta> listTables(String catalog, String schema) {
        List<TableMeta> tables = new ArrayList<>();
        String sql = dialect.showTablesSql(schema);
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, schema);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TableMeta table = new TableMeta();
                table.setCatalog(catalog);
                table.setSchema(schema);
                table.setTableName(rs.getString("table_name"));
                table.setTableType(rs.getString("table_type"));
                table.setComment(null); // PostgreSQL 无标准表注释字段
                tables.add(table);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to query PostgreSQL tables", e);
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
                column.setColumnName(rs.getString("column_name"));
                column.setDataType(rs.getString("data_type"));
                column.setOrdinalPosition(rs.getInt("ordinal_position"));
                column.setNullable("YES".equalsIgnoreCase(rs.getString("is_nullable")));
                column.setDefaultValue(rs.getString("column_default"));
                column.setAutoIncrement(
                        rs.getString("column_default") != null
                                && rs.getString("column_default").contains("nextval"));
                column.setComment(null); // 无标准字段，需查 pg_description 才能支持
                columns.add(column);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to query PostgreSQL columns", e);
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
        String sql = dialect.showSchemasSql();
        try (Statement stmt = getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            SchemaMeta schema = new SchemaMeta();
            while (rs.next()) {
                schema.setSchemaName(rs.getString("schema_name"));
            }
            schemas.add(schema);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to query PostgreSQL schemas", e);
        }
        return schemas;
    }

    @Override
    public List<CatalogMeta> listCatalogs() {
        // PostgreSQL 通常 catalog == database，本实现以当前连接数据库名为准
        try {
            return new ArrayList<CatalogMeta>() {
                {
                    CatalogMeta catalogMeta = new CatalogMeta();
                    catalogMeta.setCatalogName(getConnection().getCatalog());
                    add(catalogMeta);
                }
            };
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get PostgreSQL catalog", e);
        }
    }
}
