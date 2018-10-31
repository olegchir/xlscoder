package com.xlscoder.controller;

import com.xlscoder.dto.FileEncryptionRequest;
import com.xlscoder.model.Key;
import com.xlscoder.model.User;
import com.xlscoder.repository.KeyRepository;
import com.xlscoder.repository.UserRepository;
import com.xlscoder.security.SecurityService;
import com.xlscoder.security.UserService;
import com.xlscoder.xls.ProcessingType;
import com.xlscoder.xls.XLFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

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
    public void encrypt(@ModelAttribute("rq") FileEncryptionRequest rq,
                          HttpServletResponse response,
                          RedirectAttributes redirectAttributes) throws IOException {

        String[] filters = rq.getFilter().split("\\|");
        XLFile.sendAndProcess(rq.getDecrypt() ? ProcessingType.DECRYPT : ProcessingType.ENCRYPT, false,
                rq.getKey(), rq.getFile(),
                response, Arrays.asList(filters));
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
