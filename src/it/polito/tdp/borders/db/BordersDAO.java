package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public List<Country> loadAllCountries()
	{
		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		
		try 
		{
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) 
			{
				Country c = new Country(rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
				result.add(c);
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno) 
	{
		String sql = "SELECT * " + 
					"FROM CONTIGUITY " + 
					"WHERE year <= ?";
					//"GROUP BY state1no, state2no";
					
		List<Border> result = new ArrayList<Border>();
		
		try 
		{
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) 
			{
				Border b = new Border(rs.getInt("state1no"), rs.getInt("state2no"), 
										rs.getString("state1ab"), rs.getString("state2ab"),
										rs.getInt("dyad"), rs.getInt("year"), 
										rs.getInt("conttype"), rs.getDouble("version"));
				result.add(b);
											
			}
			
			conn.close();
			return result;

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
}
