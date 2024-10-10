package vn.iotstar.controller.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Video;
import vn.iotstar.services.ICategoryService;
import vn.iotstar.services.IVideoService;
import vn.iotstar.services.CategoryService;
import vn.iotstar.services.VideoService;
import static vn.iotstar.utils.Constant.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, maxFileSize = 1024 * 1024 * 50, maxRequestSize = 1024 * 1024 * 100)
@WebServlet(urlPatterns = { "/admin/videos", "/admin/video/edit", "/admin/video/update", "/admin/video/insert",
		"/admin/video/add", "/admin/video/delete" })
public class VideoController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	IVideoService videoService = new VideoService();
	ICategoryService categoryService = new CategoryService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String url = req.getRequestURI();

		if (url.contains("videos")) {
			List<Video> list = videoService.findAll();
			req.setAttribute("listvideo", list);
			req.getRequestDispatcher("/views/admin/video-list.jsp").forward(req, resp);
		} else if (url.contains("/admin/video/edit")) {
			int id = Integer.parseInt(req.getParameter("id"));
			Video video = videoService.findById(id);
			req.setAttribute("video", video);

			List<Category> listCategory = categoryService.findAll();
			req.setAttribute("listCategory", listCategory);

			req.getRequestDispatcher("/views/admin/video-edit.jsp").forward(req, resp);
		} else if (url.contains("/admin/video/add")) {
			List<Category> listCategory = categoryService.findAll();
			req.setAttribute("listCategory", listCategory);
			req.getRequestDispatcher("/views/admin/video-add.jsp").forward(req, resp);
		} else if (url.contains("/admin/video/delete")) {
			int id = Integer.parseInt(req.getParameter("id"));
			try {
				videoService.delete(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			resp.sendRedirect(req.getContextPath() + "/admin/videos");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String url = req.getRequestURI();

		if (url.contains("/admin/video/update")) {
			int videoId = Integer.parseInt(req.getParameter("videoId"));
			String title = req.getParameter("title");
			String description = req.getParameter("description");
			int active = Integer.parseInt(req.getParameter("active"));
			int views = Integer.parseInt(req.getParameter("views"));
			int categoryId = Integer.parseInt(req.getParameter("categoryId"));

			Video video = new Video();
			video.setVideoId(videoId);
			video.setTitle(title);
			video.setDescription(description);
			video.setActive(active);
			video.setViews(views);

			Category category = categoryService.findById(categoryId);
			video.setCategory(category);

			// Lưu hình cũ
			Video oldVideo = videoService.findById(videoId);
			String fileold = oldVideo.getPoster();

			// Xử lý hình ảnh
			String fname = processUpload(req, "poster", fileold);

			video.setPoster(fname);

			videoService.update(video);
			resp.sendRedirect(req.getContextPath() + "/admin/videos");

		} else if (url.contains("/admin/video/insert")) {
			String title = req.getParameter("title");
			String description = req.getParameter("description");
			int active = Integer.parseInt(req.getParameter("active"));
			int views = Integer.parseInt(req.getParameter("views"));
			int categoryId = Integer.parseInt(req.getParameter("categoryId"));

			Video video = new Video();
			video.setTitle(title);
			video.setDescription(description);
			video.setActive(active);
			video.setViews(views);

			Category category = categoryService.findById(categoryId);
			video.setCategory(category);

			// Xử lý hình ảnh
			String fname = processUpload(req, "poster", "default.png");

			video.setPoster(fname);

			videoService.insert(video);
			resp.sendRedirect(req.getContextPath() + "/admin/videos");
		}
	}

	// Phương thức xử lý upload hình ảnh
	private String processUpload(HttpServletRequest req, String fieldName, String defaultFileName)
			throws IOException, ServletException {
		String fname = defaultFileName;
		String uploadPath = UPLOAD_DIRECTORY;
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		try {
			Part part = req.getPart(fieldName);
			if (part != null && part.getSize() > 0) {
				String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
				int index = filename.lastIndexOf(".");
				String ext = filename.substring(index + 1);
				fname = System.currentTimeMillis() + "." + ext;
				part.write(uploadPath + "/" + fname);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fname;
	}
}
