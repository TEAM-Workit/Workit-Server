package workit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workit.dto.ability.AbilityCollectionResponseDto;
import workit.dto.ability.AbilityInfo;
import workit.dto.ability.AllAbilitiesResponseDto;
import workit.dto.ability.AllAbilityCollectionDetailResponseDto;
import workit.entity.*;
import workit.repository.*;
import workit.util.CustomException;
import workit.util.ResponseCode;

import java.util.*;
import java.util.stream.Collectors;

import static workit.service.ProjectService.sortCollection;
import static workit.service.ProjectService.stringToDateConverter;

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
        Map<Long, Integer> abilityCount = new HashMap<>();

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

    public AllAbilityCollectionDetailResponseDto getAbilityCollectionDetail(Long userId, Long abilityId) {
        Ability ability = validateUserAndAbility(userId, abilityId);

        List<WorkAbility> workAbilities = workAbilityRepository.findByAbility(ability);
        Set<Long> workIds = new HashSet<>();

        workAbilities.forEach(workAbility -> {
            Work work = workAbility.getWork();
            workIds.add(work.getId());
        });
    public AllAbilityCollectionDetailResponseDto getAbilityCollectionDetailByDateFilter
            (Long userId, Long abilityId, String start, String end) {
        Ability ability = validateUserAndAbility(userId, abilityId);
        Set<Long> workIds = getWorkIds(ability);

        List<Date> convertDate = stringToDateConverter(start, end);
        Date startDate = convertDate.get(0);
        Date endPlusOne = convertDate.get(1);

        List<Work> works = new ArrayList<>();

        workRepository
                .findAllById(workIds).stream()
                .filter(work -> work.getDate().equals(startDate) || work.getDate().after(startDate))
                .filter(work -> work.getDate().equals(endPlusOne) || work.getDate().before(endPlusOne))
                .forEach(works::add);

        List<Work> emptyList = Collections.emptyList();

        if (works.size() == 0) {
            return new AllAbilityCollectionDetailResponseDto(ability.getName(), sortCollection(emptyList));
        }

        return new AllAbilityCollectionDetailResponseDto(ability.getName(), sortCollection(works));
    }

    private Ability validateUserAndAbility(Long userId, Long abilityId) {
        userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ResponseCode.USER_NOT_FOUND));

        return abilityRepository.findById(abilityId).orElseThrow(
                () -> new CustomException(ResponseCode.ABILITY_NOT_FOUND)
        );
    }

    private Set<Long> getWorkIds(Ability ability) {
        List<WorkAbility> workAbilities = workAbilityRepository.findByAbility(ability);
        Set<Long> workIds = new HashSet<>();

        workAbilities.forEach(workAbility -> {
            Work work = workAbility.getWork();
            workIds.add(work.getId());
        });

        return workIds;
    }
}
