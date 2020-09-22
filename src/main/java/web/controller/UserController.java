package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String user(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("roles", user.printSet(user.getRoles()));
        return "myPage";
    }

    @GetMapping("/registration")
    public String pageNewUser(){
        return "regPage";
    }

    @PostMapping("/registration")
    public String addNewUser(@ModelAttribute("user") User userForm) {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(userService.getRoleById(1L));
        userForm.setRoles(roleSet);
        userService.saveUser(new User(userForm.getFirstName(),userForm.getLastName(),userForm.getAge(),
                userForm.getEmail(), userForm.getPassword(), userForm.getRoles()));
        return "redirect:/login";
    }
}
