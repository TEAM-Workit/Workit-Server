package workit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workit.dto.ability.AbilityInfo;
import workit.dto.ability.AllAbilitiesResponseDto;
import workit.repository.AbilityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AbilityService {
    private final AbilityRepository abilityRepository;

    public AllAbilitiesResponseDto getAllAbilities() {
        List<AbilityInfo> abilityInfos = abilityRepository.findAll().stream()
                .map(AbilityInfo::new)
                .collect(Collectors.toList());

        return new AllAbilitiesResponseDto(abilityInfos);
    }
}
