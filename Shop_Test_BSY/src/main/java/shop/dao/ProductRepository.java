package shop.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shop.dto.Product;

public class ProductRepository extends JDBConnection {
	/**
	 * 상품 목록
	 * @return
	 */
	public List<Product> list() {
		List<Product> proList = new ArrayList<Product>();
		String SQL = " SELECT * FROM product"
				   + " product_id "
				   ;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL);
			
			while ( rs.next()) {
				Product product = new Product();
				product.setProductId(rs.getString("product_id"));
				product.setName(rs.getString("name"));
				product.setUnitPrice(rs.getInt("unit_price"));
				product.setDescription(rs.getString("dexcription"));
				product.setManufacturer(rs.getString("manufacturer"));
				product.setCategory(rs.getString("category"));
				product.setUnitsInStock(rs.getLong("units_in_stock"));
				product.setCondition(rs.getString("`condition`"));
				product.setFile(rs.getString("file"));
				product.setQuantity(rs.getInt("quantity"));
				proList.add(product);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proList;
	}
	
	
	/**
	 * 상품 목록 검색
	 * @param keyword
	 * @return
	 */
	public List<Product> list(String keyword) {
		List<Product> proList = new ArrayList<Product>();	// 검색 결과를 저장할 Product 객체 리스트 생성
		String SQL = " SELECT * FROM product"
				   + " WHERE name LIKE concat ('%', ?, '%') "	// 상품 이름(name)에 keyword가 포함되어 있는지 확인
				   + " OR descrition LIKE concat ('%', ?, '%') "
				   + " OR manufacturer LIKE concat ('%', ?, '%') "
				   + " OR category LIKE concat ('%', ?, '%') "
				   ;
		try {
			psmt = con.prepareStatement(SQL);	// SQL 쿼리를 위한 PreparedStatement 객체를 생성
			psmt.setString(1, keyword);
			psmt.setString(2, keyword);
			psmt.setString(3, keyword);
			psmt.setString(4, keyword);
			
			rs = psmt.executeQuery();	// SQL 쿼리를 실행하고 결과를 ResultSet 객체에 저장
			
			while( rs.next() ) {	// 결과(ResultSet)에 다음 행이 있으면 반복
				Product product = new Product();	// 새 Product 객체 생성
				product.setProductId(rs.getString("product_id"));
				product.setName(rs.getString("name"));
				product.setUnitPrice(rs.getInt("unit_price"));
				product.setDescription(rs.getString("dexcription"));
				product.setManufacturer(rs.getString("manufacturer"));
				product.setCategory(rs.getString("category"));
				product.setUnitsInStock(rs.getLong("units_in_stock"));
				product.setCondition(rs.getString("`condition`"));
				product.setFile(rs.getString("file"));
				product.setQuantity(rs.getInt("quantity"));
				proList.add(product);	// 생성한 product 객체를 proList에 추가
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proList;	// Product 리스트 반환
	}
	
	/**
	 * 상품 조회
	 * @param productId
	 * @return
	 */
	public Product getProductById(String productId) {
		Product product = new Product();
		String SQL = " SELECT * FROM product "
				   + " WHERE product_id = ? "
				   ;
		try {
			psmt = con.prepareStatement(SQL);
			psmt.setString(1, product.getProductId());
			psmt.setString(2, product.getName());
			psmt.setInt(3, product.getUnitPrice());
			psmt.setString(4, product.getDescription());
			psmt.setString(5, product.getManufacturer());
			psmt.setString(6, product.getCategory());
			psmt.setLong(7, product.getUnitPrice());
			psmt.setString(8, product.getCondition());
			psmt.setString(9, product.getFile());
			psmt.setInt(10, product.getQuantity());
			
			rs = psmt.executeQuery();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return product;
	}
	
	
	/**
	 * 상품 등록
	 * @param product
	 * @return
	 */
	public int insert(Product product) {
		int result = 0;
		String SQL = " INSERT INTO product ( "
				   + " product_id "
				   + " ,name "
				   + " ,unit_price "
				   + " ,description "
				   + " ,manufacturer "
				   + " ,category "
				   + " ,units_in_stock "
				   + " ,`condition` "
				   + " ,file "
				   + " ,quantity ) "
				   + " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ) "
				   ;
		
		try {
			psmt = con.prepareStatement(SQL);
			psmt.setString(1, product.getProductId());
			psmt.setString(2, product.getName());
			psmt.setInt(3, product.getUnitPrice());
			psmt.setString(4, product.getDescription());
			psmt.setString(5, product.getManufacturer());
			psmt.setString(6, product.getCategory());
			psmt.setLong(7, product.getUnitsInStock());
			psmt.setString(8, product.getCondition());
			psmt.setString(9, product.getFile());
			psmt.setInt(10, product.getQuantity());
			
			result = psmt.executeUpdate();
			
		} catch (Exception e) {
			System.err.println("상품 등록 시 예외 발생 ...");
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 상품 수정
	 * @param product
	 * @return
	 */
	public int update(Product product) {
		int result = 0;
		String SQL = " UPDATE product SET "
				   + " product_id "
				   + " ,name "
				   + " ,unit_price "
				   + " ,description "
				   + " ,manufacturer "
				   + " ,category "
				   + " ,units_in_stock "
				   + " ,`condition` "
				   + " ,file "
				   + " ,quantity "
				   + " WHERE order_no = ? "
				   ;
		
		try {
			psmt = con.prepareStatement(SQL);
			psmt.setString(1, product.getProductId());
			psmt.setString(2, product.getName());
			psmt.setInt(3, product.getUnitPrice());
			psmt.setString(4, product.getDescription());
			psmt.setString(5, product.getManufacturer());
			psmt.setString(6, product.getCategory());
			psmt.setLong(7, product.getUnitsInStock());
			psmt.setString(8, product.getCondition());
			psmt.setString(9, product.getFile());	
			psmt.setInt(10, product.getQuantity());	
			
			result = psmt.executeUpdate();
			
		} catch (Exception e) {
			System.err.println("게시글 수정 시 예외 발생");
			e.printStackTrace();
		}
				
		return result;
	}
	
	
	
	/**
	 * 상품 삭제
	 * @param product
	 * @return
	 */
	public int delete(String productId) {
		int result = 0;
		String SQL = " DELETE FROM productId "
				   + " WHERE productId = ? "
				   ;
		try {
			psmt = con.prepareStatement(SQL);
			psmt.setString(1, productId);
			
			result = psmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}





























