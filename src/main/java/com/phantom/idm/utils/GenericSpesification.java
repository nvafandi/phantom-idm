package com.phantom.idm.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GenericSpesification<T> implements Specification<T> {

	private static final long serialVersionUID = 1900581010229669687L;

	private List<SearchCriteria> list;

	public GenericSpesification() {
		this.list = new ArrayList<>();
	}

	public void add(SearchCriteria criteria) {
		list.add(criteria);
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		List<Predicate> predicates = new ArrayList<>();

		for (SearchCriteria criteria : list) {
			if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
				predicates.add(builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
				predicates.add(builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
				predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
				predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
				predicates.add(builder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
			} else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
				predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
			} else if (criteria.getOperation().equals(SearchOperation.EQUAL_BOOLEAN_TRUE)) {
				predicates.add(builder.equal(root.get(criteria.getKey()), true));
			} else if (criteria.getOperation().equals(SearchOperation.EQUAL_BOOLEAN_FALSE)) {
				predicates.add(builder.equal(root.get(criteria.getKey()), false));
			} else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
				predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
						"%" + criteria.getValue().toString().toLowerCase() + "%"));
			} else if (criteria.getOperation().equals(SearchOperation.MATCHITEMS)) {
				predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
						"%" + criteria.getValue().toString().toUpperCase()));
			} else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
				predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
						criteria.getValue().toString().toLowerCase() + "%"));
			} else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL_DATE)) {
				try {
					predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()),
							DateUtil.parseDate(criteria.getValue().toString())));
				} catch (ParseException e) {
					log.error(e.toString());
				}

			} else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_DATE)) {
				try {
					predicates.add(builder.lessThan(root.get(criteria.getKey()),
							DateUtil.parseDate(criteria.getValue().toString())));
				} catch (ParseException e) {
					log.error(e.toString());
				}
			} else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL_DATE)) {
				try {
					predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()),
							DateUtil.parseDate(criteria.getValue().toString())));
				} catch (ParseException e) {
					log.error(e.toString());
				}
			} else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_DATE)) {
				try {
					predicates.add(builder.greaterThan(root.get(criteria.getKey()),
							DateUtil.parseDate(criteria.getValue().toString())));
				} catch (ParseException e) {
					log.error(e.toString());
				}
			} else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL_NUMERIC)) {
				predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()),
						Integer.parseInt(criteria.getValue().toString())));
			} else if (criteria.getOperation().equals(SearchOperation.EQUAL_NUMERIC)) {
				predicates.add(
						builder.equal(root.get(criteria.getKey()), Integer.parseInt(criteria.getValue().toString())));
			} else if (criteria.getOperation().equals(SearchOperation.IN)) {
				In<String> inClause = builder.in(root.get(criteria.getKey()));

				String[] inData = criteria.getValue().toString().split(",");

				for (String in : inData) {
					inClause.value(in);
				}
				predicates.add(inClause);
			} else if (criteria.getOperation().equals(SearchOperation.IS_NOT_NULL)) {
				predicates.add(builder.isNotNull((root.get(criteria.getKey()))));
			} else if (criteria.getOperation().equals(SearchOperation.IS_NULL)) {
				predicates.add(builder.isNull((root.get(criteria.getKey()))));
			}

		}

		return builder.and(predicates.toArray(new Predicate[0]));
	}
}