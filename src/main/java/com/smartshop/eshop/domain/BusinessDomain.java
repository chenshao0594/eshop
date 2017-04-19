package com.smartshop.eshop.domain;

import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;

import org.hibernate.Hibernate;

public abstract class BusinessDomain<K extends Serializable & Comparable<K>, E extends BusinessDomain<K, ?>>
		extends AbstractAuditingEntity implements BusinessDomainInterface, Comparable<E> {

	public static final Collator DEFAULT_STRING_COLLATOR = Collator.getInstance(Locale.FRENCH);

	static {
		DEFAULT_STRING_COLLATOR.setStrength(Collator.PRIMARY);
	}

	public abstract K getId();

	public abstract void setId(K id);

	public boolean isNew() {
		return getId() == null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (object == this) {
			return true;
		}
		if (Hibernate.getClass(object) != Hibernate.getClass(this)) {
			return false;
		}
		BusinessDomain<K, E> entity = (BusinessDomain<K, E>) object; // NOSONAR
																		// :
																		// traité
																		// au-dessus
																		// mais
																		// wrapper
																		// Hibernate
		K id = getId();

		if (id == null) {
			return false;
		}

		return id.equals(entity.getId());
	}

	@Override
	public int hashCode() {
		int hash = 7;
		K id = getId();
		hash = 31 * hash + ((id == null) ? 0 : id.hashCode());
		return hash;
	}

	@Override
	public int compareTo(E o) {
		if (this == o) {
			return 0;
		}
		return this.getId().compareTo(o.getId());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("entity.");
		builder.append(Hibernate.getClass(this).getSimpleName());
		builder.append("<");
		builder.append(getId());
		builder.append("-");
		builder.append(super.toString());
		builder.append(">");

		return builder.toString();
	}

}
