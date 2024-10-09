<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<h2>Danh Sách Video</h2>

<a href="${pageContext.request.contextPath}/admin/video/add">Thêm video</a>

<table border="1">
    <thead>
        <tr>
            <th>STT</th>
            <th>Video ID</th>
            <th>Images</th>
            <th>Video Title</th>
            <th>Description</th>
            <th>Views</th>
            <th>Category</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${listvideo}" var="video" varStatus="status">
            <tr>
                <td>${status.index + 1}</td>
                <td>${video.videoId}</td>
                <td><img src="${pageContext.request.contextPath}/image?fname=${video.poster}" height="100" alt="Poster Image" /></td>
                <td>${video.title}</td>
                <td>${video.description}</td>
                <td>${video.views}</td>
                <td>${video.category.categoryname}</td>
                <td>${video.active == 1 ? 'Hoạt động' : 'Khóa'}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/video/edit?id=${video.videoId}">Edit</a> |
                    <a href="${pageContext.request.contextPath}/admin/video/delete?id=${video.videoId}" 
                       onclick="return confirm('Bạn có chắc chắn muốn xóa?')">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

