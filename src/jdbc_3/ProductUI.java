package jdbc_3;

import java.sql.SQLException;
import java.util.List;

public class ProductUI {

	private ProductDao productDao = new ProductDao();
	private Scanner scanner = new Scanner(System.in);
	
	public void showMenu() {
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("1.전체조회 2.상세조회 3.검색(가격) 4.신규등록 5.삭제 6.가격수정 7.재고수정 0.종료");
		System.out.println("----------------------------------------------------------------------------------");
		
		System.out.print("### 메뉴번호: ");
		int menuNo = scanner.nextInt();
		
		try {
			switch(menuNo)  {
				case 1: 전체조회(); break;
				case 2: 상세조회(); break;
				case 3: 검색(); break;
				case 4: 신규등록(); break;
				case 5: 삭제(); break;
				case 6: 가격수정(); break;
				case 7: 재고수정(); break;
				case 0: 종료(); break;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		System.out.println();
		System.out.println();
		System.out.println();
		
		showMenu();
	}
	
	private void 전체조회() throws SQLException {
		System.out.println("<< 전체 상품 조회 >>");
		
		List<Product> products = productDao.getAllProducts();
		
		if (products.isEmpty()) {
			System.out.println("### 상품 정보가 존재하지 않습니다.");
			return;
		}
		
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("번호\t이름\t제조사\t가격\t할인가격\t재고\t품절여부\t제조날짜\t변경날짜");
		System.out.println("----------------------------------------------------------------------------------");
		for (Product product : products) {
			System.out.print(product.getNo() + "\t");
			System.out.print(product.getName() + "\t");
			System.out.print(product.getMaker() + "\t");
			System.out.print(product.getPrice() + "\t");
			System.out.print(product.getDiscountPrice() + "\t");
			System.out.print(product.getStock() + "\t");
			System.out.print(product.getSoldOut() + "\t");
			System.out.print(product.getCreatedDate() + "\t");
			System.out.println(product.getUpdatedDate());
		}
		System.out.println("----------------------------------------------------------------------------------");
	}
	
	private void 상세조회() throws SQLException {
		System.out.println("<< 상품 상세 조회 >>");
		System.out.println("상품번호를 입력해서 상품 상세정보를 확인하세요");
		System.out.print("### 상품번호: ");
		int productNo = scanner.nextInt();
		
		Product product = productDao.getProductByNo(productNo);
		if (product == null) {
			System.out.println("### 상품번호: ["+productNo+"] 상품정보가 존재하지 않습니다.");
			return;
		}
		
		System.out.println("### 상품 상세 정보 ");
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("번호: "+ product.getNo());
		System.out.println("이름: "+ product.getName());
		System.out.println("제조사: "+ product.getMaker());
		System.out.println("가격: "+ product.getPrice());
		System.out.println("할인가격: "+ product.getDiscountPrice());
		System.out.println("재고: "+ product.getStock());
		System.out.println("품절여부: "+ product.getSoldOut());
		System.out.println("제조날짜: "+ product.getCreatedDate());
		System.out.println("변경날짜: "+ product.getUpdatedDate());
		System.out.println("----------------------------------------------------------------------------------");
	}
	
	private void 검색() throws SQLException {
		System.out.println("<< 상품 상세 검색 >>");
		System.out.println("최저가와 최고가를 입력해서 원하는 가격범위의 상품을 검색하세요");
		System.out.print("최저가: ");
		int minPrice = scanner.nextInt();
		System.out.print("최고가: ");
		int maxPrice = scanner.nextInt();
		
		List<Product> products = productDao.getProductsByPricerange(minPrice, maxPrice);
		if (products.isEmpty()) {
			System.out.println("입력한 가격범위에 해당하는 상품정보가 존재하지 않습니다.");
			return;
		}
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("번호\t이름\t제조사\t가격\t할인가격\t재고\t품절여부\t제조날짜\t변경날짜");
		System.out.println("----------------------------------------------------------------------------------");
		for (Product product : products) {
			System.out.print(product.getNo() + "\t");
			System.out.print(product.getName() + "\t");
			System.out.print(product.getMaker() + "\t");
			System.out.print(product.getPrice() + "\t");
			System.out.print(product.getDiscountPrice() + "\t");
			System.out.print(product.getStock() + "\t");
			System.out.print(product.getSoldOut() + "\t");
			System.out.print(product.getCreatedDate() + "\t");
			System.out.println(product.getUpdatedDate());
		}
		System.out.println("----------------------------------------------------------------------------------");
	}
	
	private void 신규등록() throws SQLException {
		System.out.println("<< 신규 상품 등록 >>");
		System.out.println("신규 상품 정보를 등록하세요");
		
		System.out.print("### 번호: ");
		int no = scanner.nextInt();
		System.out.print("### 이름: ");
		String name = scanner.nextString();
		System.out.print("### 제조사: ");
		String maker = scanner.nextString();
		System.out.print("### 가격: ");
		int price = scanner.nextInt();
		System.out.print("### 할인가격: ");
		int discountPrice = scanner.nextInt();
		System.out.print("### 재고: ");
		int stock = scanner.nextInt();
		
		Product product = new Product();
		product.setNo(no);
		product.setName(name);
		product.setMaker(maker);
		product.setPrice(price);
		product.setDiscountPrice(discountPrice);
		product.setStock(stock);
		
		productDao.insertProduct(product);
		System.out.println("신규 상품을 등록하였습니다.");
	}
	
	private void 삭제() throws SQLException {
		System.out.println("<< 상품 정보 삭제 >>");
		System.out.println("### 상품번호를 입력받아서 상품정보를 삭제합니다.");
		
		System.out.print("상품번호 입력 :");
		int productNo = scanner.nextInt();
		
		productDao.deleteProduct(productNo);
		System.out.println("상품번호: '["+productNo+"] 상품정보가 삭제되었습니다.");
	}
	
	private void 가격수정() throws SQLException {
		System.out.println("<< 상품 가격 변경 >>");
		System.out.println("상품번호, 가격, 할인가격을 입력받아서 상품가격과 할인가격을 변경합니다.");
		
		System.out.print("상품번호 입력: ");
		int productNo = scanner.nextInt();
		System.out.print("가격 입력: ");
		int productPrice = scanner.nextInt();
		System.out.print("할인가격 입력: ");
		int productDiscountPrice = scanner.nextInt();
		
		productDao.updateProductPrice(productNo, productPrice, productDiscountPrice);
		System.out.println("상품번호: '["+productNo+"] 가격 정보가 변경되었습니다.");
	}
	
	private void 재고수정() throws SQLException {
		System.out.println("<<상품 재고 변경>>");
		System.out.println("상품번호와 변경수량을 입력받아서 상품재고를 변경합니다.");
		
		System.out.print("상품번호 입력: ");
		int productNo = scanner.nextInt();
		System.out.print("변경수량 입력: ");
		int productStock = scanner.nextInt();
		
		productDao.updateProductStock(productNo, productStock);
		System.out.println("상품번호: '["+productNo+"] 재고 정보가 변경되었습니다.");
		
		System.out.println();
	}
	
	private void 종료() {
		System.out.println("### 프로그램을 종료합니다.");
	}
	
	public static void main(String[] args) {
		new ProductUI().showMenu();
	}
	
}
