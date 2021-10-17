
package Students;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class studentDB {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        String derbyClientDriver = "org.apache.derby.jdbc.ClientDriver";
       
        Class.forName(derbyClientDriver);
        
        String url = "jdbc:derby://localhost:1527/student";
        String user = "app";
        String pw = "app"; //password

        Connection con = DriverManager.getConnection(url, user, pw);
        
        Statement stmt = con.createStatement();
        Student stu1 = new Student(1, "John", 3.00);
        Student stu2 = new Student(2, "Marry", 4.00);
        
        /* คำสั่งasm */
        insertStudentPreparedStatement(con, stu1);
        insertStudentPreparedStatement(con, stu2);

        Student stu = getStudentByIdPreparedStatement(con, 1);
        stu.setName("Jack");
        stu.setGpa(2.0);
        updateStudentNamePreparedStatement(con, stu);
        updateStudentGpaPreparedStatement(con, stu);
        deleteStudentPreparedStatement(con, stu);
       
        stmt.close();
        con.close();
    }

    public static void printAllStudent(ArrayList<Student> studentList) {
        for (Student stu : studentList) {
            System.out.print(stu.getId() + " ");
            System.out.print(stu.getName() + " ");
            System.out.println(stu.getGpa() + " ");
        }
    }

    public static ArrayList<Student> getAllStudent(Connection con) throws SQLException {
        String sql = "select * from student order by id";
        PreparedStatement ps = con.prepareStatement(sql);
        ArrayList<Student> studentList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Student student = new Student();
            student.setId(rs.getInt("id"));
            student.setName(rs.getString("name"));
            student.setGpa(rs.getDouble("gpa"));
            studentList.add(student);
        }
        rs.close();
        return studentList;

    }

    public static Student getStudentById(Statement stmt, int id) throws SQLException {
        Student stu = null;
        String sql = "select * from student where id = " + id;
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            stu = new Student();
            stu.setId(rs.getInt("id"));
            stu.setName(rs.getString("name"));
            stu.setGpa(rs.getDouble("Gpa"));
        }
        return stu;
    }

    public static void insertStudent(Statement stmt, Student stu) throws SQLException {
        
        String sql = "insert into student (id, name, gpa)" + " values (" + stu.getId() + "," + "'" + stu.getName() + "'"
                + "," + stu.getGpa() + ")";
        int result = stmt.executeUpdate(sql);
        System.out.println("Insert " + result + " row");
    }

    public static void deleteStudent(Statement stmt, Student stu) throws SQLException {
        String sql = "delete from student where id = " + stu.getId();
        int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("delete " + result + " row");
    }

    public static void updateStudent(Statement stmt, Student stu) throws SQLException {
        String sql = "update student set Gpa  = " + stu.getGpa() + " where id = " + stu.getId();
        int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("update " + result + " row");
    }

    public static void updateStudentName(Statement stmt, Student stu) throws SQLException {
        String sql = "update student set name  = '" + stu.getName() + "'" + " where id = " + stu.getId();
        int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("update " + result + " row");
    }

    public static void insertStudentPreparedStatement(Connection con, Student stu) throws SQLException {
        String sql = "insert into Student (id, name, gpa)" + " values (?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, stu.getId()); // 1 ?
        ps.setString(2, stu.getName()); // 2 ?
        ps.setDouble(3, stu.getGpa()); //3 ?
        int result = ps.executeUpdate();

        //display result
        System.out.println("Insert " + result + " row");
    }

    public static void deleteStudentPreparedStatement(Connection con, Student stu) throws SQLException {
        String sql = "delete from Student where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, stu.getId());
        int result = ps.executeUpdate();

        //display result
        System.out.println("Delete " + result + " row");
    }

    public static void updateStudentGpaPreparedStatement(Connection con, Student stu) throws SQLException {
        String sql = "update Student set Gpa  = ? where id = ? ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDouble(1, stu.getGpa());
        ps.setInt(2, stu.getId());
        int result = ps.executeUpdate();

        //display result
        System.out.println("update " + result + " row");
    }

    public static void updateStudentNamePreparedStatement(Connection con, Student stu) throws SQLException {
        String sql = "update Student set name  = ? where id = ? ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, stu.getName());
        ps.setInt(2, stu.getId());
        int result = ps.executeUpdate();
        
        //display result
        System.out.println("update " + result + " row");
    }

    public static Student getStudentByIdPreparedStatement(Connection con, int id) throws SQLException {
        Student stu = null;
        String sql = "select * from Student where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            stu = new Student();
            stu.setId(rs.getInt("id"));
            stu.setName(rs.getString("name"));
            stu.setGpa(rs.getDouble("Gpa"));
        }
        return stu;
    }
}