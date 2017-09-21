package labactivity1;
import java.util.*;
import java.sql.*;



public class AddToDatabase 
{
    private static final long LIMIT = 1000L;
    private static long last = 0;
    public static long getID() {
    Long id = System.currentTimeMillis() % LIMIT;
    if ( id <= last ) {
        id = (last + 1) % LIMIT;
    }
    return last = id;
    }
    
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:sqlserver://localhost:1433;integratedSecurity=true;databaseName=LabActivity1";
    
    static final String USER = "username";
    static final String PASS = "password";
    
    Connection conn = null;
    Statement stmt = null;
    
    Scanner scan = new Scanner(System.in);
    
    public void addToTable()
    {
            System.out.println("Connected to the database.");
            String admin = "Admin";
            String student = "Student";
            System.out.println("Please follow the instructions.");
            System.out.println("The ID will be automatically generated by the system.");
            System.out.println("Please enter the first name of the new user: ");
            String newFirst = scan.next();
            System.out.println("Please enter the last name of the new user: ");
            String newLast = scan.next();
            System.out.println("Is the new user an Admin or Student?");
            String newUserType = scan.next();
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            if(newUserType.equals(admin) || (newUserType.equals(student)))
            {
                Long newID = getID();
                String sql = "INSERT INTO User_Table(FirstName, LastName, UserType, ID)" + "VALUES (?, ?, ?, ?)";
                PreparedStatement prpstmt = conn.prepareStatement(sql);
                prpstmt.setString(1,newFirst);
                prpstmt.setString(2,newLast);
                prpstmt.setString(3,newUserType);
                prpstmt.setLong(4,newID);
                prpstmt.execute();
                System.out.println("A record has been inserted!");
                stmt.close();
                conn.close();
            }
            else
            {
                System.out.println("ERROR! Please enter Admin or Student only.");
            }
        }catch(SQLException se){
                 se.printStackTrace();
                }catch(Exception e){
                    e.printStackTrace();
                }finally{
                    try{
                        if(stmt!=null)
                            stmt.close();
                    }catch (SQLException se2){
                    }
                    try{
                        if(conn!=null)
                            conn.close();
                    }catch(SQLException se){
                        se.printStackTrace();
                    }
                }
    }
}