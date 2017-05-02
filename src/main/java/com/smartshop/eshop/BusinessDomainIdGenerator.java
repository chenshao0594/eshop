package com.smartshop.eshop;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;

public class BusinessDomainIdGenerator extends ObjectIdGenerator<BusinessDomainId> {

	protected final Class<?> _scope;

	protected BusinessDomainIdGenerator() {
		_scope = Object.class;
	}

	@Override
	public Class<?> getScope() {
		return _scope;
	}

	@Override
	public boolean canUseFor(ObjectIdGenerator<?> gen) {
		return (gen instanceof BusinessDomainIdGenerator);
	}

	@Override
	public ObjectIdGenerator<BusinessDomainId> forScope(Class<?> scope) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ObjectIdGenerator<BusinessDomainId> newForSerialization(Object context) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey key(Object key) {
		if (key == null) {
			return null;
		}
		return new IdKey(getClass(), null, key);
	}

	@Override
	public BusinessDomainId generateId(Object forPojo) {
		// TODO Auto-generated method stub
		return null;
	}
}

class BusinessDomainId {
	private long id;
	private String displayName;

	public BusinessDomainId(long id, String displayName) {
		this.id = id;
		this.displayName = displayName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
