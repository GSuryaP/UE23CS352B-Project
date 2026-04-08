package com.pesu.expensetracker.util;

import com.pesu.expensetracker.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class SessionManager {
    private User loggedInUser;

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser() {
        return this.loggedInUser;
    }

    public boolean isLoggedIn() {
        return this.loggedInUser != null;
    }

    public void logout() {
        this.loggedInUser = null;
    }

    public boolean isAdmin() {
        return this.loggedInUser != null && this.loggedInUser.isAdmin();
    }
}
