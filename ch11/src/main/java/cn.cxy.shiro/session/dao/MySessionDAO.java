package cn.cxy.shiro.session.dao;

import cn.cxy.shiro.utils.JdbcTemplateUtils;
import cn.cxy.shiro.utils.SerializableUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Serializable;
import java.util.List;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/19 17:48 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class MySessionDAO extends CachingSessionDAO {


    protected void doUpdate(Session session) {
        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            return;//如果session过期/停止，没必要继续更新
        }
        JdbcTemplate jdbcTemplate = JdbcTemplateUtils.getInstance();
        String sql = "update sessions set session = ? where id = ?";
        jdbcTemplate.update(sql, SerializableUtils.serialize(session), session.getId());
    }

    protected void doDelete(Session session) {
        JdbcTemplate jdbcTemplate = JdbcTemplateUtils.getInstance();
        String sql = "delete from sessions where id = ?";
        jdbcTemplate.update(sql, session.getId());
    }

    protected Serializable doCreate(Session session) {
        JdbcTemplate jdbcTemplate = JdbcTemplateUtils.getInstance();
        Serializable sessionId = generateSessionId(session);
        //cxy 设置目标sessionID
        assignSessionId(session, sessionId);
        String sql = "insert into sessions(id,session) values(?,?)";
        jdbcTemplate.update(sql, sessionId, SerializableUtils.serialize(session));
        return session.getId();
    }

    protected Session doReadSession(Serializable sessionId) {
        JdbcTemplate jdbcTemplate = JdbcTemplateUtils.getInstance();
        String sql = "select session from sessions where id = ?";
        List<String> sessionStrList = jdbcTemplate.queryForList(sql, String.class, sessionId);
        if (CollectionUtils.isEmpty(sessionStrList)) {
            return null;
        }
        return SerializableUtils.deserialize(sessionStrList.get(0));
    }
}
