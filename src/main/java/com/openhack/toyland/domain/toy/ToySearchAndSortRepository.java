package com.openhack.toyland.domain.toy;

import static com.openhack.toyland.domain.QMaintenance.*;
import static com.openhack.toyland.domain.QOrganization.*;
import static com.openhack.toyland.domain.skill.QTechStack.*;
import static com.openhack.toyland.domain.toy.QToy.*;
import javax.persistence.EntityManager;
import java.util.List;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class ToySearchAndSortRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public ToySearchAndSortRepository(EntityManager entityManager) {
		this.jpaQueryFactory = new JPAQueryFactory(entityManager);
	}

	public List<Toy> findToysByConditions(Pageable pageable, List<Long> organizations, List<Long> skills,
		List<String> categories, List<String> periods, List<String> searches) {
		return jpaQueryFactory.select(toy)
			.from(toy)
			.leftJoin(maintenance).on(toy.id.eq(maintenance.toyId))
			.leftJoin(organization).on(toy.organizationId.eq(organization.id))
			.leftJoin(techStack).on(techStack.toyId.eq(toy.id))
			.where(
				isOrganizationFilter(organizations),
				isSkillFilter(skills),
				isCategoryFilter(categories),
				isPeriodFilter(periods)
				// isSearchFilter(searches)
			)
			.orderBy(orderType(pageable.getSort()))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();
	}

	private OrderSpecifier<?> orderType(Sort sort) {
		if (StringUtils.equals(sort.toString(), "active")) {
			return maintenance.active.desc();
		}

		return toy.createdDate.desc();
	}

	private BooleanExpression isOrganizationFilter(List<Long> organizationId) {
		if (organizationId == null) {
			return null;
		}

		return Expressions.anyOf(organizationId.stream().map(this::isOrganization).toArray(BooleanExpression[]::new));
	}

	private BooleanExpression isOrganization(Long organizationId) {
		if (organizationId != null) {
			return null;
		}
		return organization.id.eq(organizationId);
	}

	private BooleanExpression isSkillFilter(List<Long> skillId) {
		if (skillId == null) {
			return null;
		}

		return Expressions.anyOf(skillId.stream().map(this::isSkill).toArray(BooleanExpression[]::new));
	}

	private BooleanExpression isSkill(Long skillId) {
		if (skillId != null) {
			return null;
		}
		return techStack.skillId.eq(skillId);
	}

	private BooleanExpression isCategoryFilter(List<String> category) {
		if (category == null) {
			return null;
		}

		return Expressions.anyOf(category.stream().map(this::isCategory).toArray(BooleanExpression[]::new));
	}

	private BooleanExpression isCategory(String category) {
		if (StringUtils.isEmpty(category)) {
			return null;
		}
		return toy.category.eq(Category.valueOf(category));
	}

	private BooleanExpression isPeriodFilter(List<String> period) {
		if (period == null) {
			return null;
		}

		return Expressions.anyOf(period.stream().map(this::isPeriod).toArray(BooleanExpression[]::new));

	}

	private BooleanExpression isPeriod(String period) {
		if (StringUtils.isEmpty(period)) {
			return null;
		}
		return toy.period.eq(Period.valueOf(period));
	}

	// TODO: 명확한 검색 범위
	// private BooleanExpression isSearchFilter(List<String> search) {
	// }
	//
	// private BooleanExpression isSearch(String search) {
	//
	// }
}
