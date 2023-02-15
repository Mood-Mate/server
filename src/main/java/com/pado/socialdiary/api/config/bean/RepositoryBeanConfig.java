package com.pado.socialdiary.api.config.bean;

import com.pado.socialdiary.api.moduel.member.mapper.MemberMapper;
import com.pado.socialdiary.api.moduel.member.mapper.MemberMybatisRepository;
import com.pado.socialdiary.api.moduel.member.mapper.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RepositoryBeanConfig {

    private final MemberMapper memberMapper;

    @Bean
    public MemberRepository memberRepository() {
        return new MemberMybatisRepository(memberMapper);
    }
}
