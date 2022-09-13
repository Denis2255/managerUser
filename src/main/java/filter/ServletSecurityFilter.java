package filter;

import dao.UserDAO;
import model.User;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.nonNull;

public class ServletSecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain)
            throws IOException, ServletException {

        final HttpServletRequest request1 = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        final String login = request1.getParameter("login");
        final String password = request1.getParameter("password");

        @SuppressWarnings("unchecked")
        final AtomicReference<UserDAO> dao = (AtomicReference<UserDAO>) request1.getServletContext().getAttribute("dao");
        final HttpSession session = request1.getSession();

        if (nonNull(session) &&
                nonNull(session.getAttribute("login")) &&
                nonNull(session.getAttribute("password"))) {

            final User.ROLE role = (User.ROLE) session.getAttribute("role");
            moveToMenu(request1, res, role);
        } else if (dao.get().userIsExist(login, password)) {
            final User.ROLE role = dao.get().getRoleByLoginPassword(login, password);
            request1.getSession().setAttribute("login", login);
            request1.getSession().setAttribute("password", password);
            request1.getSession().setAttribute("role", role);
            moveToMenu(request1, res, role);
        } else {
            moveToMenu(request1, res, User.ROLE.UNKNOWN);
        }
    }
    private void moveToMenu( HttpServletRequest request, HttpServletResponse response, User.ROLE role)
            throws ServletException, IOException {
        if (role.equals(User.ROLE.ADMIN) | role.equals(User.ROLE.USER)) {
            request.getRequestDispatcher("/user").forward(request, response);
        } else {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }

}
