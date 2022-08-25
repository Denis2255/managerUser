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



    public void init() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);//get/post
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        Controller controller=new Controller();
        try {
            switch (action) {
                case "/new":
                    controller.showNewForm(request, response);
                    break;
                case "/insert":
                    controller.insertUser(request, response);
                    break;
                case "/delete":
                    controller.deleteUser(request, response);
                    break;
                case "/edit":
                    controller.showEditForm(request, response);
                    break;
                case "/update":
                    controller.editUser(request, response);
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