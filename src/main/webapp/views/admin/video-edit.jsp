<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<h2>Chỉnh Sửa Video</h2>

<form action="${pageContext.request.contextPath}/admin/video/update" method="post" enctype="multipart/form-data">
    <div>
        <label for="videoId">Video ID:</label>
        <input type="text" id="videoId" name="videoId" value="${video.videoId}" readonly />
    </div>

    <div>
        <label for="title">Video Title:</label>
        <input type="text" id="title" name="title" value="${video.title}" />
    </div>

    <div>
        <label for="views">View Count:</label>
        <input type="number" id="views" name="views" value="${video.views}" />
    </div>

    <div>
        <label for="category">Category:</label>
        <select name="categoryId">
            <c:forEach var="category" items="${listCategory}">
                <option value="${category.categoryId}" ${category.categoryId == video.category.categoryId ? 'selected' : ''}>
                    ${category.categoryname}
                </option>
            </c:forEach>
        </select>
    </div>

    <div>
        <label for="description">Description:</label>
        <textarea id="description" name="description">${video.description}</textarea>
    </div>

    <div>
        <label>Status:</label>
        <input type="radio" name="active" value="1" ${video.active == 1 ? 'checked' : ''} /> Hoạt động
        <input type="radio" name="active" value="0" ${video.active == 0 ? 'checked' : ''} /> Khóa
    </div>

    <div>
        <label>Upload Poster:</label>
        <input type="file" name="poster" />
        <br/>
        <img src="${pageContext.request.contextPath}/upload/${video.poster}" height="150" alt="Poster Image" />
    </div>

    <div>
        <button type="submit">Update</button>
    </div>
</form>

<a href="${pageContext.request.contextPath}/admin/videos">Quay lại danh sách video</a>
