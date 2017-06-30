package org.apache.shiro.session.scheduler;

import cn.cxy.shiro.utils.JdbcTemplateUtils;
import cn.cxy.shiro.utils.SerializableUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Function: 验证调度器实现 - 重写覆盖调用 sessionDAO.getActiveSession() 方法【此方法默认获取全部】  -- 修改为分页获取session验证
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/19 18:28 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class MySessionValidationScheduler implements SessionValidationScheduler, Runnable {

    private JdbcTemplate jdbcTemplate = JdbcTemplateUtils.getInstance();

    private static final Logger logger = LoggerFactory.getLogger(MySessionValidationScheduler.class);

    ValidatingSessionManager sessionManager;
    private ScheduledExecutorService service;
    private long interval = DefaultSessionManager.DEFAULT_SESSION_VALIDATION_INTERVAL;
    private boolean enabled = false;

    public void run() {
        String sql = "select session from sessions limit ?,?";
        int start = 0;
        int size = 20;
        List<String> sessionList = jdbcTemplate.queryForList(sql, String.class, start, size);
        while (sessionList.size() > 0) {
            try {
                for (String s : sessionList) {
                    Session session = SerializableUtils.deserialize(s);
                    //cxy 反射执行
                    Method validateMethod = ReflectionUtils.findMethod(AbstractValidatingSessionManager.class, "validate", Session.class, SessionKey.class);
                    validateMethod.setAccessible(true);
                    ReflectionUtils.invokeJdbcMethod(validateMethod, sessionManager, session, new DefaultSessionKey(session.getId()));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            start += size;
            sessionList = jdbcTemplate.queryForList(sql, String.class, start, size);
        }
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void enableSessionValidation() {
        if (this.interval > 0l) {
            this.service = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    //cxy 守护线程
                    thread.setDaemon(true);
                    return thread;
                }
            });
            this.service.scheduleAtFixedRate(this, interval, interval, TimeUnit.SECONDS);
            this.enabled = true;
        }
    }

    public void disableSessionValidation() {
        this.service.shutdownNow();
        this.enabled = false;
    }
}
