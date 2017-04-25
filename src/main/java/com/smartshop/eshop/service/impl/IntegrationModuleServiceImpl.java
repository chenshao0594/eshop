package com.smartshop.eshop.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartshop.eshop.core.config.ModuleConfig;
import com.smartshop.eshop.domain.IntegrationModule;
import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.eshop.repository.IntegrationModuleRepository;
import com.smartshop.eshop.repository.search.IntegrationModuleSearchRepository;
import com.smartshop.eshop.service.IntegrationModuleService;

import net.minidev.json.JSONValue;

/**
 * Service Implementation for managing IntegrationModule.
 */
@Service
@Transactional
public class IntegrationModuleServiceImpl extends AbstractDomainServiceImpl<IntegrationModule, Long>
implements IntegrationModuleService {

	private final Logger LOGGER = LoggerFactory.getLogger(IntegrationModuleServiceImpl.class);
	private final IntegrationModuleRepository integrationModuleRepository;
	private final IntegrationModuleSearchRepository integrationModuleSearchRepository;
	@Inject
	private IntegrationModulesLoader integrationModulesLoader;

	public IntegrationModuleServiceImpl(IntegrationModuleRepository integrationModuleRepository,
			IntegrationModuleSearchRepository integrationModuleSearchRepository) {
		super(integrationModuleRepository, integrationModuleSearchRepository);
		this.integrationModuleRepository = integrationModuleRepository;
		this.integrationModuleSearchRepository = integrationModuleSearchRepository;
	}
	@Override
	public List<IntegrationModule> getIntegrationModules(String module) {
		List<IntegrationModule> modules = null;
		try {
			//CacheUtils cacheUtils = CacheUtils.getInstance();
			if(modules==null) {
				modules = integrationModuleRepository.findByModule(module);
				//set json objects
				for(IntegrationModule mod : modules) {
					String regions = mod.getRegions();
					if(regions!=null) {
						Object objRegions=JSONValue.parse(regions); 
						JSONArray arrayRegions=(JSONArray)objRegions;
						Iterator i = arrayRegions.iterator();
						while(i.hasNext()) {
							mod.getRegionsSet().add((String)i.next());
						}
					}
					String details = mod.getConfigDetails();
					if(details!=null) {
						//Map objects = mapper.readValue(config, Map.class);
						Map<String,String> objDetails= (Map<String, String>) JSONValue.parse(details); 
						mod.setDetails(objDetails);
					}
					String configs = mod.getConfiguration();
					if(configs!=null) {
						//Map objects = mapper.readValue(config, Map.class);
						Object objConfigs=JSONValue.parse(configs); 
						JSONArray arrayConfigs=(JSONArray)objConfigs;
						Map<String,ModuleConfig> moduleConfigs = new HashMap<String,ModuleConfig>();
						Iterator i = arrayConfigs.iterator();
						while(i.hasNext()) {

							Map values = (Map)i.next();
							String env = (String)values.get("env");
							ModuleConfig config = new ModuleConfig();
							config.setScheme((String)values.get("scheme"));
							config.setHost((String)values.get("host"));
							config.setPort((String)values.get("port"));
							config.setUri((String)values.get("uri"));
							config.setEnv((String)values.get("env"));
							if((String)values.get("config1")!=null) {
								config.setConfig1((String)values.get("config1"));
							}
							if((String)values.get("config2")!=null) {
								config.setConfig1((String)values.get("config2"));
							}
							moduleConfigs.put(env, config);
						}
						mod.setModuleConfigs(moduleConfigs);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("getIntegrationModules()", e);
		}
		return modules;
	}

	@Override
	public IntegrationModule getByCode(String moduleCode) {
		return this.integrationModuleRepository.findByCode(moduleCode);
	}

	@Override
	public void createOrUpdateModule(String json) throws BusinessException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			@SuppressWarnings("rawtypes")
			Map object = mapper.readValue(json, Map.class);
			IntegrationModule module = integrationModulesLoader.loadModule(object);
			if(module!=null) {
				IntegrationModule m = this.getByCode(module.getCode());
				if(m!=null) {
					this.delete(m);	 	
				}
				this.save(module);
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		} 

	}

}
