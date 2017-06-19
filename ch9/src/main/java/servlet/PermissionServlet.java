package servlet;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/17 10:49 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
@WebServlet(value = "/permission")
public class PermissionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Subject subject = SecurityUtils.getSubject();
        subject.checkPermission("user:create");
        req.setAttribute("subject",subject);
        req.getRequestDispatcher("/WEB-INF/jsp/hasPermission.jsp").forward(req,resp);
    }
}
