package com.revature.gameStart.util;
import com.revature.gameStart.dtos.Principal;
import com.revature.gameStart.models.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * User session class
 */
public class UserSession {

    private static final UserSession userSession = new UserSession();
    private ArrayList<HttpSession> httpSessionArrayList = new ArrayList<>();
    private UserSession() {

    }

    /**
     * constructor for user session that returns a user session
     * @return returns a user session
     */
    public static UserSession getUserSession(){
        return userSession;
    }

    /**
     * returns a http session array list
     * @return returns a list of http session
     */
    public ArrayList<HttpSession> getHttpSessionArrayList() {
        return httpSessionArrayList;
    }

    /**
     * creates a http session and sets the attribute of that session to the user that logs in
     * @param req http request
     * @param user user
     */
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

    /**
     * checks the http session for a user
     * @param req http servelet request
     */
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

    /**
     * invalidates a user session and logs them out
     * @param req http servlet request
     */
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


