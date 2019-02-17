package ch.fhnw.lecture.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor // necessary for JSON serialization
public class Tick {
	long tick;
}
