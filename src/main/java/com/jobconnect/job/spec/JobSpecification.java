package com.jobconnect.job.spec;

import com.jobconnect.job.entity.Job;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Set;

public class JobSpecification {

    private JobSpecification() {
        throw new IllegalStateException(
            "Utility class, cannot be instantiated"
        );
    }

    public static Specification<Job> hasLocation(String workLocation) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("location"), workLocation.toLowerCase());
    }

    public static Specification<Job> hasJobType(String jobType) {
        return (root , query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("employmentType"), jobType.toLowerCase());
    }

    public static Specification<Job> hasSkills(Set<String> skills) {
        return (root, query, criteriaBuilder) -> {
            Join<Job, String> skillsJoin = root.join("skills");
            List<String> lowerSkills = skills.stream()
                    .map(String::toLowerCase)
                    .toList();
             return skillsJoin.in(lowerSkills);
        };
    }

    public static Specification<Job> hasKeyword(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                    criteriaBuilder.like(root.get("title"), "%" + keyword.toLowerCase() + "%"),
                    criteriaBuilder.like(root.get("description"), "%" + keyword.toLowerCase() + "%"),
                    criteriaBuilder.like(root.get("companyName"), "%" + keyword.toLowerCase() + "%")
                );
    }
}
