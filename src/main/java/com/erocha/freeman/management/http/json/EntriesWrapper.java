package com.erocha.freeman.management.http.json;

import java.time.LocalDate;
import java.util.SortedSet;

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
public class EntriesWrapper {

	@Singular
	private SortedSet<UserEntry> entries;
	private LocalDate startDate;
	private LocalDate endDate;
}
