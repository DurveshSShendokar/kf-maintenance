package com.kfMaintenancce.dto;

public class UserDetailsDTO {
    private String firstName;
    private String lastName;
    private String emailId;
    private String userName;
    private String contactNo;
    private String roleName;
    private String departmentName;

    // Constructor
    public UserDetailsDTO(String firstName, String lastName, String emailId,
                          String userName, String contactNo, String roleName,
                          String departmentName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.userName = userName;
        this.contactNo = contactNo;
        this.roleName = roleName;
        this.departmentName = departmentName;
    }

    // Getters & Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getContactNo() { return contactNo; }
    public void setContactNo(String contactNo) { this.contactNo = contactNo; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
}
