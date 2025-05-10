package refai.project.model;

public class Administrator {
    private String adminId;
    private String name;
    private String role;

    public Administrator() {
    }

    public Administrator(String adminId, String name, String role) {
        this.adminId = adminId;
        this.name = name;
        this.role = role;
    }

    // Getters and Setters
    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}