<%@ page import="shop.dao.ProductRepository"%>
<%@ page import="shop.dto.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shop</title>
    <jsp:include page="/layout/meta.jsp" />
    <jsp:include page="/layout/link.jsp" />
</head>
<body>
    <jsp:include page="/layout/header.jsp" />
    <%
    	String root = request.getContextPath();
        String productId = request.getParameter("id"); // URL에서 productId 가져오기
        ProductRepository proDAO = new ProductRepository();
        Product product = null;
        
        if (productId != null) {
            product = proDAO.getProductById(productId); // productId로 상품 정보 조회
        }
    %>
    <div class="px-4 py-5 my-5 text-center">
        <h1 class="display-5 fw-bold text-body-emphasis">상품 정보</h1>
        <div class="col-lg-6 mx-auto">
            <p class="lead mb-4">Shop 쇼핑몰 입니다.</p>
            <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                <a href="<%= root %>/shop/products.jsp" class="btn btn-primary btn-lg px-4 gap-3">상품목록</a>
            </div>
        </div>
    </div>
    
    <!-- 상품 정보 영역 -->
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <img src="img?id=<%= product.getProductId() %>" class="w-100 p-2">
            </div>
            <div class="col-md-6">
                <h3 class="mb-5"><%= product.getName() %></h3>
                <table class="table">
                    <colgroup>
                        <col width="120px">
                        <col>
                    </colgroup>
                    <tbody>
                        <tr>
                            <td>상품ID :</td>
                            <td><%= product.getProductId() %></td>
                        </tr>
                        <tr>
                            <td>제조사 :</td>
                            <td><%= product.getManufacturer() %></td>
                        </tr>
                        <tr>
                            <td>분류 :</td>
                            <td><%= product.getCategory() %></td>
                        </tr>
                        <tr>
                            <td>상태 :</td>
                            <td><%= product.getCondition() %></td>
                        </tr>
                        <tr>
                            <td>재고 수 :</td>
                            <td><%= product.getUnitsInStock() %></td>
                        </tr>
                        <tr>
                            <td>가격 :</td>
                            <td>₩ <%= product.getUnitPrice() %></td>
                        </tr>
                    </tbody>
                </table>
                <div class="mt-4">
                    <form name="addForm" action="./addCart.jsp" method="post">
                        <input type="hidden" name="id" value="<%= product.getProductId() %>">
                        <div class="btn-box d-flex justify-content-end">
                            <a href="<%= root %>/shop/cart.jsp" class="btn btn-lg btn-warning mx-3">장바구니</a>
                            <a href="javascript:;" class="btn btn-lg btn-success mx-3" onclick="addToCart()">주문하기</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="/layout/footer.jsp" />
    <jsp:include page="/layout/script.jsp" />
</body>
</html>
