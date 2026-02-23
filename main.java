package myPackage;

import java.sql.*;
import java.util.Scanner;

public class hotel {

    private static String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static String user_name = "root";
    private static String password = "SNmysql";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection con = DriverManager.getConnection(url, user_name, password);
            while (true) {
                System.out.println();
                System.out.println("===============");
                System.out.println("WELCOME TO SOMEN NANDI HOTEL");
                System.out.println("===============");
                System.out.println("1. New Reservation ");
                System.out.println("2. Check Reservation");
                System.out.println("3. Get Room Number");
                System.out.println("4. update Reservation ");
                System.out.println("5. Delete Reservation ");
                System.out.println("6. Exit ");
                System.out.print("Enter Your Choice : ");
                int choice = sc.nextInt();
                System.out.println();

                switch (choice) {
                    case 1: {
                        sc.nextLine();
                        System.out.print("Enter Name : ");
                        String name = sc.nextLine();
                        System.out.print("Enter Room Number : ");
                        int room_num = sc.nextInt();
                        System.out.print("Enter Contact Number : ");
                        String contact = sc.next();
                        new_reservation(con, name, room_num, contact);
                        break;
                    }
                    case 2: {
                        view_rev(con);
                        break;
                    }
                    case 3: {
                        System.out.print("Enter Name : ");
                        String name = sc.nextLine();
                        get_room(con, name);
                        break;
                    }

                    case 4: {
                        System.out.print("Enter reservation id : ");
                        int id = sc.nextInt();
                        System.out.print(" 1. Update name \n 2. Update Room No \n 3. Update Contact Number \n");
                        int cho = sc.nextInt();
                        switch (cho) {
                            case 1: {
                                sc.nextLine();
                                System.out.print("Enter new Name Number : ");
                                String new_name = sc.nextLine();
                                update_name(con, id, new_name);
                                break;
                            }
                            case 2: {
                                System.out.print("Enter new Room Number : ");
                                int new_room = sc.nextInt();
                                update_room(con, id, new_room);
                                break;
                            }
                            case 3: {
                                System.out.print("Enter new Contact Number : ");
                                String new_contact = sc.next();
                                update_contact(con, id, new_contact);
                                break;
                            }
                            default:
                                System.out.println("wrong input ");
                        }
                        break;
                    }

                    case 5: {
                        System.out.print("Enter id : ");
                        int id = sc.nextInt();
                        delete_rev(con, id);
                        break;
                    }

                    case 6: {
                        System.out.println("Thank you so much");
                        con.close();
                        System.exit(0);

                    }
                    default: {
                        System.out.println("Wrong input");
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void new_reservation(Connection con, String guest_name, int room_num, String contact_num) {
        String query = "INSERT INTO rev(guest_name,room_num,contact_num) VALUES(?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, guest_name);
            ps.setInt(2, room_num);
            ps.setString(3, contact_num);
            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Reversion done successfully"); 
            }else {
                System.out.println("Reservation Declined");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void view_rev(Connection con) throws SQLException {
        String view_query = "SELECT * FROM rev";
        PreparedStatement ps_view = con.prepareStatement(view_query);
        ResultSet rs = ps_view.executeQuery();
        while (rs.next()) {
            System.out.println("Guest ID : " + rs.getInt("rev_id"));
            System.out.println("Guest Name : " + rs.getString("guest_name"));
            System.out.println("Room number : " + rs.getInt("room_num"));
            System.out.println("Contact Number : " + rs.getString("contact_num"));
            System.out.println("Date : " + rs.getString("rev_date"));
            System.out.println();

        }
    }

    public static void get_room(Connection con, String name) throws SQLException {
        String query = "SELECT * FROM rev WHERE guest_name = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        boolean found = false;
        while (rs.next()) {
            found = true;
            System.out.println("Guest ID : " + rs.getInt("rev_id"));
            System.out.println("Guest Name : " + rs.getString("guest_name"));
            System.out.println("Room number : " + rs.getInt("room_num"));
            System.out.println("Contact Number : " + rs.getString("contact_num"));
            System.out.println("Date : " + rs.getString("rev_date"));
            System.out.println();

        }
        if (!found) {
            System.out.println("No data found with this name " + name);
        }
    }

    public static void update_name(Connection con, int id, String new_name) throws SQLException {
        String query = "UPDATE rev SET guest_name = ? WHERE rev_id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, new_name);
        ps.setInt(2, id);
        int rs = ps.executeUpdate();
        if (rs > 0) {
            System.out.println("Update Successfully"); 
        }else {
            System.out.println("Update not successfully");
        }

    }

    public static void update_room(Connection con, int id, int new_room_no) throws SQLException {
        String query = "UPDATE rev SET room_num = ? WHERE rev_id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, new_room_no);
        ps.setInt(2, id);
        int rs = ps.executeUpdate();
        if (rs > 0) {
            System.out.println("Update Successfully"); 
        }else {
            System.out.println("Update not successfully");
        }

    }

    public static void update_contact(Connection con, int id, String new_contact) throws SQLException {
        String query = "UPDATE rev SET contact_num = ? WHERE rev_id = ?";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setString(1, new_contact);
        ps.setInt(2, id);
        int rs = ps.executeUpdate();
        if (rs > 0) {
            System.out.println("Update Successfully"); 
        }else {
            System.out.println("Update not successfully");
        }

    }

    public static void delete_rev(Connection con, int id) throws SQLException {
        String query = "DELETE FROM rev WHERE rev_id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        int rs = ps.executeUpdate();

        if (rs > 0) {
            System.out.println(id + " is deleted successfully "); 
        }else {
            System.out.println("Delete not Successfully");
        }

    }
}
