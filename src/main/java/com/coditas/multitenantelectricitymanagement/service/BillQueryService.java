package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.constants.ExceptionConstants;
import com.coditas.multitenantelectricitymanagement.dto.billQuery.BillQueryRequest;
import com.coditas.multitenantelectricitymanagement.dto.billQuery.BillQueryResponse;
import com.coditas.multitenantelectricitymanagement.entity.BillQuery;
import com.coditas.multitenantelectricitymanagement.entity.User;
import com.coditas.multitenantelectricitymanagement.enums.QueryStatus;
import com.coditas.multitenantelectricitymanagement.enums.Role;
import com.coditas.multitenantelectricitymanagement.exception.ResourceNotFoundException;
import com.coditas.multitenantelectricitymanagement.exception.RoleMisMatchException;
import com.coditas.multitenantelectricitymanagement.mapper.BillQueryMapper;
import com.coditas.multitenantelectricitymanagement.repository.BillQueryRepository;
import com.coditas.multitenantelectricitymanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillQueryService {

    private final BillQueryRepository billQueryRepository;
    private final UserRepository userRepository;
    private final BillQueryMapper billQueryMapper;

    public BillQueryResponse issueQuery(BillQueryRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String billerName = authentication.getName();

        User biller = userRepository.findByUsername(billerName).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstants.BILLER_NOT_FOUND)
        );

        if (!biller.getRole().equals(Role.BILLER)) {
            throw new RoleMisMatchException(ExceptionConstants.BILLER_ROLE_MISMATCH);
        }

        BillQuery query = new BillQuery();
        query.setBiller(biller);
        query.setQuery(request.getQuery());
        query.setStatus(QueryStatus.PENDING);
        BillQuery savedBillQuery = billQueryRepository.save(query);

        return billQueryMapper.toDTO(savedBillQuery);
    }

    public List<BillQueryResponse> getAllQueries() {
        List<BillQuery> queries = billQueryRepository.findAll();
        return billQueryMapper.toDTOList(queries);

    }
}
