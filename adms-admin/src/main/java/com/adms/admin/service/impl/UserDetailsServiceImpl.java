package com.adms.admin.service.impl;

import com.adms.admin.entity.JobUser;
import com.adms.admin.entity.JwtUser;
import com.adms.admin.mapper.JobUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author wangyz
 * @date 2021年04月08日 11:34
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private JobUserMapper jobUserMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        JobUser jobUser = jobUserMapper.loadByUserName(s);
        return new JwtUser(jobUser);
    }
}
