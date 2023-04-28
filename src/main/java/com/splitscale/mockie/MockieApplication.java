package com.splitscale.mockie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.splitscale.mockie.config.RequestLoggingFilterConfig;

@SpringBootApplication
@Import(RequestLoggingFilterConfig.class)
public class MockieApplication {

  public static void main(String[] args) {
    SpringApplication.run(MockieApplication.class, args);
  }

}
