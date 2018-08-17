package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegistrationPage(
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            Model model) {
        model.addAttribute("user", user); //here
        if (result.hasErrors()) {
            return "registration";
        } else {
            userService.saveUser(user);
            model.addAttribute("message",
                    "User Account Successfully Created");
        }
        return "index";
    }

    @RequestMapping("/")
    public String ListMessages(Model model) {
        model.addAttribute("messages", messageRepository.findAll());
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/update/{id}")
    public String userUpdate(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", messageRepository.findById(id).get());
        return "message";
    }
    @RequestMapping("/delete/{id}")
    public String delMessage(@PathVariable("id") long id){
        messageRepository.deleteById(id);
        return "redirect:/secure";
    }

    @RequestMapping("/secure")
    public String searchByName(Model model, HttpServletRequest request, Authentication authentication, Principal principal) {
        Boolean isAdmin = request.isUserInRole("ADMIN");
        Boolean isUser = request.isUserInRole("USER");
        UserDetails userDetails = (UserDetails)
                authentication.getPrincipal();
        String username = getUser().getUsername();
        model.addAttribute("messages", messageRepository.findByUsername(username));
        return "list";

    }
    @GetMapping("/add")
    public String addTask(Model model){
        model.addAttribute("message", new Message());
        return "message";
    }
    @PostMapping("/processMessage")
    public String processForm(@ModelAttribute Message message, BindingResult result, Model model)
    {
        String username = getUser().getUsername();
        message.setUsername(username);
        messageRepository.save(message);
        model.addAttribute("messages", messageRepository.findByUsername(username));
        return "list";
    }
    @RequestMapping("/messaginglist")
    public String messagelist(HttpServletRequest request, Authentication authentication, Principal principal, Model model) {
        Boolean isAdmin = request.isUserInRole("ADMIN");
        Boolean isUser = request.isUserInRole("USER");
        UserDetails userDetails = (UserDetails)
                authentication.getPrincipal();

        String username = principal.getName();
        model.addAttribute("messages", messageRepository.findByUsername(username));
        return "messaginglist";
    }

    private User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentusername = authentication.getName();
        User user = userRepository.findByUsername(currentusername);
        return user;
    }
}
