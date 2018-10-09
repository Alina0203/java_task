package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class connect {

	public static void main(String[] args) throws SQLException {

		connect c = new connect();
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/books";
			String login = "postgres";
			String password = "root";
			Connection con = DriverManager.getConnection(url, login, password);
			System.out.println("Connecting to database....");

			try {

				// c.insert(con, "Изучаем Java", "Берт Бейтс", 2012);
				// c.insert(con, "Изучаем Java","Кэти Сьерра",2012);
				// c.insert(con, "Паттерны проектирования","Берт Бейтс",2013);
				// c.insert(con, "Паттерны проектирования","Кэти Сьерра",2013);
				// c.insert(con, "Java 8. Полное руководство","Герберт Шилдт",2015);
				// c.insert(con, "Полный справочник по Java 7-е издание","Герберт Шилдт",2007);
				// c.insert(con, "Java Методы программирования","Игорь Блинов",2013);
				// c.insert(con, "Java Методы программирования","Валерий Романчик",2013);
				c.select(con);
			} finally {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void insert(Connection con, String Book_Name, String Full_Name, Integer Book_Year) throws SQLException {
		PreparedStatement stmt = con.prepareStatement("INSERT INTO author (Book_Name,Full_Name,Book_Year) VALUES (?,?,?)");
		stmt.setString(1, Book_Name);
		stmt.setString(2, Full_Name);
		stmt.setLong(3, Book_Year);
		stmt.executeUpdate();
		stmt.close();
	}

	private void select(Connection con) throws SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM author where Book_Year= 2013");
		while (rs.next()) {
			String str = rs.getString("Full_Name") + ";" + rs.getString(2);
			System.out.println("Contact :" + str);
		}
		rs.close();
		stmt.close();
	}
}
