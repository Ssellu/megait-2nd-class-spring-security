package com.megait.security;

import com.megait.security.domain.Account;
import com.megait.security.domain.Role;
import com.megait.security.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityApplicationTests {

	@Autowired
	private AccountService accountService;

	@Autowired
	private MockMvc mockMvc;


	// 익명사용자로 index 페이지 요청 테스트 - OK
	// 인증된 사용자로 index 페이지 요청 테스트 - OK
	// 익명사용자로 admin 페이지 요청 테스트 - X
	// ADMIN 사용자로 admin 페이지 요청 테스트 - OK

	// 로그인 성공 시 -> authenticated 상태인지 확인
	// 로그인 실패 시 -> unauthenticated 상태인지 확인

	@PostConstruct
	private void createTestUser(){
		Account account = Account.builder()
				.username("test01")
				.password("1234")
				.role(Role.USER)
				.build();
		accountService.createNewAccount(account);
	}

	@Test
	public void login_success() throws Exception {
		mockMvc.perform(formLogin().user("test01").password("1234"))
				.andExpect(authenticated());
		// perform() : 수행하라
		// andExpect() : 수행 결과를 XXX로 기대하라
	}

	// TODO 로그인 실패 테스트 구현
	@Test
	public void login_failed() throws Exception {
		mockMvc.perform(formLogin().user("test01").password("1234"))
				.andExpect(authenticated());
	}

	@Test
	@WithAnonymousUser
	public void admin_page_with_anonymous_user() throws Exception{
		mockMvc.perform(get("/admin"))
				.andDo(print())  // responseBody 출력
				.andExpect(status().isUnauthorized());
	}

	// TODO 익명사용자로 index 페이지 요청 테스트 - OK

	@Test
	void contextLoads() {
	}

}
