package com.splitscale.mockie.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.splitscale.Loglemon;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggingFilter implements Filter {

  private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    String requestPayload = null;
    if ("POST".equalsIgnoreCase(httpRequest.getMethod()) ||
        "PUT".equalsIgnoreCase(httpRequest.getMethod())) {
      requestPayload = getRequestPayload(httpRequest);
    }

    long startTime = System.currentTimeMillis();

    try {
      chain.doFilter(request, response);
    } finally {
      long endTime = System.currentTimeMillis();
      long duration = endTime - startTime;

      String logMessage = String.format("[HTTP %d] %s %s (%d ms) | Request Payload: %s | Response Payload: %s",
          httpResponse.getStatus(),
          httpRequest.getMethod(),
          httpRequest.getRequestURI(),
          duration,
          requestPayload,
          getResponsePayload(httpResponse));

      logger.info(logMessage);
      Loglemon.sendLog(logMessage);
    }
  }

  private String getRequestPayload(HttpServletRequest request) {
    try {
      return StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      logger.error("Error reading request payload", e);
      return null;
    }
  }

  private String getResponsePayload(HttpServletResponse response) {
    try {
      return response.getOutputStream().toString();
    } catch (IOException e) {
      logger.error("Error reading response payload", e);
      return null;
    }
  }

}
