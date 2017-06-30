package cn.cxy.shiro.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

/**
 * Function: 自定义实现 session 创建、停止、过期监听
 * Reason: 继承自 org.apache.shiro.session.SessionListenerAdapter 可以进行任意监听
 * Date: 2017/6/19 14:00 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class MySessionListener2 extends SessionListenerAdapter {
    @Override
    public void onStart(Session session) {
        System.out.println("MySessionListener2---------Session创建："+session.getId());
    }
}
