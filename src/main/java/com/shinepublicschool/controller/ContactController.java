package com.shinepublicschool.controller;

import com.shinepublicschool.model.Contact;
import com.shinepublicschool.service.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
public class ContactController {

    //    private static Logger logger= LoggerFactory.getLogger(ContactController.class);
    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    //Used to display contact page
    @RequestMapping("/contact")
    public String displayContactPage(Model model) {
        model.addAttribute("contact", new Contact());
        return "contact";
    }

//    @RequestMapping(value = "/saveMsg", method = RequestMethod.POST)
//    public ModelAndView saveMessage(@RequestParam String name, @RequestParam String mobileNum, @RequestParam String email, @RequestParam String subject, @RequestParam String message){
//        logger.info("Name : "+name);
//        logger.info("Mobile No : "+mobileNum);
//        logger.info("Email : "+email);
//        logger.info("Subject : "+subject);
//        logger.info("Message : "+message);
//        return new ModelAndView("redirect:/contact");
//    }

    //Used to save contact details
    @PostMapping(value = "/saveMsg")
    public String saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors errors) {
        if (errors.hasErrors()) {
            log.error("contact form validation failed due to :" + errors.toString());
            return "contact";
        }
        contactService.saveMessageDetails(contact);
        return ("redirect:/contact");
    }

    @RequestMapping("/displayMessages")
    public ModelAndView displayMessages(Model model) {
        List<Contact> contactMsgs = contactService.findMsgsWithOpenStatus();
        for (Contact c : contactMsgs) {
            System.out.println(c);
        }
        ModelAndView modelAndView = new ModelAndView("messages");
        modelAndView.addObject("contactMsgs", contactMsgs);
        return modelAndView;
    }

    @RequestMapping(value = "/closeMsg", method = RequestMethod.GET)
    public String closeMsg(@RequestParam int id, Authentication authentication) {
        contactService.updateMsgStatus(id, authentication.getName());
        return "redirect:/displayMessages";
    }
}
