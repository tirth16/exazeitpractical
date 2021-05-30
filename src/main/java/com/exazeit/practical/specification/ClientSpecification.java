package com.exazeit.practical.specification;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.exazeit.practical.entity.Client;
import com.exazeit.practical.util.SearchCriteria;

public class ClientSpecification implements Specification<Client> {

	private List<SearchCriteria> criterias;

	public ClientSpecification(List<SearchCriteria> criterias) {
		super();
		this.criterias = criterias;
	}

	@Override
	public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		return builder
				.or(CommonSearchPredicate.searchCriteriaPredicates(root, builder, criterias).toArray(new Predicate[0]));
	}

}
