package cn.cxy.shiro.utils;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public class JdbcTemplateUtils {

    private static JdbcTemplate jdbcTemplate;

    public static JdbcTemplate getInstance() {
        if (null == jdbcTemplate) {
            DruidDataSource ds = new DruidDataSource();
            ds.setDriverClassName("com.mysql.jdbc.Driver");
            ds.setUrl("jdbc:mysql://localhost:3306/shiro");
            ds.setUsername("root");
            ds.setPassword("admin");
            jdbcTemplate = new JdbcTemplate(ds);
        }
        return jdbcTemplate;
    }

    private JdbcTemplateUtils() {
    }
}
