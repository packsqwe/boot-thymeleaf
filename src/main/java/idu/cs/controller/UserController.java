package idu.cs.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import idu.cs.domain.User;
import idu.cs.exception.ResourceNotFoundException;
import idu.cs.repository.UserRepository;

@Controller
public class UserController {
   @Autowired UserRepository userRepo; // Dependency Injection
   
   @GetMapping("/")
   public String home(Model model) {
      return "index";
   }
   
   @GetMapping("/login")
   public String getLogin(Model model) {
      model.addAttribute("users", userRepo.findAll());
      return "login";
   }
   
   @GetMapping("/user-reg-form")
   public String getRegForm(Model model) {
      return "form";
   }
   
   @GetMapping("/users")
   public String getAllUser(Model model) {
      model.addAttribute("users", userRepo.findAll());
      return "userlist";
   }
   
   @PostMapping("/users")
   public String createUser(@Valid @RequestBody User user, Model model) {
      userRepo.save(user);
      model.addAttribute("users", userRepo.findAll());
      return "redirect:/users";
   }
   
   @GetMapping("/users/{id}")
   public String getUserById(@PathVariable(value = "id") Long userId, Model model)
         throws ResourceNotFoundException {
      User user = userRepo.findById(userId).get(); //.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
      //model.addAttribute("id", user.getId()); // 이 값을 user.html로 넘겨주는것. 이름을 꼭 아이디값으로 하지 않아도 됨
      //model.addAttribute("name", user.getName());
      //model.addAttribute("company", user.getCompany());
      model.addAttribute("user", user); // user를 통채로 가져오기
      return "user";
      //return ResponseEntity.ok().body(user);
   }
   
   @PutMapping("/users/{id}") // @PatchMapping
   public String updateUser(@PathVariable(value = "id") Long userId, @Valid User userDetails, Model model) {
      User user = userRepo.findById(userId).get(); // user는 DB로 부터 읽어온 객체 
      user.setName(userDetails.getName()); // userDetails가 전송한 객체
      user.setCompany(userDetails.getCompany());
      userRepo.save(user); // 저장!
      return "redirect:/users";
   }
   
   @DeleteMapping("/users/{id}")
   public String deleteUser(@PathVariable(value = "id") Long userId, Model model) {
      User user = userRepo.findById(userId).get();
      userRepo.delete(user);
      model.addAttribute("name", user.getName());
      return "user_delete";
   }
   
	@GetMapping("/users/fn")
	public String getUserByName(@Param(value="name") String name, Model model)
			throws ResourceNotFoundException {
		List<User> user = userRepo.findByName(name);
		model.addAttribute("user", user);
		return "userlist";
		//return ResponseEntity.ok().body(user);
	}
	
	
}