package com.megait.security.service;

import com.megait.security.domain.Account;
import com.megait.security.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {
    // 로그인, 회원가입 담당

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    public Account createNewAccount(Account account){
        //account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.encodePassword(passwordEncoder);
        return accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(s);
        return User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .roles(account.getRole().getAuthority())
                .build();
    }
}
