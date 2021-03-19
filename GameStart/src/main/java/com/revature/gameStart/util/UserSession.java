package com.revature.gameStart.util;
import com.revature.gameStart.dtos.Principal;
import com.revature.gameStart.models.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public class UserSession {

    private static final UserSession userSession = new UserSession();
    private ArrayList<HttpSession> httpSessionArrayList = new ArrayList<>();
    private UserSession() {

    }
    public static UserSession getUserSession(){
        return userSession;
    }

    public ArrayList<HttpSession> getHttpSessionArrayList() {
        return httpSessionArrayList;
    }

    public void createSession(HttpServletRequest req , User user) {
        HttpSession httpSession = req.getSession();

        httpSession.setAttribute("ipAddress", req.getRemoteAddr());
        httpSession.setAttribute("userId", user.getId());
        httpSession.setAttribute("username", user.getUsername());
        httpSession.setAttribute("userRole", user.getRole().toString());
        httpSession.setAttribute("firstname", user.getFirstName());
        httpSession.setAttribute("lastname", user.getLastName());
        httpSession.setAttribute("email", user.getEmail());

        httpSession.setMaxInactiveInterval(-1);
        httpSessionArrayList.add(httpSession);
    }

    public void checkForUser(HttpServletRequest req){


        // Get the HTTP cookie named Authorization
        HttpSession httpSession = null;
        Principal principal = null;
//        Cookie[] cookies = req.getCookies();
        for (HttpSession httpSession1: httpSessionArrayList) {
            if(httpSession1.getAttribute("ipAddress").equals(req.getRemoteAddr())) {
                httpSession = httpSession1;
                break;
            }

        }

        if (httpSession != null)
        {
            principal = new Principal();
            principal.setId((Integer)httpSession.getAttribute("userId"));
            principal.setUsername((String)httpSession.getAttribute("username"));
            principal.setRole((String)httpSession.getAttribute("userRole"));
            principal.setFirstname((String)httpSession.getAttribute("firstname"));
            principal.setLastname((String)httpSession.getAttribute("lastname"));
            principal.setEmail((String)httpSession.getAttribute("email"));
            req.setAttribute("principal", principal);
        }
    }

    public void logoutUser(HttpServletRequest req) {

        for (HttpSession httpSession1: httpSessionArrayList) {
            if(((String)httpSession1.getAttribute("ipAddress")).equals(req.getRemoteAddr())) {
                httpSessionArrayList.remove(httpSession1);
                break;
            }
        }
        req.getSession().invalidate();

    }

//    public void deleteUser(int id) {
//        for (HttpSession httpSession1: httpSessionArrayList) {
//            if(((Integer)httpSession1.getAttribute("userId")) == id) {
//                httpSession1.invalidate();
//                httpSessionArrayList.remove(httpSession1);
//                break;
//
//            }
//        }
//    }
}


