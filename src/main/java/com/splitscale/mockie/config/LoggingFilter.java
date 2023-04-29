package com.splitscale.mockie.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.splitscale.Loglemon;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

@Component
public class LoggingFilter implements Filter {

  private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {

    try {
      HttpServletRequest httpRequest = (HttpServletRequest) request;

      ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(httpRequest);
      String requestPayload = getContentAsString(requestWrapper.getContentAsByteArray(),
          requestWrapper.getCharacterEncoding());

      class CustomResponseWrapper extends HttpServletResponseWrapper {
        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        private final ServletOutputStream outputStream = new ServletOutputStream() {
          @Override
          public void write(int b) throws IOException {
            baos.write(b);
          }

          @Override
          public boolean isReady() {
            throw new UnsupportedOperationException("Unimplemented method 'isReady'");
          }

          @Override
          public void setWriteListener(WriteListener arg0) {
            throw new UnsupportedOperationException("Unimplemented method 'setWriteListener'");
          }
        };

        public CustomResponseWrapper(HttpServletResponse response) {
          super(response);
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
          return outputStream;
        }

        @Override
        public PrintWriter getWriter() throws IOException {
          return new PrintWriter(baos);
        }

        @Override
        public void flushBuffer() throws IOException {
          super.flushBuffer();
          baos.flush();
        }

        public byte[] getResponseAsByteArray() {
          return baos.toByteArray();
        }
      }

      CustomResponseWrapper responseWrapper = new CustomResponseWrapper((HttpServletResponse) response);
      chain.doFilter(requestWrapper, responseWrapper);

      String responsePayload = getContentAsString(responseWrapper.getResponseAsByteArray(),
          responseWrapper.getCharacterEncoding());

      String logMessage = String.format("%s %s %s | requestPayload=%s | responsePayload=%s",
          httpRequest.getMethod(),
          httpRequest.getRequestURI(),
          responseWrapper.getStatus(),
          requestPayload,
          responsePayload);

      logger.info(logMessage);
      Loglemon.sendLog(logMessage);
    } catch (Exception e) {
      final String message = "[LoggingFilter] [ERROR]: " + e.getMessage();
      logger.error(message);
      Loglemon.sendLog(message);
    }

  }

  private String getContentAsString(byte[] content, String charsetName) throws UnsupportedEncodingException {
    return new String(content, charsetName);
  }

}
