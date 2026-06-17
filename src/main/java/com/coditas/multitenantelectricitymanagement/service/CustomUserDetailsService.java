package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.repository.EmployeeRepository;
import com.coditas.multitenantelectricitymanagement.repository.UserRepository;
import com.coditas.multitenantelectricitymanagement.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String tenant = TenantContext.getTenant();
        UserDetails user = null;
        if(tenant == null || tenant.equals("public")) {
            user = userRepository.findByUsername(username).orElseThrow(
                    () -> new UsernameNotFoundException("UserNotFound")
            );
        }
        else{
            user = employeeRepository.findByName(username);
            if(user == null){
                user = employeeRepository.findByEmail(username);
            }
        }
        if (user == null) {
            throw new UsernameNotFoundException("Employee not found in tenant");
        }
        return user;
    }
}
