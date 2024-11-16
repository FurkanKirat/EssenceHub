package com.essencehub.project.Employees;

public abstract class Staff {
    private boolean isAdmin;
    private String ID;
    private String password;
    public Staff(String ID, String password) {
        this.ID = ID;
        this.password = password;
    }


    // Getter Setters
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
