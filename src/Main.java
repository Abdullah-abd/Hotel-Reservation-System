import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hotel_res_system";
        String username = "root";
        String password = "Abdull@0735";
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Congratulations: database connected successfully");
            Statement smt = connection.createStatement();
            Scanner scanner = new Scanner(System.in);
            while (true){
                System.out.println("Hotel Reservation System");
                System.out.println("1 Reserve a room");
                System.out.println("2 View Reservation");
                System.out.println("3 Get room number");
                System.out.println("4 Update Details");
                System.out.println("5 Delete Reservation");
                System.out.println("0 Exit");
                System.out.println("CHOOSE AN OPTION");
                int choice = scanner.nextInt();
                switch (choice){
                    case 1:
                        reserveRoom( scanner, smt);
                        break;
                    case 2:
                        viewReservation(smt);
                        break;
                    case 3:
                        getRoom( scanner, smt);
                        break;
                    case 4:
                        updateReservation( scanner, smt);
                        break;
                    case 5:
                        deleteReservation( scanner, smt);
                        break;

                }

            }
        }
        catch (SQLException e){
            System.err.println("connection failed!"+ e.getMessage());
        }
    }

    private static void deleteReservation( Scanner scanner, Statement smt) {
        System.out.println("Enter id");
        int id = scanner.nextInt();
        String query = "DELETE FROM reservations WHERE reservation_id ="+ id;
        try{
            int rowsAffected = smt.executeUpdate(query);
            if(rowsAffected>0){
                System.out.println("Reservation deleted ");
            }else{
                System.out.println("Deletion failed");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private static void getRoom( Scanner scanner, Statement smt) {
        System.out.println("Enter Reservation id");
        int id = scanner.nextInt();
        System.out.println("Enter name");
        String name = scanner.next();
        String query = "SELECT room_number FROM reservations WHERE reservation_id="+id+" AND guest_name='"+name+"';";
        try {
            ResultSet rs = smt.executeQuery(query);
            if (rs.next()) {
                int roomNumber = rs.getInt("room_number");
                System.out.println("======================");
                System.out.println("Room No. of "+name +" is "+roomNumber);
            } else {
                System.out.println("======================");
                System.out.println("No matching reservation found for ID "+id+" and name "+name);
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void updateReservation( Scanner scanner, Statement smt) {
        System.out.println("Enter Reservation id");
        int id = scanner.nextInt();
        System.out.println();
        System.out.println("Enter name");
        String name = scanner.next();
        System.out.println("Enter Room Number");
        int roomNumber = scanner.nextInt();
        System.out.println("Enter Phone Number");
        String phoneNumber = scanner.next();
        String query = "UPDATE reservations SET guest_name = '" + name +"', room_number='"+roomNumber+"', contact_number='"+phoneNumber+"' WHERE reservation_id = "+id +";";
        try {
            int rowsAffected = smt.executeUpdate(query);
            if (rowsAffected>0){
                System.out.println("Reservation updated successfully");
            } else {
                System.out.println("Failed to update!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void viewReservation( Statement smt) {
        String query = "select * from reservations";
        try {
            ResultSet rs = smt.executeQuery(query);
            while(rs.next()){
                int id = rs.getInt("reservation_id");
                String name = rs.getString("guest_name");
                int roomNumber = rs.getInt("room_number");
                String phone = rs.getString("contact_number");
                Date date = rs.getDate("res_date");
                System.out.println(rs);
                System.out.println("======================");
                System.out.println("Reservation id: "+id);
                System.out.println("Name: "+name);
                System.out.println("Room No. : "+roomNumber);
                System.out.println("Phone Number: "+phone);
                System.out.println("Date: "+date);
                System.out.println();

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void reserveRoom( Scanner scanner, Statement smt) throws SQLException {
        System.out.println();
        System.out.println("Enter name");
        String name = scanner.next();
        System.out.println("Enter Room Number");
        int roomNumber = scanner.nextInt();
        System.out.println("Enter Phone Number");
        String phoneNumber = scanner.next();
        String query = "INSERT INTO reservations(guest_name,room_number,contact_number) values('" + name + "','"+ roomNumber + "','"+phoneNumber+"');";
        try {
            int rowAffected = smt.executeUpdate(query);
            if (rowAffected>0){
                System.out.println("Reservation Successful");
            } else {
                System.out.println("Reservation failed!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}