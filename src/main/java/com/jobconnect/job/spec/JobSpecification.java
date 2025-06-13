package com.jobconnect.job.spec;

import com.jobconnect.job.entity.Job;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

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

    public static Specification<Job> hasSkills(List<String> skills) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            for (String skill : skills) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("skills"), "%" + skill.toLowerCase() + "%"));
            }
            return predicate;
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
