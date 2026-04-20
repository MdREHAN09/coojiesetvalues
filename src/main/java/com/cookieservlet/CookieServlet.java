package com.cookieservlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/CookieServlet")
public class CookieServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String userName = request.getParameter("userName");

        String name = null;
        int count = 0;

        // 🔹 Read cookies
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("user")) {
                    name = c.getValue();
                }
                if (c.getName().equals("count")) {
                    try {
                        count = Integer.parseInt(c.getValue());
                    } catch (Exception e) {
                        count = 0;
                    }
                }
            }
        }

        // 🔹 First time entry
        if (userName != null && !userName.isEmpty()) {
            name = userName;
            count = 0;
        }

        // 🔹 Increment visit count
        count++;

        // 🔹 Create cookies
        Cookie userCookie = new Cookie("user", name);
        Cookie countCookie = new Cookie("count", String.valueOf(count));

        // 🔹 Expiry (30 sec demo)
        userCookie.setMaxAge(30);
        countCookie.setMaxAge(30);

        response.addCookie(userCookie);
        response.addCookie(countCookie);

        // 🔹 OUTPUT
        out.println("<html><body style='text-align:center;'>");

        if (name != null) {
            out.println("<h2 style='color:blue;'>Welcome back, " + name + "!</h2>");
            out.println("<h3 style='color:green;'>You have visited this page " + count + " times</h3>");
        }

        // 🔹 Cookie list
        out.println("<h3>🍪 Cookie Details:</h3>");
        out.println("<p>User Cookie → " + name + "</p>");
        out.println("<p>Visit Count → " + count + "</p>");

        // 🔹 Expiry info
        out.println("<h4 style='color:red;'>Note: Cookies expire in 30 seconds</h4>");

        // 🔹 Back button
        out.println("<br><a href='index.html'>Go Back</a>");

        // 🔹 Logout button
        out.println("<form action='CookieServlet' method='post'>");
        out.println("<input type='submit' value='Logout'>");
        out.println("</form>");

        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔹 Delete cookies
        Cookie user = new Cookie("user", "");
        user.setMaxAge(0);

        Cookie count = new Cookie("count", "");
        count.setMaxAge(0);

        response.addCookie(user);
        response.addCookie(count);

        response.sendRedirect("index.html");
    }
}