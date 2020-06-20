package com.project.cars.config;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.cars.dto.ApiErrorDTO;
import com.project.cars.errors.ServiceException;

public class JwtTokenFilter extends GenericFilterBean {

	private final JwtTokenProvider jwtTokenProvider;

	public JwtTokenFilter(final JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public static void populateResponse(final HttpServletRequest req, final ResponseEntity<Object> responseEntity,
			final HttpServletResponse servletResponse) throws IOException {
		final Enumeration<String> headerNames = req.getHeaderNames();

		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				final String headerName = headerNames.nextElement();
				final String header = req.getHeader(headerName);
				servletResponse.addHeader(headerName, header);
				servletResponse.setContentType("application/json");
			}
		}

		servletResponse.setStatus(responseEntity.getStatusCodeValue());
		final String json = new ObjectMapper().writeValueAsString(responseEntity.getBody());
		servletResponse.getWriter().write(json);
	}

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain filterChain)
			throws IOException, ServletException {

		final String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
		try {
			if (token != null && jwtTokenProvider.validateToken(token)) {
				final Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
				SecurityContextHolder.getContext().setAuthentication(auth);
			} else if (((HttpServletRequest) req).getRequestURI().equals("/me")) {
				final HttpServletResponse response = (HttpServletResponse) res;
				populateResponse((HttpServletRequest) req,
						buildResponseEntity(new ApiErrorDTO("Unauthorized", HttpStatus.UNAUTHORIZED.value())),
						response);
				return;
			}
		} catch (final ServiceException ex) {
			final HttpServletResponse response = (HttpServletResponse) res;
			populateResponse((HttpServletRequest) req,
					buildResponseEntity(new ApiErrorDTO(ex.getMessage(), HttpStatus.UNAUTHORIZED.value())), response);
			return;
		} catch (final Exception ex) {
			final HttpServletResponse response = (HttpServletResponse) res;
			populateResponse((HttpServletRequest) req,
					buildResponseEntity(new ApiErrorDTO("Unauthorized", HttpStatus.UNAUTHORIZED.value())), response);
			return;
		}

		filterChain.doFilter(req, res);
	}

	private ResponseEntity<Object> buildResponseEntity(final ApiErrorDTO apiError) {
		return new ResponseEntity<>(apiError, HttpStatus.resolve(apiError.getErrorCode()));
	}

}
