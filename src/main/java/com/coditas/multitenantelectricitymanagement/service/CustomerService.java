package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.constants.ExceptionConstants;
import com.coditas.multitenantelectricitymanagement.dto.customer.CustomerRequest;
import com.coditas.multitenantelectricitymanagement.dto.customer.CustomerResponse;
import com.coditas.multitenantelectricitymanagement.dto.userclientcompany.UserClientCompanyRequest;
import com.coditas.multitenantelectricitymanagement.dto.userclientcompany.UserClientCompanyResponse;
import com.coditas.multitenantelectricitymanagement.entity.*;
import com.coditas.multitenantelectricitymanagement.enums.Role;
import com.coditas.multitenantelectricitymanagement.exception.ResourceNotFoundException;
import com.coditas.multitenantelectricitymanagement.mapper.CustomerMapper;
import com.coditas.multitenantelectricitymanagement.mapper.UserClientCompanyMapper;
import com.coditas.multitenantelectricitymanagement.mapper.UserMapper;
import com.coditas.multitenantelectricitymanagement.repository.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AreaRepository areaRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ClientCompanyRepository clientCompanyRepository;
    private final UserClientCompanyRepository userClientCompanyRepository;
    private final UserClientCompanyMapper userClientCompanyMapper;

    @Transactional
    public CustomerResponse registerCustomer(Long areaId, CustomerRequest request) {
        Area area = areaRepository.findById(areaId).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND)
        );
        User user = userMapper.toEntity(request.getUser());
        user.setRole(Role.CUSTOMER);
        User savedUser = userRepository.save(user);

        Customer customer = new Customer();
        customer.setUser(savedUser);
        customer.setArea(area);
        customer.setAddress(request.getAddress());
        customer.setMobileNumber(request.getMobileNumber());

        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toDTO(savedCustomer);
    }

    public UserClientCompanyResponse assignServiceProvider(@Valid UserClientCompanyRequest request) {

        User user = userRepository.findById(request.getUser()).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND)
        );

        ClientCompany clientCompany = clientCompanyRepository.findById(request.getClientCompany()).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstants.RESOURCE_NOT_FOUND)
        );

        UserClientCompany userClient = new UserClientCompany();

        UserClientCompanyId compositeId = new UserClientCompanyId();
        userClient.setId(compositeId);

        userClient.setUser(user);
        userClient.setClientCompany(clientCompany);

        UserClientCompany savedUserClient = userClientCompanyRepository.save(userClient);

        return userClientCompanyMapper.toDTO(savedUserClient);

    }
}
