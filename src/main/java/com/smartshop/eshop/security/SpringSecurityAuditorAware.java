package com.smartshop.eshop.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.smartshop.eshop.common.BusinessConstants;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

	@Override
	public String getCurrentAuditor() {
		String userName = SecurityUtils.getCurrentUserLogin();
		return userName != null ? userName : BusinessConstants.SYSTEM_ACCOUNT;
	}
}
