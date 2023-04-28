package com.splitscale.mockie.user;

import java.util.Map;
import java.util.Random;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/user")
public class UserController {
  
  @ResponseBody
  @GetMapping(path = "/")
  public ResponseEntity<UserDisplayable> getUser() {

    UserDisplayable user = new UserDisplayable(3423, "stevenBallaret");
    return new ResponseEntity<UserDisplayable>(user, HttpStatus.OK);
  }

  @ResponseBody
  @PostMapping(path = "/create")
  public ResponseEntity<UserDisplayable> createUser(@RequestBody Map<String, String> body) {

    long low = 0;
    long high = 1000;
    Random r = new Random();
    long result = r.nextLong(high - low) + low;

    String username = body.get("username");
    String password = body.get("password");

    if (username == null || password == null) {
      return new ResponseEntity<UserDisplayable>(HttpStatus.BAD_REQUEST);
    }

    UserDisplayable user = new UserDisplayable(result, username);

    String sessionID = "hwieg83764giuwef874ty3g5908";
    String cookieValue = "sessionID=" + sessionID + "; Max-Age=604800; Path=/; Secure; HttpOnly";

    MultiValueMap<String, String> headers = new HttpHeaders();
    headers.add("Set-Cookie", cookieValue);

    return new ResponseEntity<UserDisplayable>(user, headers, HttpStatus.OK);

  }

  @ResponseBody
  @GetMapping(path = "/get")
  public ResponseEntity<UserDisplayable> getUser(@RequestParam Map<String, String> param) {

    UserDisplayable user = new UserDisplayable(3423, "stevenBallaret");

    String username = param.get("username");

    if (username.equals(user.username)) {
      return new ResponseEntity<UserDisplayable>(user, HttpStatus.OK);
    }

    return new ResponseEntity<UserDisplayable>(HttpStatus.BAD_REQUEST);
  }

  @ResponseBody
  @GetMapping
  public ResponseEntity<UserDisplayable> getCurrentUser() {

    UserDisplayable user = new UserDisplayable(3423, "stevenBallaret");

    return new ResponseEntity<UserDisplayable>(user, HttpStatus.OK);
  }

  @ResponseBody
  @PutMapping(path = "/update")
  public ResponseEntity<String> updateUser(@RequestBody Map<String, String> body) {
    String username = body.get("username");
    String password = body.get("password");

    if (username == null || password == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username or password is empty");
    }

    return ResponseEntity.status(HttpStatus.CREATED).body("User update success");
  }

  @ResponseBody
  @DeleteMapping(path = "/delete")
  public ResponseEntity<String> deleteUser(@RequestBody Map<String, String> body) {
    String username = body.get("username");
    String password = body.get("password");

    if (username == null || password == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username or password is empty");
    }

    return ResponseEntity.status(HttpStatus.CREATED).body("User delete success");
  }
}
