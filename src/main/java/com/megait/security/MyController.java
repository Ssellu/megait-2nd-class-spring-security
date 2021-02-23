package com.megait.security;

import com.megait.security.domain.Account;
import com.megait.security.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@Slf4j
public class MyController {

    // 아무나 접근 가능
    @GetMapping("/")
    public String index(Model model, Principal principal){
        if(principal == null)
            model.addAttribute("message", "안녕하세요!");
        else
            model.addAttribute("message", "안녕하세요!" + principal.getName() + "님");
        return "index";
    }

    // 인증된 사용자만 접근 가능(=로그인을 한 사용자만 접근 가능)
    @GetMapping("/dashboard")
    public String dashboard(Model model){
        model.addAttribute("message", "안녕하세요!");
        return "dashboard";
    }

    // 모두 접근 가능
    @GetMapping("/info")
    public String info(Model model){
        model.addAttribute("message", "안녕하세요!");
        return "info";
    }

    // 관리자만 접근 가능
    @GetMapping("/admin")
    public String admin(Model model){
        model.addAttribute("message", "안녕하세요!");
        return "admin";
    }

}
