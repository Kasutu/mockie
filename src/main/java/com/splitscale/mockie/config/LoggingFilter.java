package com.splitscale.mockie.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggingFilter implements Filter {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    // Log incoming request
    LOGGER.info("Incoming request {} {}", httpRequest.getMethod(), httpRequest.getRequestURI());

    try {
      // Call next filter (if any) or target servlet/controller
      chain.doFilter(request, response);
    } catch (Exception e) {
      // Log any errors
      LOGGER.error("Error processing request: {}", e.getMessage(), e);
      throw e;
    } finally {
      // Log outgoing response
      LOGGER.info("Outgoing response with status {}", httpResponse.getStatus());
    }
  }

}
