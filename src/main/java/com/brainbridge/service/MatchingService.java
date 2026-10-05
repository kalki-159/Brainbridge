package com.brainbridge.service;

import com.brainbridge.entity.User;
import com.brainbridge.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchingService {

    private final UserRepository userRepository;

    public MatchingService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Map<String, Object>> findMatches(Long userId) {
        User current = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        List<User> users = userRepository.findAll();

        return users.stream()
                .filter(user -> !Objects.equals(user.getId(), current.getId()))
                .map(user -> buildMatch(current, user))
                .filter(match -> (Integer) match.get("score") > 0)
                .sorted((a, b) -> Integer.compare((Integer) b.get("score"), (Integer) a.get("score")))
                .collect(Collectors.toList());
    }

    private Map<String, Object> buildMatch(User a, User b) {
        Set<String> aSkills = tokens(a.getSkills());
        Set<String> aGoals = tokens(a.getLearningGoals());
        Set<String> aInterests = tokens(a.getInterests());

        Set<String> bSkills = tokens(b.getSkills());
        Set<String> bGoals = tokens(b.getLearningGoals());
        Set<String> bInterests = tokens(b.getInterests());

        Set<String> aNeedsFromB = intersection(aGoals, bSkills);
        Set<String> bNeedsFromA = intersection(bGoals, aSkills);
        Set<String> sharedInterests = intersection(aInterests, bInterests);

        int score = Math.min(40, aNeedsFromB.size() * 20)
                + Math.min(40, bNeedsFromA.size() * 20)
                + Math.min(20, sharedInterests.size() * 10);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("userId", b.getId());
        result.put("name", b.getName());
        result.put("bio", b.getBio());
        result.put("skills", b.getSkills());
        result.put("learningGoals", b.getLearningGoals());
        result.put("interests", b.getInterests());
        result.put("score", Math.min(score, 100));
        result.put("matchedNeeds", aNeedsFromB);
        result.put("reciprocalMatches", bNeedsFromA);
        result.put("sharedInterests", sharedInterests);

        return result;
    }

    private Set<String> tokens(String value) {
        if (value == null || value.isBlank()) {
            return Collections.emptySet();
        }

        return Arrays.stream(value.split("[,;|]"))
                .map(String::trim)
                .map(String::toLowerCase)
                .filter(token -> !token.isBlank())
                .collect(Collectors.toSet());
    }

    private Set<String> intersection(Set<String> first, Set<String> second) {
        Set<String> result = new HashSet<>(first);
        result.retainAll(second);
        return result;
    }
}
