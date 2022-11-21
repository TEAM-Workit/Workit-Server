package workit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workit.dto.ability.AbilityCollectionResponseDto;
import workit.dto.ability.AbilityInfo;
import workit.dto.ability.AllAbilitiesResponseDto;
import workit.entity.*;
import workit.repository.*;
import workit.util.CustomException;
import workit.util.ResponseCode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AbilityService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final WorkAbilityRepository workAbilityRepository;
    private final WorkRepository workRepository;
    private final AbilityRepository abilityRepository;

    public AllAbilitiesResponseDto getAllAbilities() {
        List<AbilityInfo> abilityInfos = abilityRepository.findAll().stream()
                .map(AbilityInfo::new)
                .collect(Collectors.toList());

        return new AllAbilitiesResponseDto(abilityInfos);
    }

    public List<AbilityCollectionResponseDto> getAbilityCollection(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ResponseCode.USER_NOT_FOUND)
        );


        List<Project> projects = projectRepository.findByUser(user);
        HashMap<Long, Integer> abilityCount = new HashMap<>();

        projects.forEach(project -> {
            List<Work> works = workRepository.findByProject(project);
            works.forEach(work -> {
                List<WorkAbility> workAbilities = workAbilityRepository.findByWork(work);
                workAbilities.forEach((workAbility -> {
                    Ability ability = workAbility.getAbility();
                    Long abilityId = ability.getId();

                    if (!abilityCount.containsKey(abilityId)) {
                        abilityCount.put(abilityId, 0);
                    }
                    abilityCount.put(abilityId, abilityCount.get(abilityId) + 1);
                }));
            });
        });

        List<AbilityCollectionResponseDto> responseDtos = new ArrayList<>();
        List<Ability> abilities = abilityRepository.findAllById(abilityCount.keySet());
        abilities.stream()
                .sorted(Comparator.comparing(Ability::getName))
                .forEach(ability -> {
                    AbilityCollectionResponseDto responseDto =
                            new AbilityCollectionResponseDto(ability, abilityCount.get(ability.getId()));
                    responseDtos.add(responseDto);
                });

        return responseDtos;
    }
}
