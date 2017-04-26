package com.smartshop.eshop.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.common.BusinessConstants;
import com.smartshop.eshop.domain.Country;
import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.domain.Zone;
import com.smartshop.eshop.domain.ZoneDescription;
import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.eshop.repository.ZoneRepository;
import com.smartshop.eshop.repository.search.ZoneSearchRepository;
import com.smartshop.eshop.service.ZoneService;

/**
 * Service Implementation for managing Zone.
 */
@Service
@Transactional
public class ZoneServiceImpl extends AbstractDomainServiceImpl<Zone, Long> implements ZoneService {

	private final Logger LOGGER = LoggerFactory.getLogger(ZoneServiceImpl.class);
	private final static String ZONE_CACHE_PREFIX = "ZONES_";
	private final ZoneRepository zoneRepository;
	private final ZoneSearchRepository zoneSearchRepository;

	public ZoneServiceImpl(ZoneRepository zoneRepository, ZoneSearchRepository zoneSearchRepository) {
		super(zoneRepository, zoneSearchRepository);
		this.zoneRepository = zoneRepository;
		this.zoneSearchRepository = zoneSearchRepository;
	}

	@Override
	public Zone getByCode(String code) {
		return zoneRepository.findByCode(code);
	}

	@Override
	public void addDescription(Zone zone, ZoneDescription description) throws BusinessException {
		if (zone.getDescriptions() != null) {
			if (!zone.getDescriptions().contains(description)) {
				zone.getDescriptions().add(description);
				update(zone);
			}
		} else {
			Set<ZoneDescription> descriptions = new HashSet<ZoneDescription>();
			descriptions.add(description);
			zone.setDescriptions(descriptions);
			update(zone);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Zone> getZones(Country country, Language language) throws BusinessException {

		List<Zone> zones = null;
		try {

			String cacheKey = ZONE_CACHE_PREFIX + country.getIsoCode() + BusinessConstants.UNDERSCORE + language.getCode();

			zones = zoneRepository.listByLanguageAndCountry(country.getIsoCode(), language.getId());

			// set names
			for (Zone zone : zones) {
				ZoneDescription description = (ZoneDescription) zone.getDescriptions().toArray()[0];
				zone.setName(description.getName());
			}

		} catch (Exception e) {
			LOGGER.error("getZones()", e);
		}
		return zones;

	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Zone> getZones(Language language) throws BusinessException {

		Map<String, Zone> zones = null;
		try {

			String cacheKey = ZONE_CACHE_PREFIX + language.getCode();

			zones = new HashMap<String, Zone>();
			List<Zone> zns = zoneRepository.listByLanguage(language.getId());

			// set names
			for (Zone zone : zns) {
				ZoneDescription description = (ZoneDescription) zone.getDescriptions().toArray()[0];
				zone.setName(description.getName());
				zones.put(zone.getCode(), zone);

			}

		} catch (Exception e) {
			LOGGER.error("getZones()", e);
		}
		return zones;

	}

}
