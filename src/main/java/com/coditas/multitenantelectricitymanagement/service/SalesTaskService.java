package com.coditas.multitenantelectricitymanagement.service;

import com.coditas.multitenantelectricitymanagement.constants.ExceptionConstants;
import com.coditas.multitenantelectricitymanagement.dto.salestask.SalesTaskRequest;
import com.coditas.multitenantelectricitymanagement.dto.salestask.SalesTaskResponse;
import com.coditas.multitenantelectricitymanagement.entity.SalesTask;
import com.coditas.multitenantelectricitymanagement.entity.User;
import com.coditas.multitenantelectricitymanagement.enums.TaskStatus;
import com.coditas.multitenantelectricitymanagement.exception.DuplicateResourceException;
import com.coditas.multitenantelectricitymanagement.exception.ResourceNotFoundException;
import com.coditas.multitenantelectricitymanagement.exception.UnAuthenticatedUserException;
import com.coditas.multitenantelectricitymanagement.mapper.SalesTaskMapper;
import com.coditas.multitenantelectricitymanagement.repository.SalesTaskRepository;
import com.coditas.multitenantelectricitymanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class SalesTaskService {

    private final SalesTaskRepository salesTaskRepository;
    private final UserRepository userRepository;
    private final SalesTaskMapper salesTaskMapper;

    @Transactional
    public SalesTaskResponse createTask(SalesTaskRequest request) {

        if (salesTaskRepository.existsByTask(request.getTask())) {
            throw new DuplicateResourceException(ExceptionConstants.DUPLICATERESOURCE);
        }

        SalesTask task = new SalesTask();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UnAuthenticatedUserException(ExceptionConstants.UNAUTHENTICATEDUSER);
        }
        String currentUsername = authentication.getName();
        User manager = userRepository.findByUsername(currentUsername).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstants.RESOURCENOTFOUND)
        );
        task.setManager(manager);

        if (request.getSalesTeamMemberId() != null) {
            User salesTeamMember = userRepository.findById(request.getSalesTeamMemberId()).orElseThrow(
                    () -> new ResourceNotFoundException(ExceptionConstants.RESOURCENOTFOUND)
            );
            task.setSalesMember(salesTeamMember);
            task.setAssignedAt(Instant.now());
        }

        task.setTask(request.getTask());
        task.setStatus(TaskStatus.PENDING);

        SalesTask savedTask = salesTaskRepository.save(task);
        return salesTaskMapper.toDTO(savedTask);

    }
}
