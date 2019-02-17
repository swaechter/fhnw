package ch.fhnw.lecture.history;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

@Component
public class HistoryService {
	
	private static final int DELAY_PER_ITEM_MS = 100;
	
	private List<LocationHistory> queryResult(LocalDate startDate, LocalDate endDate) {
		List<LocationHistory> result = new ArrayList<>();
		for(LocalDate d = startDate; d.compareTo(endDate) < 0; d = d.plusDays(1)) {
			result.add(new LocationHistory(d));
		}
		return result;
	}

	public Collection<LocationHistory> find(LocalDate startDate, LocalDate endDate) throws InterruptedException {
		List<LocationHistory> history = queryResult(startDate, endDate);
		Thread.sleep(history.size() * DELAY_PER_ITEM_MS);
		return history;
	}

	public Flux<LocationHistory> findFlux(LocalDate startDate, LocalDate endDate) {
		return Flux.fromIterable(queryResult(startDate, endDate)).delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS));
	}

}
