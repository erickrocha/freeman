package com.erocha.freeman.app.http.mapper;

import com.erocha.freeman.app.http.json.ProfileTO;
import com.erocha.freeman.commons.mapper.Mapper;
import com.erocha.freeman.security.domains.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper implements Mapper<Profile, ProfileTO> {

	@Override
	public ProfileTO convertTransferObject(Profile profile) {
		return new ProfileTO(profile.name(), profile.getLabel());
	}

	@Override
	public Profile convertEntity(ProfileTO profileTO) {
		return Profile.valueOf(profileTO.getValue());
	}
}
