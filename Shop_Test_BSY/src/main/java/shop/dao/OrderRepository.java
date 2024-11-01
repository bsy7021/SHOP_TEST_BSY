package shop.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shop.dto.Order;
import shop.dto.Product;

public class OrderRepository extends JDBConnection {
	
	/**
	 * 주문 등록
	 * @param user
	 * @return
	 */
	// 테이블명 변환 메소드(원래 테이블명 order)
	
	// 메소드 선언
	// - public : 전역
	// - String : 문자열 객체( 반환값이자 반환 객체 형식-> return할 때 똑같은 형식으로 해줘야한다)
	// - table() : 메소드명
	public String table() {
		// " " 감싸면 -> 문자열
		return "`order`";	// "``" -> ``로 감싸는 이유는 SQL 에서 order by 라는 예약어가 이미 있기 때문
	}
	
	// 메소드 선언	
	public int insert(Order order) {
		int result = 0;	// 반환할 변수 선언
		// INSERT 기본 세팅
		// String SQL = "  "
		// 			  + "  "
		// 			  ;
		String SQL = " INSERT INTO " + table() + " ( " 
				   + " ship_name "
				   + " ,zip_code "
				   + " ,country "
				   + " ,address "
				   + " ,date "
				   + " ,order_pw "
				   + " ,user_id "
				   + " ,total_price "
				   + " ,phone "
				   + " ) "	// 순서 : INSERT INTO 테이블명 컬럼명
				   + " VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? )"
				   ;
		// try ~ catch
		// try 안에서 실행한 결과가 오류 (Exception:에외처리) 발생 시 catch 부분에 선언한 실행문이 실행된다.
		try {
			psmt = con.prepareStatement(SQL);		// psmt : preparedStatement 객체
			psmt.setString(1, order.getShipName());	// VALUE ? 에 순서대로 변수 대입
			psmt.setString(2, order.getZipCode());
			psmt.setString(3, order.getCountry());
			psmt.setString(4, order.getAddress());
			psmt.setString(5, order.getDate());
			psmt.setString(6, order.getOrderPw());
			psmt.setString(7, order.getUserId());
			psmt.setInt(8, order.getTotalPrice());
			psmt.setString(9, order.getPhone());
			
			result = psmt.executeUpdate();	// result 변수에 쿼리 실행 결과값 입력
			
			// 0 : 실패
			// 0 > : 성공
			
		} catch (Exception e) {
			System.err.println(table() + " 테이블에 insert 시 에러 발생 ... ");	// 실행중 오류 발생 시 출력문
			e.printStackTrace();											// 전체 에러 구문
		}
		
		return result;	// 결과 값 반환
	}

