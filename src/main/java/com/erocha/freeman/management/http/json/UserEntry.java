package com.erocha.freeman.management.http.json;

import java.util.ArrayList;
import java.util.List;

import com.erocha.freeman.time.http.json.DayAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntry implements Comparable<UserEntry> {

	private String userId;
	private String name;
	private String avatar;
	@Singular
	private List<DayAmount> days;
	private boolean selected;

	public void addDaysAmount(DayAmount dayAmount) {
		if (this.days == null) {
			this.days = new ArrayList<>();
		}
		this.days.add(dayAmount);
	}

	@Override
	public int compareTo(UserEntry o) {
		return getName().compareTo(o.getName());
	}
}
