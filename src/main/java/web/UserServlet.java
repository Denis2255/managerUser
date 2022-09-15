package web;

import java.io.IOException;

import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.Controller;
import model.User;


@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private final String NEW = "/new";
    private final String INSERT = "/insert";
    private final String DELETE = "/delete";
    private final String EDIT = "/edit";
    private final String UPDATE = "/update";
    private final String LOGOUT = "/logout";

    Controller controller = new Controller();
    String action;

    public void init() {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        action = request.getServletPath();
        final HttpSession session = request.getSession();
        final User.ROLE role = (User.ROLE) session.getAttribute("role");
        if (role.equals(User.ROLE.USER)) {
            try {
                controller.listUserUser(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            switch (action) {
                case INSERT:
                    controller.insertUser(request, response);
                    break;
                case UPDATE:
                    controller.editUser(request, response);
                    break;
                default:
                    controller.listUser(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException();
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        if (!(cookies == null)) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        session.removeAttribute("password");
        session.removeAttribute("login");
        session.removeAttribute("role");
        response.sendRedirect(super.getServletContext().getContextPath());
        action = request.getServletPath();
        try {
            switch (action) {
                case NEW:
                    controller.showNewForm(request, response);
                    break;
                case EDIT:
                    controller.showEditForm(request, response);
                case LOGOUT:
                    controller.deleteCookie(request, response);
                    break;
                case DELETE:
                    controller.deleteUser(request, response);
                    break;
                default:
                    controller.listUser(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

}