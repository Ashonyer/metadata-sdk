/** DBSyncer Copyright 2020-2023 All Rights Reserved. */
package cn.isihon.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.regex.Matcher;

import org.apache.commons.lang3.StringUtils;

import static java.util.regex.Pattern.compile;

public abstract class DatabaseUtil {

    public static Connection getConnection(
            String driverClassName, String url, String username, String password) throws SQLException {
        if (StringUtils.isNotBlank(driverClassName)) {
            try {
                Class.forName(driverClassName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e); // TODO
            }
        }
        return DriverManager.getConnection(url, username, password);
    }

    public static void close(AutoCloseable rs) {
        if (null != rs) {
            try {
                rs.close();
            } catch (Exception e) {
                throw new RuntimeException(e); // TODO
            }
        }
    }

    public static String getDatabaseName(String url) {
        Matcher matcher = compile("(//)(?!(\\?)).+?(\\?)").matcher(url);
        if (matcher.find()) {
            url = matcher.group(0);
        }
        int s = url.lastIndexOf("/");
        int e = url.lastIndexOf("?");
        if (s > 0 && e > 0) {
            return StringUtils.substring(url, s + 1, e);
        }
        throw new RuntimeException("database is invalid"); // TODO
    }
}
