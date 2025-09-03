package com.nsbm.uni_cricket_360.service.impl;

import com.nsbm.uni_cricket_360.dto.FitnessTestDTO;
import com.nsbm.uni_cricket_360.dto.InjuryDTO;
import com.nsbm.uni_cricket_360.entity.FitnessTest;
import com.nsbm.uni_cricket_360.entity.Injury;
import com.nsbm.uni_cricket_360.entity.Player;
import com.nsbm.uni_cricket_360.exception.NotFoundException;
import com.nsbm.uni_cricket_360.repository.InjuryRepo;
import com.nsbm.uni_cricket_360.repository.PlayerRepo;
import com.nsbm.uni_cricket_360.service.InjuryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InjuryServiceImpl implements InjuryService {

    @Autowired
    private InjuryRepo injuryRepo;

    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<InjuryDTO> getAllInjuries() {
        return mapper.map(injuryRepo.findAll(), new TypeToken<List<InjuryDTO>>() {
        }.getType());
    }

    @Override
    public InjuryDTO searchInjury(Long id) {
        Injury injury = injuryRepo.findById(id).orElseThrow(() -> new NotFoundException("Injury record not found with id " + id));
        return mapper.map(injury, InjuryDTO.class);
    }

    @Override
    public InjuryDTO saveInjury(InjuryDTO dto) {
        Injury injury = mapper.map(dto, Injury.class);

        // Set player from DB
        if (dto.getPlayer() != null && dto.getPlayer().getId() != null) {
            Player player = playerRepo.findById(dto.getPlayer().getId())
                    .orElseThrow(() -> new NotFoundException("Player not found with id: " + dto.getPlayer().getId()));
            injury.setPlayer(player);
        }

        Injury saved = injuryRepo.save(injury);
        return mapper.map(saved, InjuryDTO.class);
    }

    @Override
    public InjuryDTO updateInjury(Long id, InjuryDTO dto) {
        Injury existingInjury = injuryRepo.findById(id).orElseThrow(() -> new NotFoundException("Injury records not found with id " + id));

        // Set player from DB
        if (dto.getPlayer() != null && dto.getPlayer().getId() != null) {
            Player player = playerRepo.findById(dto.getPlayer().getId())
                    .orElseThrow(() -> new NotFoundException("Player not found with id: " + dto.getPlayer().getId()));
            existingInjury.setPlayer(player);
        }

        existingInjury.setInjury_type(dto.getInjury_type());
        existingInjury.setDate_reported(dto.getDate_reported());
        existingInjury.setRecovery_days(dto.getRecovery_days());
        existingInjury.setStatus(dto.getStatus());

        Injury saved = injuryRepo.save(existingInjury);
        return mapper.map(saved, InjuryDTO.class);
    }

    @Override
    public void deleteInjury(Long id) {
        Injury injury = injuryRepo.findById(id).orElseThrow(() -> new NotFoundException("Injury record not found with id " + id));
        injuryRepo.delete(injury);
    }
}
