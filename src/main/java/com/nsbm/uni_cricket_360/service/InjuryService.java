package com.nsbm.uni_cricket_360.service;

import com.nsbm.uni_cricket_360.dto.InjuryDTO;

import java.util.List;

public interface InjuryService {

    List<InjuryDTO> getAllInjuries();

    InjuryDTO searchInjury(Long id);

    InjuryDTO saveInjury(InjuryDTO dto);

    InjuryDTO updateInjury(Long id, InjuryDTO dto);

    void deleteInjury(Long id);

}
