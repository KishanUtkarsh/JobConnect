package com.jobconnect.user.service;

import com.jobconnect.user.dto.RecruiterRequestDTO;
import com.jobconnect.user.dto.RecruiterResponseDTO;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface RecruiterService {

    RecruiterResponseDTO getRecruiter(UUID userId);

    RecruiterResponseDTO updateRecruiter(RecruiterRequestDTO recruiterRequestDTO,
                                         Authentication authentication);

    String deleteRecruiter(UUID userId);
}
