package cn.isihon.common.model;

import lombok.Data;

/**
 * Catalog（目录） 如 PostgreSQL 的 pg_database、MySQL 的 catalog 通常为 def。 属性：名称、所有者、字符集等。
 *
 * @author Sihon
 * @version 1.0
 * @since 2025/7/24 16:54
 */
@Data
public class CatalogMeta {
    private String catalogName;
    private String owner;
    private String charset;
}
