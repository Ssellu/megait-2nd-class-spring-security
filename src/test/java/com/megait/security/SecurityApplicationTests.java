package com.megait.security;

import com.megait.security.domain.Account;
import com.megait.security.domain.Role;
import com.megait.security.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.annotation.PostConstruct;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
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







	@PostConstruct
	@DisplayName("테스트유저 생성 - 테스트 전에 무조건 실행")
	private void createTestUser(){
		Account account = Account.builder()
				.username("test01")
				.password("1234")
				.role(Role.USER)
				.build();
		accountService.createNewAccount(account);
	}

	@Test
	@DisplayName("로그인 성공 - authenticated")
	public void login_success() throws Exception {
		mockMvc.perform(formLogin().user("test01").password("1234"))
				.andExpect(authenticated());
		// perform() : 수행하라
		// andExpect() : 수행 결과를 XXX로 기대하라
	}

	@Test
	@DisplayName("로그인 실패 - unauthenticated")
	public void login_failed() throws Exception {
		// given - 초기값
		String username = "wrong_user";
		String password = "wrong_password";

		// when - 상황
		ResultActions actions = mockMvc.perform(formLogin().user(username).password(password));

		// then - 결과
		actions.andExpect(unauthenticated());

	}

	@Test
	@WithAnonymousUser
	@DisplayName("익명 사용자로 admin 페이지 요청 - unauthorized")
	public void admin_page_with_anonymous_user() throws Exception{
		mockMvc.perform(get("/admin"))
				.andDo(print())  // responseBody 출력
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(roles={"ADMIN"})
	@DisplayName("ADMIN 사용자로 admin 페이지 요청 - OK")
	public void admin_page_with_admin_user() throws Exception{
		mockMvc.perform(get("/admin"))
				.andDo(print())  // responseBody 출력
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(roles={"USER"})
	@DisplayName("일반 사용자로 admin 페이지 요청 - Forbidden")
	public void admin_page_with_user() throws Exception{
		mockMvc.perform(get("/admin"))
				.andDo(print())  // responseBody 출력
				.andExpect(status().isForbidden());
	}



	@Test
	@WithAnonymousUser
	@DisplayName("익명 사용자로 index 페이지 요청 (OK)")
	public void index_page_with_anonymous_user() throws Exception{
		mockMvc.perform(get("/"))
				.andDo(print())  // responseBody 출력
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "test01", password = "1234")
	@DisplayName("인증된 사용자로 index 페이지 요청 (OK)")
	public void index_page_with_user() throws Exception{
		mockMvc.perform(get("/"))
				.andDo(print())  // responseBody 출력
				.andExpect(status().isOk());
	}

}
