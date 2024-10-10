package vn.iotstar.controller.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.utils.Constant;

@WebServlet(urlPatterns = { "/image" })
public class DownloadFileController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getParameter("fname");
		File file = new File(Constant.UPLOAD_DIRECTORY + "/" + fileName);
		// Xác định loại tệp dựa trên phần mở rộng
		String mimeType = getServletContext().getMimeType(file.getName());
		if (mimeType == null) {
			// Nếu không xác định được loại tệp, mặc định là binary stream
			mimeType = "application/octet-stream";
		}
		resp.setContentType(mimeType);
		if (file.exists()) {
			try (FileInputStream fileInputStream = new FileInputStream(file)) {
				IOUtils.copy(fileInputStream, resp.getOutputStream());
				resp.flushBuffer();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found: " + fileName);
		}
	}
}
