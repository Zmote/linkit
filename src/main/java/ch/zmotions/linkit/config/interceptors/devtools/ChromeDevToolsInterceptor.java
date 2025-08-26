package ch.zmotions.linkit.config.interceptors.devtools;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

public class ChromeDevToolsInterceptor implements HandlerInterceptor {
	private static final String CHROME_DEV_TOOLS_URI = ".well-known/appspecific/com.chrome.devtools.json";
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		if(request.getRequestURI().contains(CHROME_DEV_TOOLS_URI)){
			response.sendRedirect("/");
		}
		return true;
	}
}