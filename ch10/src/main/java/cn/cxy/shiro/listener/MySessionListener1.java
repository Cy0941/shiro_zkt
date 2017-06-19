package cn.cxy.shiro.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * Function: 自定义实现 session 创建、停止、过期监听
 * Reason: 实现 org.apache.shiro.session.SessionListener 接口则必须实现所有接口
 * Date: 2017/6/19 13:56 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class MySessionListener1 implements SessionListener {

    public void onStart(Session session) {
        System.out.println("Session创建："+session.getId());
    }

    public void onStop(Session session) {
        System.out.println("Session过期："+session.getId());
    }

    public void onExpiration(Session session) {
        System.out.println("Session停止："+session.getId());
    }
}
