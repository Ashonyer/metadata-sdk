package cn.isihon.impl.oracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
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
 * @since 2025/7/24 17:17
 */
public class OracleMetadataProvider extends MetadataProviderBase {

    private final Dialect dialect = new OracleDialect();

    public OracleMetadataProvider(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<TableMeta> listTables(String catalog, String schema) {
        List<TableMeta> tables = new ArrayList<>();
        String sql = dialect.showTablesSql(schema);
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, schema.toUpperCase());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TableMeta table = new TableMeta();
                table.setCatalog(catalog);
                table.setSchema(schema);
                table.setTableName(rs.getString("table_name"));
                table.setTableType(rs.getString("table_type"));
                table.setComment(rs.getString("comments"));
                tables.add(table);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to query Oracle tables", e);
        }
        return tables;
    }

    @Override
    public List<ColumnMeta> listColumns(String catalog, String schema, String tableName) {
        List<ColumnMeta> columns = new ArrayList<>();
        String sql = dialect.showColumnsSql(schema, tableName);
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, schema.toUpperCase());
            stmt.setString(2, tableName.toUpperCase());
            stmt.setString(3, schema.toUpperCase());
            stmt.setString(4, tableName.toUpperCase());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ColumnMeta column = new ColumnMeta();
                column.setTableName(tableName);
                column.setColumnName(rs.getString("column_name"));
                column.setDataType(rs.getString("data_type"));
                column.setOrdinalPosition(rs.getInt("column_id"));
                column.setNullable("Y".equalsIgnoreCase(rs.getString("nullable")));
                column.setDefaultValue(rs.getString("data_default"));
                column.setPrimaryKey("YES".equalsIgnoreCase(rs.getString("primary_key")));
                column.setAutoIncrement(false); // Oracle 需额外解析 IDENTITY/SEQUENCE，暂略
                column.setComment(rs.getString("comments"));
                columns.add(column);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to query Oracle columns", e);
        }
        return columns;
    }

    @Override
    public List<SchemaMeta> listSchemas() {
        List<SchemaMeta> schemas = new ArrayList<>();
        String sql = dialect.showSchemasSql();
        try (Statement stmt = getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            SchemaMeta schemaMeta = new SchemaMeta();
            while (rs.next()) {
                schemaMeta.setOwner(rs.getString("username"));
            }
            schemas.add(schemaMeta);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to query Oracle schemas", e);
        }
        return schemas;
    }

    @Override
    public List<CatalogMeta> listCatalogs() {
        // Oracle 不支持 Catalog，返回空列表或当前连接的 service 名
        return Collections.emptyList();
    }

    @Override
    public Optional<TableMeta> getTable(String catalog, String schema, String tableName) {
        return listTables(catalog, schema).stream()
                .filter(t -> t.getTableName().equalsIgnoreCase(tableName))
                .findFirst();
    }

    @Override
    public Optional<ColumnMeta> getColumn(
            String catalog, String schema, String tableName, String columnName) {
        return listColumns(catalog, schema, tableName).stream()
                .filter(c -> c.getColumnName().equalsIgnoreCase(columnName))
                .findFirst();
    }
}
