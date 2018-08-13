package be.unamur.laboil.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Joachim Lebrun on 12-08-18
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {

        Authentication auth
                = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            logger.info("User : '{}' attempted to access the protected URL: '{}'",
                    auth.getName(), httpServletRequest.getRequestURI());
        } else {
            logger.info("Un auth user attempted to access the protected URL: '{}'", httpServletRequest.getRequestURI());
        }

        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login");

    }
}
