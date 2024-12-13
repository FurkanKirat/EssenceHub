package com.essencehub.project.DatabaseOperations.InsertData;

import java.util.Random;

import com.essencehub.project.User.AdminOperations;
import com.essencehub.project.User.User;
import static com.essencehub.project.User.Performance.F;
import static com.essencehub.project.User.Performance.A;

public class CreateUsers {
    private String[] names = {
            "John", "Jane", "Michael", "Emily", "David", "Sarah", "Robert", "Jessica", "Daniel", "Laura",
            "Chris", "Anna", "James", "Megan", "Andrew", "Samantha", "Matthew", "Olivia", "Joshua", "Sophia",
            "Ryan", "Emma", "Jacob", "Isabella", "Ethan", "Mia", "Alexander", "Charlotte", "Benjamin", "Amelia",
            "Samuel", "Grace", "Joseph", "Lily", "Henry", "Chloe", "Logan", "Ella", "William", "Ava",
            "Nathan", "Hannah", "Anthony", "Zoe", "Aaron", "Victoria", "Adam", "Natalie", "Thomas", "Brooklyn"
    };

    private String[] surnames = {
            "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez",
            "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin",
            "Lee", "Perez", "Thompson", "White", "Harris", "Sanchez", "Clark", "Ramirez", "Lewis", "Robinson",
            "Walker", "Young", "Allen", "King", "Wright", "Scott", "Torres", "Nguyen", "Hill", "Flores",
            "Green", "Adams", "Nelson", "Baker", "Hall", "Rivera", "Campbell", "Mitchell", "Carter", "Roberts"
    };

    private String[] passwords = {
            "1234", "542", "ab23", "xYz9", "789",
            "4pQr", "AB12", "qwe", "2kL", "5dfg",
            "lm3", "9876", "3xy", "abc2", "T56",
            "wX8", "pq12", "4Fg5", "nM7", "XZ3",
            "qTu1", "89K", "vZ5", "34r", "abc5",
            "2bC3", "lmN4", "890", "tuv", "aB9",
            "CdE2", "456", "mNq", "X23", "pQr5",
            "789", "qwe1", "TY67", "kLp3", "4df5",
            "567", "mn1", "xyZ", "12Tu", "7Kp",
            "X9q", "f5Fg", "12zN", "3Mn", "Zpq5"
    };

    private String[] departments = {
            "Software Development", "IT Support", "Network Administration", "Cybersecurity",
            "Data Analysis", "Project Management", "Web Development", "Quality Assurance",
            "System Engineering", "Technical Support"
    };

    private String[] phoneNumbers = new String[50];
    private double[] salaries = new double[50];
    private boolean[] isAdmin = {true, false, false, false, false};
    private String[] birthDates = new String[50];

    public CreateUsers() {
        initializePhoneNumbers();
        initializeSalaries();
        initializeBirthDates();
    }

    private void initializePhoneNumbers() {
        Random random = new Random();
        for (int i = 0; i < phoneNumbers.length; i++) {
            phoneNumbers[i] = "5" + (100000000 + random.nextInt(900000000)); // Example: 5XXXXXXXXX
        }
    }

    private void initializeSalaries() {
        Random random = new Random();
        for (int i = 0; i < salaries.length; i++) {
            salaries[i] = 3000 + random.nextDouble() * 7000; // Range: 3000 to 10000
        }
    }

    private void initializeBirthDates() {
        Random random = new Random();
        for (int i = 0; i < birthDates.length; i++) {
            int day = 1 + random.nextInt(28); // 1 to 28
            int month = 1 + random.nextInt(12); // 1 to 12
            int year = 1970 + random.nextInt(30); // 1970 to 2000
            birthDates[i] = String.format("%02d-%02d-%d", day, month, year); // Format: DD-MM-YYYY
        }
    }

    public String generateEmail(String name, String surname) {
        return name.toLowerCase() + "." + surname.toLowerCase() + "@company.com";
    }

    public String getRandomName() {
        Random random = new Random();
        return names[random.nextInt(names.length)];
    }

    public String getRandomSurname() {
        Random random = new Random();
        return surnames[random.nextInt(surnames.length)];
    }

    public String getRandomPassword() {
        Random random = new Random();
        return passwords[random.nextInt(passwords.length)];
    }

    public String getRandomDepartment() {
        Random random = new Random();
        return departments[random.nextInt(departments.length)];
    }

    public String getRandomPhoneNumber() {
        Random random = new Random();
        return phoneNumbers[random.nextInt(phoneNumbers.length)];
    }

    public double getRandomSalary() {
        Random random = new Random();
        return salaries[random.nextInt(salaries.length)];
    }

    public boolean getRandomIsAdmin() {
        Random random = new Random();
        return isAdmin[random.nextInt(isAdmin.length)];
    }

    public String getRandomBirthDate() {
        Random random = new Random();
        return birthDates[random.nextInt(birthDates.length)];
    }

    public static void main(String[] args) {
        CreateUsers createUsers = new CreateUsers();



        String nameEmployee = createUsers.getRandomName();
        String surnameEmployee = createUsers.getRandomSurname();
        User employee = new User(nameEmployee, surnameEmployee, createUsers.getRandomPhoneNumber(), createUsers.getRandomSalary(), false, createUsers.getRandomBirthDate(),
                createUsers.getRandomDepartment(), createUsers.generateEmail(nameEmployee, surnameEmployee), 40, true, createUsers.getRandomPassword(),
                F, 0, "/com/essencehub/project/images/ProfilePictures/defaultpicture1.png");

        String nameAdmin = createUsers.getRandomName();
        String surnameAdmin = createUsers.getRandomSurname();
        User admin = new User(nameAdmin, surnameAdmin, createUsers.getRandomPhoneNumber(), createUsers.getRandomSalary(), true, createUsers.getRandomBirthDate(),
                createUsers.getRandomDepartment(), createUsers.generateEmail(nameAdmin, surnameAdmin), 40, true, createUsers.getRandomPassword(),
                A, 370, "/com/essencehub/project/images/ProfilePictures/defaultpicture1.png");

        AdminOperations.addUser(employee); //ADDS EMPLOYEE
        AdminOperations.addUser(admin); //REMOVE FROM COMENT ADMIN ADMIN WITH RUN
        System.out.println("The code worked");
    }
}