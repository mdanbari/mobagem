package net.h2.web.mob.profile;

import net.h2.web.core.base.exception.BaseServerBusinessException;
import net.h2.web.core.base.shared.service.IBaseService;

public interface IProfileService extends IBaseService<ProfileDTO, Long> {	

	ProfileDTO findProfileByMobile(Long mobile) throws BaseServerBusinessException;

	ProfileDTO findByMobileAndConfimCode(Long mobilePhone, Integer confirmCode) throws BaseServerBusinessException;

}
