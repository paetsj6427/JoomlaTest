package CucumberTest.JoomlaTest;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

//@SuppressWarnings("unused")
public class DBQuery {
	
	private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    ArrayList<String[]> result = new ArrayList<String[]>();

    private String host = "127.0.0.1";
    private String dbName = "testrail";
    private String dbTable = "users";
    private String user = "testrail_adm_t";
    private String pass = ""; 
    
    public DBQuery() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Fehler bei MySQL-JDBC-Bridge" + e);
            return;
        }

        readDBData(0);

        printDBData(result);

        System.out.println("Anzahl Tupel: " + result.size());
    }

    private void readDBData(int i) {

        switch (i) {
        case 0:
            host = JOptionPane.showInputDialog("Datenbankhost:");
            if (host.equals("")) {
                System.err.println("Bitte einen Datenbankhost angeben!");
                readDBData(i);
            }
            i++;

        case 1:
            dbName = JOptionPane.showInputDialog("Datenbankname:");
            if (dbName.equals("")) {
                System.err.println("Bitte einen Datenbanknamen angeben!");
                readDBData(i);
            }
            i++;

        case 2:
            dbTable = JOptionPane.showInputDialog("Tabelle:");
            if (dbTable.equals("")) {
                System.err.println("Bitte eine Tabelle angeben!");
                readDBData(i);
            }
            i++;

        case 3:
            user = JOptionPane.showInputDialog("Benutzername:");
            if (user.equals("")) {
                System.err.println("Bitte einen Benutzernamen angeben!");
                readDBData(i);
            }
            i++;

        case 4:
            pass = JOptionPane.showInputDialog("Passwort:");
            break;
        }

        try {
            
            String url = "jdbc:mysql://" + host + "/" + dbName;
            connection = DriverManager.getConnection(url, user, pass);
            statement = connection.createStatement();

            String sqlQuery = "SELECT * FROM " + dbTable;
            resultSet = statement.executeQuery(sqlQuery);
            int spalten = resultSet.getMetaData().getColumnCount();
            System.out.println("Anzahl Spalten: " + spalten);

            while (resultSet.next()) {
                String[] str = new String[8];
                for (int k = 1; k <=spalten; k++) {
                    str[k - 1] = resultSet.getString(k);
                }
                result.add(str);
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Fehler bei Tabellenabfrage: " + e);
            return;
        }
    }

    private void printDBData(ArrayList<String[]> list) {
        for (Iterator<String[]> iter = list.iterator(); iter.hasNext();) {
            String[] str = (String[]) iter.next();

            for (int i = 0; i < str.length; i++) {
                System.out.print(str[i] + "\t");
            }
            System.out.print(System.getProperty("line.separator"));
        }
    }

    public static void main(String args[]) {
        new DBQuery();
    }
    

}
