package com.splitscale.mockie.response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/res")
public class HeaderResController {
  @ResponseBody
  @RequestMapping(path = "/access-token")
  public ResponseEntity<String> createUser() {

    String sessionID = "hwieg83764giuwef874ty3g5908";
    String cookieValue = "sessionID=" + sessionID + "; Max-Age=604800; Path=/; Secure; HttpOnly";

    MultiValueMap<String, String> headers = new HttpHeaders();
    headers.add("Set-Cookie", cookieValue);

    return new ResponseEntity<String>("access-token", headers, HttpStatus.OK);

  }
}
