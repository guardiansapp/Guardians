package com.guardians.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public enum Profile {

	ADMIN(1, "ROLE_ADMIN"),
	DEFAULT(2, "ROLE_DEFAULT");
	
	private int cod;
	private String desc;
	
	public static Profile toEnum(int cod) {
		for(int i = 0; i < Profile.values().length; i ++) {
			if(Profile.values()[i].getCod() == cod) {
				return Profile.values()[i];
			}
		}
		return null;
	}
	
}
