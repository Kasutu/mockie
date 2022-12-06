package com.splitscale.mockie.user;

import java.util.Map;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/user")
public class UserController {

  @ResponseBody
  @PostMapping(path = "/create")
  public ResponseEntity<User> createUser(@RequestBody Map<String, String> body) {

    User user = new User();

    long low = 0;
    long high = 1000;
    Random r = new Random();
    long result = r.nextLong(high - low) + low;

    user.setId(result);
    user.setUsername(body.get("username"));
    user.setPassword(body.get("password"));

    return new ResponseEntity<User>(user, HttpStatus.OK);
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
