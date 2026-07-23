package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


    @GetMapping(value = "/admin")
    public String adminPanel(ModelMap model) {
        model.addAttribute("users", userService.getAllUser());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("newUser", new User());
        model.addAttribute("profileUser", userService.getCurrentUserFromContext());
        model.addAttribute("editUser", new User());
        return "admin";
    }

    @PostMapping(value = "/save")
    public String saveUser(@ModelAttribute("newUser") User user,
                           @RequestParam(value = "roleIds", required = false) List<Long> roleIds) {
        userService.saveUserWithRolesAndPassword(user, roleIds);
        return "redirect:/admin";
    }

    @PostMapping(value = "/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "/edit")
    public String editUser(@RequestParam("id") Long id,
                           ModelMap model) {
        User user = userService.getUser(id);
        user.setPassword(null);
        model.addAttribute("editUser", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "edit";
    }
}