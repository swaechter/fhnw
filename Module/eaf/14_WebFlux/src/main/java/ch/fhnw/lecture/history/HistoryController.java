package ch.fhnw.lecture.history;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class HistoryController {
	@Autowired
	private HistoryService historyService;

	@GetMapping(path = "/history")
	public Collection<LocationHistory> find(
			@RequestParam("startDate") @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = ISO.DATE) LocalDate endDate) throws InterruptedException {
		System.out.println(startDate);
		return historyService.find(startDate, endDate);
	}

	@GetMapping(path = "/historyStream", produces= {MediaType.APPLICATION_STREAM_JSON_VALUE, MediaType.TEXT_EVENT_STREAM_VALUE})
	public Flux<LocationHistory> findStream(
			@RequestParam("startDate") @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = ISO.DATE) LocalDate endDate) {
		return historyService.findFlux(startDate, endDate);
	}

}


/*
curl "http://localhost:8080/history?startDate=2018-12-01&endDate=2018-12-31"
curl "http://localhost:8080/history2?startDate=2018-12-01&endDate=2018-12-31"

curl http://localhost:8080/infinite

POST /echo HTTP/1.1
accept-encoding: gzip
user-agent: ReactorNetty/0.8.3.RELEASE
host: localhost:8080
accept: * / *
transfer-encoding: chunked
Content-Type: text/plain;charset=UTF-8


 */