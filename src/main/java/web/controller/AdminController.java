package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String blogMain(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("roles", user.printSet(user.getRoles()));

        Iterable<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/delete")
    public String deleteUser(@ModelAttribute("user") User user) {
        userService.deleteUser(user.getId());
        return "redirect:/admin/home";
    }

    @PostMapping("/adduser")
    public String addUserPost(@ModelAttribute("user") User userForm, @RequestParam(name = "role", required = false) String role) {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(userService.getRoleById(1L));
        if ((role != null) && (role.equals("admin"))) {
            roleSet.add(userService.getRoleById(2L));
        }
        userForm.setRoles(roleSet);
        userService.saveUser(new User(userForm.getFirstName(),userForm.getLastName(),userForm.getAge(),
                userForm.getEmail(), userForm.getPassword(), userForm.getRoles()));
        return "redirect:/admin/home";
    }

    @PostMapping("/edit/{id}")
    public String updateUserPost(@ModelAttribute("user") User userForm,
                                 @RequestParam(name = "role", required = false) String role) {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(userService.getRoleById(1L));
            if ((role != null) && (role.equals("admin"))) {
            roleSet.add(userService.getRoleById(2L));
        }
        userForm.setRoles(roleSet);
        userService.editUser(userForm);
        return "redirect:/admin/home";
    }
}
