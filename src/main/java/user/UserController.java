package user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import user.UserService;
import user.UserModel;

@RestController
//@RequestMapping("/")
public class UserController {
	
	@Autowired
	
	UserService userService;
	@RequestMapping("/")
	public String home() {
		return "OK";
	}
	@RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<List<UserModel>> getAllUser() {
		List<UserModel> listUser= userService.findAll();
		if(listUser.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<UserModel>>(listUser, HttpStatus.OK);
           	
    }
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public UserModel findUser(@PathVariable("id") Integer id) {
		UserModel user = userService.getOne(id);
		if(user == null) {
			ResponseEntity.notFound().build();
		}
		return user;
	}
	
	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public UserModel saveUser(@Valid @RequestBody UserModel user) {
		return userService.save(user);
	}
	
	@RequestMapping(value = "/user/", method = RequestMethod.PUT)
	public ResponseEntity<UserModel> updateUser(@PathVariable(value = "id") Integer userId, 
	                                       @Valid @RequestBody UserModel userForm) {
		UserModel user = userService.getOne(userId);
	    if(user == null) {
	        return ResponseEntity.notFound().build();
	    }
	    user.setName(userForm.getName());
	    user.setAddress(userForm.getAddress());
	    user.setGender(userForm.getGender());

	    UserModel updatedUser = userService.save(user);
	    return ResponseEntity.ok(updatedUser);
	}
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<UserModel> deleteUser(@PathVariable(value = "id") Integer id) {
		UserModel user = userService.getOne(id);
	    if(user == null) {
	        return ResponseEntity.notFound().build();
	    }

	    userService.delete(user);
	    return ResponseEntity.ok().build();
	}
}