	/**
	 * 최근 등록한 orderNo 
	 * @return
	 */
	// 마지막주문번호
	// SELECT
	// : 해당 값 보통 int 의 최댓값을 불러온다.
	// 	 보통 SELECT MAX ( 컬럼명 ) as 참조할 컬럼명 FROM 테이블명
	//	 as 로 참조할 컬럼명은 변수명 짓는거와 같다.
	public int lastOrderNo() {									// lastOrderNo의 메소드 선언
		int result = 0;											// int니까 반환할 변수 0으로 선언 - ( 0: 실패값 -> 성공해서 1이상이 들어가면 성공 근데 실패해서 아무것도 대입이 안되면 실패
		String SQL = " SELECT MAX ( order_no ) as max_no FROM " + table()	// 입력하는게 아니고 마지막 주문번호를 조회해야 하니까 SELECT 쓰고 MAX(order_no) 주문번호의 최댓값은 오토 인크리먼트니까 최댓값이겠지
				   ;
		try {								
			stmt = con.createStatement();						// 뭔진 모르겠는데 객체 생성할라면 쓰라니까 씀
			rs = stmt.executeQuery(SQL);						// 이거 사실 execute뭐 써야되는지 모르는데 얘만 써져서 씀			
			result = rs.getInt("max_no");						// 결과값을 반환변수에 대입
			
		} catch (Exception e) {	
			e.printStackTrace();								// 에러나면 에러 문 나오게
		}
		
		return result;	// 반환값
	}

	
	/**
	 * 주문 내역 조회 - 회원
	 * @param userId
	 * @return
	 */
	public List<Product> list(String userId) {
		List<Product> proList = new ArrayList<Product>();	// 조회된 주문 내역을 저장할 리스트 생성
		// 주문 내역 조회 SQL 쿼리 준비
		String SQL = " SELECT o.order_no, p.name, p.unit_price, io.amount "
				   + " FROM " + table() + " JOIN product_io io ON o.order_no = io.order_no "
				   + " JOIN product p ON io.product_id = p.product_id "
				   + " WHERE o.user_id = ? "	// 특정 회원 id 값이 ?
				   ;
		try {
			 // SQL 쿼리를 PreparedStatement 객체에 할당
			psmt = con.prepareStatement(SQL);
			psmt.setString(1, userId);
			
            // 쿼리 실행 및 결과 ResultSet에 저장
			rs = psmt.executeQuery();
			
			// ResultSet에서 데이터를 읽어와서 Product 객체에 저장
			while ( rs.next() ) {
				Product product = new Product();
				
				product.setOrderNo(rs.getInt("order_no"));
                product.setName(rs.getString("product_name"));
                product.setUnitPrice(rs.getInt("unit_price"));
                product.setAmount(rs.getInt("amount"));
                // Product 객체를 리스트에 추가
                proList.add(product);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 주문 내역이 담긴 리스트 반환
		return proList;
	}
	
	/**
	 * 주문 내역 조회 - 비회원
	 * @param phone
	 * @param orderPw
	 * @return
	 */
	public List<Product> list(String phone, String orderPw) {
		List<Product> nproList = new ArrayList<Product>();
		String SQL = " SELECT o.order_no, p.name, p.unit_price, io.amount "
				   + " FROM " + table() + " JOIN product_io io ON o.order_no = io.order_no "
				   + " JOIN product p ON io.product_id = p.product_id "
				   + " WHERE phone = ? AND order_pw = ? "
				   ;
		try {
			psmt = con.prepareStatement(SQL);
			psmt.setString(1, phone);
			psmt.setString(2, orderPw);
			
			rs = psmt.executeQuery();
			
			while ( rs.next() ) {
				Product product = new Product();
				
				product.setOrderNo(rs.getInt("order_no"));
                product.setName(rs.getString("product_name"));
                product.setUnitPrice(rs.getInt("unit_price"));
                product.setAmount(rs.getInt("amount"));
                nproList.add(product);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nproList;
	}
	
	/**
	 * 주문 수정
	 * @param orderNo
	 * @return
	 */
	public int update(Order order) {
		int result = 0;
		String SQL = " UPDATE " + table() + " SET "
				   + " ship_name "
				   + " ,zip_code "
				   + " ,country "
				   + " ,address "
				   + " ,date "
				   + " ,order_pw "
				   + " ,user_id "
				   + " ,total_price "
				   + " ,phone "
				   + " WHERE order_no = ? "
				   ;
		
		try {
			psmt = con.prepareStatement(SQL);		// psmt : preparedStatement 객체
			psmt.setString(1, order.getShipName());	// VALUE ? 에 순서대로 변수 대입
			psmt.setString(2, order.getZipCode());
			psmt.setString(3, order.getCountry());
			psmt.setString(4, order.getAddress());
			psmt.setString(5, order.getDate());
			psmt.setString(6, order.getOrderPw());
			psmt.setString(7, order.getUserId());
			psmt.setInt(8, order.getTotalPrice());
			psmt.setString(9, order.getPhone());	
			
			result = psmt.executeUpdate();
			
		} catch (Exception e) {
			System.err.println("게시글 수정 시 예외 발생");
			e.printStackTrace();
		}
				
		return result;
	}
	
	/**
	 * 주문 삭제
	 * @param orderNo
	 * @return
	 */
	public int delete(int orderNo) {
		int result = 0;
		String SQL = " DELETE FROM " + table()
				   + " WHERE order_no = ? "
				   ;
		try {
			psmt = con.prepareStatement(SQL);
			psmt.setInt(1, orderNo);
			
			result = psmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
