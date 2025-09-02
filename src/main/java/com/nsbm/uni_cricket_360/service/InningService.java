package com.nsbm.uni_cricket_360.service;

import com.nsbm.uni_cricket_360.dto.InningDTO;

import java.util.List;

public interface InningService {
    List<InningDTO> getAllInnings();

    InningDTO searchInningById(Long id);

    InningDTO saveInning(InningDTO dto);

    InningDTO updateInning(Long id, InningDTO dto);

    void deleteInning(Long id);
}
