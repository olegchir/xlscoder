package com.xlscoder.controller;

import com.xlscoder.coder.KeyGen;
import com.xlscoder.coder.KeyPairHolder;
import com.xlscoder.model.Key;
import com.xlscoder.repository.KeyRepository;
import com.xlscoder.repository.UserRepository;
import com.xlscoder.security.KeyValidator;
import org.bouncycastle.openpgp.PGPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@Controller
public class KeyFrontController {

    @Autowired
    KeyRepository keyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private KeyValidator keyValdator;

    @RequestMapping(path = "/keys/add", method = RequestMethod.GET)
    public String createKey(Model model) throws NoSuchAlgorithmException, NoSuchProviderException, PGPException, IOException {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("allUsers", userRepository.findAll());
        Key key = new Key();
        KeyPairHolder keyPairHolder = KeyGen.generatePairs();
        key.setPrivateKey(keyPairHolder.getPrivateKey());
        key.setPublicKey(keyPairHolder.getPublicKey());
        key.setPgpPrivateKey(keyPairHolder.getPgpPrivateKey());
        key.setPgpPublicKey(keyPairHolder.getPgpPublicKey());
        key.setShaSalt(keyPairHolder.getShaSalt());
        model.addAttribute("key", key);
        return "keys/update";
    }

    @RequestMapping(path = "/keys/add", method = RequestMethod.POST)
    @Transactional
    public String saveKey(@ModelAttribute("key") Key key, BindingResult bindingResult, Model model) {
        keyValdator.validate(key, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userRepository.findAll());
            return "keys/update";
        }

        keyRepository.save(key);

        return "redirect:/keys";
    }

    @RequestMapping(path = "/keys", method = RequestMethod.GET)
    public String getAllKeys(Model model) {
        model.addAttribute("keys", keyRepository.findAll());
        return "keys/list";
    }

    @RequestMapping(path = "/keys/edit/{id}", method = RequestMethod.GET)
    public String editKey(Model model, @PathVariable(value = "id") Long id) {
        model.addAttribute("allUsers", userRepository.findAll());
        model.addAttribute("key", keyRepository.findOne(id));
        return "keys/update";
    }

    @RequestMapping(path = "/keys/delete/{id}", method = RequestMethod.GET)
    public String deleteKey(@PathVariable(name = "id") Long id) {
        keyRepository.delete(id);
        return "redirect:/keys";
    }
}
