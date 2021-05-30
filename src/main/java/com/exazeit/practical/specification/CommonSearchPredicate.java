package com.exazeit.practical.specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.exazeit.practical.enumration.SearchOperation;
import com.exazeit.practical.util.SearchCriteria;

public class CommonSearchPredicate {
	public static <T> List<Predicate> searchCriteriaPredicates(Root<T> root, CriteriaBuilder builder,
			List<SearchCriteria> criterias) {
		List<Predicate> predicates = new ArrayList<>();

		for (SearchCriteria criteria : criterias) {
			if (criteria.getOperation().equalsIgnoreCase(SearchOperation.EQUALS.toString())) {
				if (root.get(criteria.getKey()).getJavaType() == String.class) {
					predicates.add(builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%"));
				} else if (root.get(criteria.getKey()).getJavaType() == Date.class) {
					Expression<String> dateStringExpr = builder.function("date_format", String.class,
							root.get(criteria.getKey()), builder.literal("%Y-%m-%d %T"));
					predicates.add(builder.like(builder.lower(dateStringExpr),
							"%" + criteria.getValue().toString().toLowerCase() + "%"));
				} else {
					predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
				}
			} else if (criteria.getOperation().equalsIgnoreCase(SearchOperation.UNEQUALS.toString())) {
				if (root.get(criteria.getKey()).getJavaType() == String.class) {
					predicates.add(builder.notEqual(root.<String>get(criteria.getKey()), criteria.getValue()));
				} else {
					System.out.println(" KEY" + criteria.getKey() + "  ROOT KEY" + root.get(criteria.getKey())
							+ "    VALUE" + criteria.getValue());
					Predicate p = builder.notEqual(root.get(criteria.getKey()), criteria.getValue());

					predicates.add(p);
				}
			}
		}
		return predicates;
	}

}
