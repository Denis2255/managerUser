package filter;

import dao.UserDAO;
import model.User;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ServletSecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain)
            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        final String login = req.getParameter("login");
        final String password = req.getParameter("password");

        @SuppressWarnings("unchecked") final AtomicReference<UserDAO> dao = (AtomicReference<UserDAO>) req.getServletContext().getAttribute("dao");
        final HttpSession session = req.getSession();
        Cookie[] cookies = req.getCookies();
        if (!(cookies == null)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JWT")) {
                    final User.ROLE role = User.ROLE.ADMIN;
                    moveToMenu(req, res, role);
                } else if (dao.get().userIsExist(login, password)) {
                    final User.ROLE role = dao.get().getRoleByLoginPassword(login, password);
                    req.getSession().setAttribute("login", login);
                    req.getSession().setAttribute("password", password);
                    req.getSession().setAttribute("role", role);
                    try {
                        JWTcreate generator = new JWTcreate();
                        Map<String, String> claims = new HashMap<>();
                        claims.put("action", "read");
                        claims.put("sub", login);
                        claims.put("password", password);
                        claims.put("aud", "*");
                        String token = generator.generateJwt(claims);
                        System.out.println(token);
                        Cookie cookieToken = new Cookie("JWT", token);
                        res.addCookie(cookieToken);
                    } catch (Exception e) {
                        System.out.println("Exception JWT-token");
                    }
                    moveToMenu(req, res, role);
                } else {
                    moveToMenu(req, res, User.ROLE.UNKNOWN);
                }
            }
        }
    }

    private void moveToMenu(HttpServletRequest request, HttpServletResponse response, User.ROLE role)
            throws ServletException, IOException {

        if (role.equals(User.ROLE.ADMIN) | role.equals(User.ROLE.USER)) {
            request.getRequestDispatcher("/user").include(request, response);
        } else if (role.equals(User.ROLE.UNKNOWN)) {
            request.getRequestDispatcher("login.jsp").include(request, response);
        }
    }

    @Override
    public void destroy() {
    }

}
