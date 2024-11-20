package com.essencehub.project.User;

public enum AuthorizedDegree {
    ADMIN(3),    
    MANAGER(2),  
    EMPLOYEE(1); 

    private final int level;

    AuthorizedDegree(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public static AuthorizedDegree fromLevel(int level) {
        for (AuthorizedDegree degree : values()) {
            if (degree.getLevel() == level) {
                return degree;
            }
        }
        throw new IllegalArgumentException("Geçersiz yetki seviyesi: " + level);
    }
}
