package web;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Controller;


@WebServlet("/")
public class UserServlet extends HttpServlet {
    private final String NEW = "/new";
    private final String INSERT = "/insert";
    private final String DELETE = "/delete";
    private final String EDIT = "/edit";
    private final String UPDATE = "/update";
    Controller controller = new Controller();
    String action;

    public void init() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        action = request.getServletPath();
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
        action = request.getServletPath();
        try {
            switch (action) {
                case NEW:
                    controller.showNewForm(request, response);
                    break;
                case EDIT:
                    controller.showEditForm(request, response);
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