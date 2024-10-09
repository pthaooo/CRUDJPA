<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<h2>Thêm Video</h2>

<form action="${pageContext.request.contextPath}/admin/video/insert" method="post" enctype="multipart/form-data">
    <div>
        <label for="title">Video Title:</label>
        <input type="text" id="title" name="title" />
    </div>

    <div>
        <label for="views">View Count:</label>
        <input type="number" id="views" name="views" value="0" />
    </div>

    <div>
        <label for="category">Category:</label>
        <select name="categoryId">
            <c:forEach var="category" items="${listCategory}">
                <option value="${category.categoryId}">
                    ${category.categoryname}
                </option>
            </c:forEach>
        </select>
    </div>

    <div>
        <label for="description">Description:</label>
        <textarea id="description" name="description"></textarea>
    </div>

    <div>
        <label>Status:</label>
        <input type="radio" name="active" value="1"  checked/> Hoạt động
        <input type="radio" name="active" value="0" /> Khóa
    </div>

    <div>
        <label>Upload Poster:</label>
        <input type="file" name="poster" />
    </div>

    <div>
        <button type="submit">Create</button>
        <button type="reset">Reset</button>
    </div>
</form>

<a href="${pageContext.request.contextPath}/admin/videos">Quay lại danh sách video</a>
