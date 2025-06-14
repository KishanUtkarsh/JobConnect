package com.jobconnect.user.service;

import com.jobconnect.user.dto.RecruiterRequestDTO;
import com.jobconnect.user.dto.RecruiterResponseDTO;

import java.util.UUID;

public interface RecruiterService {

    RecruiterResponseDTO getRecruiter(UUID userId);

    RecruiterResponseDTO updateRecruiter(UUID userId, RecruiterRequestDTO recruiterRequestDTO);

    String deleteRecruiter(UUID userId);
}
