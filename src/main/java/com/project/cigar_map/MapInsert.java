package com.project.cigar_map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MapInsert {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "jdbc:mysql://localhost:3306/cigar";
		String user = "cigar";
		String password = "123159";
		
		Connection conn = null;
		Statement stmt = null;
		
		BufferedReader br = null;
		
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				conn = DriverManager.getConnection(url, user, password);
				stmt = conn.createStatement();
				br = new BufferedReader(new FileReader("C:\\eGovFrameDev-4.0.0-64bit\\workspace\\Project_Cigar\\src\\main\\webapp\\흡연구역 데이터.csv"));
				String line = null;
				while((line=br.readLine())!=null) {
					String[] addresses = line.split(",");
					String sql = String.format( "insert into cigar_map_table values (0, '%s', '%s', '%s', '%s', '%s')",addresses[11], addresses[12], addresses[1], addresses[8], addresses[14]);
					//System.out.println(sql);
					stmt.executeUpdate(sql);
				}
				System.out.println("저장 완료");	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			} finally {
				if(br !=null) try {br.close();} catch(IOException e) {}
				if(stmt !=null) try {stmt.close();} catch(SQLException e) {}
				if(conn !=null) try {conn.close();} catch(SQLException e) {}
			}
	}
}