package com.nsbm.uni_cricket_360.service;

import com.nsbm.uni_cricket_360.dto.MatchDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MatchService {
    List<MatchDTO> getAllMatches();

    MatchDTO getMatchById(Long id);

    MatchDTO saveMatch(MatchDTO dto, MultipartFile imageFile);

    MatchDTO updateMatch(Long id, MatchDTO dto, MultipartFile imageFile);

    MatchDTO updateMatchImage(Long id, MultipartFile imageFile);

    void deleteMatch(Long id);
}
