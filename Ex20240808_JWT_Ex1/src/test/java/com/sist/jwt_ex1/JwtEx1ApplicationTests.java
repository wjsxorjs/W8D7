package com.sist.jwt_ex1;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Base64;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.sist.jwt_ex1.jwt.JwtProvider;

import io.jsonwebtoken.security.Keys;

@SpringBootTest
class JwtEx1ApplicationTests {
	@Value("${custom.jwt.secretKey}")
	private String secretKey;

	@Autowired
	JwtProvider jwtProvider;

	@Test
	@DisplayName("!!!!!!!!!secretKey!!!!!!!!!")
	void loadSecretKey(){
		assertThat(secretKey).isNotNull(); // "있잖아. 이거 null 아니지?"
	}

	@Test
	@DisplayName("암호화 알고리즘으로 시크릿 키 암호화")
	void GenBase64(){
		String encoding = Base64.getEncoder()
								.encodeToString(secretKey.getBytes());
		
		SecretKey sk = Keys.hmacShaKeyFor(encoding.getBytes());

		assertThat(sk).isNotNull(); // "있잖아. 이거 null 아니지?"
	}

	@Test
	@DisplayName("jwtProvider 기능 테스트 #1")
	void testNo1(){
		SecretKey sk = jwtProvider.getSecretKey();

		assertThat(sk).isNotNull();
	}

	@Test
	@DisplayName("jwtProvider 기능 테스트 #2")
	void testNo2(){
		SecretKey sk1 = jwtProvider.getSecretKey();
		SecretKey sk2 = jwtProvider.getSecretKey();

		assertThat(sk2 == sk1).isTrue(); // 주소값도 비교. 실제 같은 객체인지 비교
		// assertThat(sk2).isEqualTo(sk1); // 이건 value로 값만 비교
	}

}
