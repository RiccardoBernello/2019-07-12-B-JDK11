package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Cibo;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods() {
		String sql = "SELECT * FROM food";
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			List<Food> list = new ArrayList<>();

			ResultSet res = st.executeQuery();

			while (res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"), res.getString("display_name")));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public List<Condiment> listAllCondiments() {
		String sql = "SELECT * FROM condiment";
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			List<Condiment> list = new ArrayList<>();

			ResultSet res = st.executeQuery();

			while (res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"), res.getString("display_name"),
							res.getDouble("condiment_calories"), res.getDouble("condiment_saturated_fats")));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Portion> listAllPortions() {
		String sql = "SELECT * FROM portion";
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			List<Portion> list = new ArrayList<>();

			ResultSet res = st.executeQuery();

			while (res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"), res.getDouble("portion_amount"),
							res.getString("portion_display_name"), res.getDouble("calories"),
							res.getDouble("saturated_fats"), res.getInt("food_code")));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public List<Cibo> getNodi(Integer calorie) {
		String sql = "Select f.food_code as ID, f.display_name as nome,  "
				+ "       Count(Distinct(p.portion_id)) as peso " + "from food as f, `portion` as p "
				+ "where p.food_code=f.`food_code` " + "group by f.food_code having peso>=? ";
		List<Cibo> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, calorie);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				try {
					Cibo c= new Cibo(res.getInt("ID"), res.getString("nome"));
					result.add(c);
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
	public List<Adiacenza> getArchi() {
		String sql = "Select f1.food_code as ID1, f1.display_name as nome1, " + 
				"       f2.food_code as ID2, f2.display_name as nome2, " + 
				"       (Avg(p1.saturated_fats)-Avg(p2.saturated_fats)) as peso " + 
				"from food as f1,food as f2, `portion` as p1, `portion` as p2 " + 
				"where f1.food_code=p1.food_code and f2.food_code=p2.food_code and f1.food_code>f2.food_code " + 
				"Group by ID1, ID2 " + 
				"having peso!=0 ";
		List<Adiacenza> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				try {
					Cibo c1= new Cibo(res.getInt("ID1"), res.getString("nome1"));
					Cibo c2= new Cibo(res.getInt("ID2"), res.getString("nome2"));
					result.add(new Adiacenza(c1, c2, res.getDouble("peso")));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
}
