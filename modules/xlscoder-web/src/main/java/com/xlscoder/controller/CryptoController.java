package com.xlscoder.controller;

import com.xlscoder.dto.FileEncryptionRequest;
import com.xlscoder.model.Key;
import com.xlscoder.model.User;
import com.xlscoder.repository.KeyRepository;
import com.xlscoder.repository.UserRepository;
import com.xlscoder.security.SecurityService;
import com.xlscoder.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CryptoController {
    @Autowired
    KeyRepository keyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(path = "/files/encrypt", method = RequestMethod.POST)
    public String encrypt(@ModelAttribute("rq") FileEncryptionRequest rq,
//            @RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

//        try {
//            // get your file as InputStream
//            InputStream is = ...;
//            // copy it to response's OutputStream
//            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
//            response.flushBuffer();
//        } catch (IOException ex) {
//            log.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
//            throw new RuntimeException("IOError writing file to output stream");
//        }

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded "
//                        + file.getOriginalFilename()
                        + "!");

        return "redirect:/";
    }

    @RequestMapping(path = "/files/encrypt", method = RequestMethod.GET)
    public String encrypt(Model model) {
        User user = userService.findByUsername(securityService.findLoggedInUsername());

        FileEncryptionRequest rq = new FileEncryptionRequest();
        model.addAttribute("rq", rq);
        model.addAttribute("keys", user.getKeys());

        return "files/encrypt";
    }


}
