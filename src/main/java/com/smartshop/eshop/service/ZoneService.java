package com.smartshop.eshop.service;

import java.util.List;
import java.util.Map;

import com.smartshop.eshop.domain.Country;
import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.domain.Zone;
import com.smartshop.eshop.domain.ZoneDescription;
import com.smartshop.eshop.exception.BusinessException;

/**
 * Service Interface for managing Zone.
 */
public interface ZoneService extends AbstractDomainService<Zone, Long> {
	Zone getByCode(String code);

	void addDescription(Zone zone, ZoneDescription description) throws BusinessException;

	List<Zone> getZones(Country country, Language language) throws BusinessException;

	Map<String, Zone> getZones(Language language) throws BusinessException;

}