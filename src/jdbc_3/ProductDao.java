package jdbc_3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ProductDao {
	
	// 모든 상품정보를 반환하는 기능
	public List<Product> getAllProducts() throws SQLException {
		String sql = """
			select 
				prod_no, prod_name, prod_maker, prod_price, prod_discount_price,
				prod_stock, prod_sold_out, prod_created_date, prod_updated_date
			from 
				sample_products
			order
				by prod_no desc
		""";
		
		List<Product> productList = new ArrayList<Product>();
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			int no = rs.getInt("prod_no");
			String name = rs.getString("prod_name");
			String maker = rs.getString("prod_maker");
			int price = rs.getInt("prod_price");
			int discountPrice = rs.getInt("prod_discount_Price");
			int stock = rs.getInt("prod_stock");
			String soldOut = rs.getString("prod_sold_out");
			Date createdDate = rs.getDate("prod_created_date");
			Date updatedDate = rs.getDate("prod_updated_date");
			
			Product product = new Product();
			product.setNo(no);
			product.setName(name);
			product.setMaker(maker);
			product.setPrice(price);
			product.setDiscountPrice(discountPrice);
			product.setStock(stock);
			product.setSoldOut(soldOut);
			product.setCreatedDate(createdDate);
			product.setUpdatedDate(updatedDate);
			
			productList.add(product);
		}
		rs.close();
		pstmt.close();
		connection.close();
		
		return productList;
	}
	
	// 상품번호에 해당하는 상품정보를 반환하는 기능
	public Product getProductByNo(int productNo) throws SQLException {
		String sql = """
			select
				 prod_no, prod_name, prod_maker, prod_price, prod_discount_price,
				 prod_stock, prod_sold_out, prod_created_date, prod_updated_date
			from 
				 sample_products
			where
				 prod_no = ?
	""";
	
	Product product = null;
	
	Connection connection = getConnection();
	PreparedStatement pstmt = connection.prepareStatement(sql);
	pstmt.setInt(1,productNo);
	ResultSet rs = pstmt.executeQuery();
	while(rs.next()) {
		int no = rs.getInt("prod_no");
		String name = rs.getString("prod_name");
		String maker = rs.getString("prod_maker");
		int price = rs.getInt("prod_price");
		int discountPrice = rs.getInt("prod_discount_price");
		int stock = rs.getInt("prod_stock");
		String soldOut = rs.getString("prod_sold_out");
		Date createdDate = rs.getDate("prod_created_date");
		Date updatedDate = rs.getDate("prod_updated_date");
		
		product = new Product();
		product.setNo(no);
		product.setName(name);
		product.setMaker(maker);
		product.setPrice(price);
		product.setDiscountPrice(discountPrice);
		product.setStock(stock);
		product.setCreatedDate(createdDate);
		product.setUpdatedDate(updatedDate);
	}
	rs.close();
	pstmt.close();
	connection.close();
	
	return product;
	
	}
	
	// 최저가격, 최고가격을 전달받아서 해당 가격범위에 속하는 상품정보를 반환하는 기능
	public List<Product> getProductsByPricerange(int min, int max) throws SQLException {
		String sql = """
			select
				prod_no, prod_name, prod_maker, prod_price, prod_discount_price,
				prod_stock, prod_sold_out, prod_created_Date, prod_updated_date
			from
				sample_products
			where
				prod_price >= ? and prod_price <= ?
			order by 
				prod_price asc
		""";
		
		List<Product> productList = new ArrayList<Product>();
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, min);
		pstmt.setInt(2, max);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			int no = rs.getInt("prod_no");
			String name = rs.getString("prod_name");
			String maker = rs.getString("prod_maker");
			int price = rs.getInt("prod_price");
			int discountPrice = rs.getInt("prod_discount_price");
			int stock = rs.getInt("prod_stock");
			Date createdDate = rs.getDate("prod_created_date");
			Date updatedDate = rs.getDate("prod_updated_date");
			
			Product product = new Product();
			product.setNo(no);
			product.setName(name);
			product.setMaker(maker);
			product.setPrice(price);
			product.setDiscountPrice(discountPrice);
			product.setStock(stock);
			product.setCreatedDate(createdDate);
			product.setUpdatedDate(updatedDate);
			
			productList.add(product);
		
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return productList;
	}
	
	// 신규  상품정보를 저장하는 기능
	public void insertProduct(Product product) throws SQLException {
		String sql = """
				insert into sample_products
				(prod_no, prod_name, prod_maker, prod_price, prod_discount_Price, prod_stock)
				values 
				(?, ?, ?, ?, ?, ?)
		""";
		Connection connection = getConnection();
		
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1,product.getNo());
		pstmt.setString(2,product.getName());
		pstmt.setString(3, product.getMaker());
		pstmt.setInt(4, product.getPrice());
		pstmt.setInt(5, product.getDiscountPrice());
		pstmt.setInt(6, product.getStock());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	// 상품번호를 전달받아서 해당 상품정보를  삭제하는 기능
	public void deleteProduct(int no) throws SQLException {
		String sql = """
				delete
						from sample_products
				where
						prod_no = ?
		""";
		Connection connection = getConnection();
		
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, no);
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	// 상품번호, 가격, 할인가격을 전달받아서 해당 상품의 가격, 할인가격을 수정하는 기능
	public void updateProductPrice(int no, int price, int discountPrice) throws SQLException {
		String sql = """
			update sample_products
			set
					 prod_price = ?,
					 prod_discount_price = ?,
					 prod_updated_date = sysdate
			where 
					prod_no = ?
		""";
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, no);
		pstmt.setInt(2, price);
		pstmt.setInt(3, discountPrice);
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	// 상품번호, 수량을 전달받아서 해당상품의 수량을 변경하는 기능
	public void updateProductStock(int no, int stock) throws SQLException {
		String sql = """
			update sample_products
			set		
					prod_stock = ?,
					prod_updated_date = sysdate
			where	
					prod_no = ?
		""";
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, stock);
		pstmt.setInt(2, no);
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	

	
	private Connection getConnection() throws SQLException {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException ex) {
			throw new SQLException(ex.getMessage(), ex);
		}
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "hr";
		String password = "zxcv1234";
		
		return DriverManager.getConnection(url, user, password);
	}
}
