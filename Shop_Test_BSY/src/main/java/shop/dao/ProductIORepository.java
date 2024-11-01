package shop.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import shop.dto.Product;

public class ProductIORepository extends JDBConnection {
	
	/**
	 * 
	 * @param product
	 * @param type
	 * @return
	 */
	public int insert(Product product, String type) {
		int result = 0;
		String SQL = " INSERT INTO product_io ( "
				   + " ,product_id "
				   + " ,order_no "
				   + " ,amount "
				   + " ,type "
				   + " ,io_date "
				   + " ,user_id ) "
				   + " VALUES ( ?, ?, ?, ?, ?, ? ) "
				   ;
		

		try {
			psmt = con.prepareStatement(SQL);
			psmt.setString(1, product.getProductId());
			psmt.setInt(2, product.getOrderNo());
			psmt.setInt(3, product.getAmount());
			psmt.setString(4, product.getType());
			psmt.setString(5, product.getIoDate());
			psmt.setString(6, product.getUserId());
			
			result = psmt.executeUpdate();
			
		} catch (Exception e) {
			System.err.println("입출고 목록 등록시 예외 발생...");
			e.printStackTrace();
		}
		
		if(result > 0) {
			ProductRepository productRepository = new ProductRepository();
			if( type == "IN")
			{
				product.setUnitsInStock( product.getUnitsInStock() + product.getAmount() );
				productRepository.update(product);
			}
			else {
				product.setUnitsInStock( product.getUnitsInStock() - product.getAmount() );
				productRepository.update(product);
			}
		}
		
		return result;
	}

}