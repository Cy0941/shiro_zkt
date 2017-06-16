package cn.cxy.shiro.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/16 18:57 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
@WebServlet(value = "/unauthorized")
public class UnauthorizedServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/unauthorized.jsp").forward(req,resp);
    }
}
