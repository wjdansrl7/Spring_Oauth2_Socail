package com.demo.auth;

import com.demo.domain.User;
import com.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    /**
     * 로그인한 유저가 DB에 저장되어있는지를 찾는다.
     * 찾으면 앞에서 구현한 Authentication(UserDetail를 구현한 PrincipalDetails)을 반환하여
     * SecurityContextHolder에 저장할 수 있게 해준다.
     * 회원을 찾아주는 로직을 구현하는 메서드
     * 이때, 회원정볼르 Form 로그인이면 UseDetails 타입, OAuth2 로그인이면 OAuth2User 타입 반환
     * 1. Form 로그인이면 UserDetailService의 loadUserByUsername 메서드가 실행되고
     * 2. OAuth2 로그인이면 OAuth2UserService의 loadUserByUsername 메서드가 실행된다.
     * 3. loadUserByUsername메서드는 "이런 정보가 들어왔는데 얘 혹시 회원이야?" 라고 묻는 메서드이다.
     * = loadUserByUsername에서는 회원을 찾아주는 로직을 구현하면된다.
     * 4. 이때, 회원정보는 Form 로그인이면 UserDetails타입으로, OAuth2 로그인이면 OAuth2User타입으로 반환해준다.
     * 5. UserDetails 또는 OAuth2User를 반환하면 Spring에서 알아서 Session에 저장해준다.
     * 6. Spring Security의 in-memory 세션 저장소인 SecurityContextHolder에 인증객체를 저장한다.
     * 7. SecurityContextHolder에 들어갈 수 있는 인증객체는 Authentication타입 1가지이다.
     * 8. Authentication은 AbstractAuthenticationToken으로 구현되어있고, AbstractAuthenticationToken을
     * UsernamePasswordAuthenticationToken과 OAuth2LoginAuthenticationToken이 구현하고있다.
     * 9. 우리가 5번에서 UserDetails, OAuth2User를 반환하면 Spring이 알아서
     * UserDetails는 UsernamePasswordAuthenticationToken으로,
     * OAuth2User는 OAuth2LoginAuthenticationToken으로 변환하고
     * 10. UsernamePasswordAuthenticationToken과 OAuth2LoginAuthenticationToken은
     * Authentication의 자식이니 SecurityContextHolder에 저장할 수 있게된다.
     */

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User byUsername = userRepository.findByUsername(username);

        if (byUsername != null) {
            return new PrincipalDetails(byUsername);
        }
        return null;
    }
}



























