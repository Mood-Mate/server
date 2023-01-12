package com.pado.socialdiary.api.common.config.security;

import com.pado.socialdiary.api.member.entity.Member;
import com.pado.socialdiary.api.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member findMember = memberMapper.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Not Found Entity"));

        return new UserDetailsImpl(findMember);
    }
}
