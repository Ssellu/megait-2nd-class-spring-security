package com.megait.security;

import com.megait.security.domain.Account;
import com.megait.security.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/account/{role}/{username}/{password}")
    public Account createAccount(@ModelAttribute Account account){
        log.info("Account Username: " + account.getUsername());
        Account newAccount =accountService.createNewAccount(account);
        return newAccount;
    }
}
