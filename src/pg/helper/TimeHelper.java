package pg.helper;

import java.time.Duration;
import java.time.LocalTime;

/**
 * Created by gawa on 27.03.17.
 */
public final class TimeHelper {

	private TimeHelper() {}

	public static String durationInfo(LocalTime begin, LocalTime end) {
		long oneSecond = 1;
		long oneMinute = 60 * oneSecond;
		long oneHour = 60 * oneMinute;
		Duration duration = Duration.between(begin, end);
		if(duration.getSeconds() >= oneHour){
			return String.format("Czas trwania: %d[h].", duration.getSeconds() / oneHour);
		}
		if(duration.getSeconds() >= oneMinute){
			return String.format("Czas trwania: %d[m].", duration.getSeconds() / oneMinute);
		}
		return String.format("Czas trwania: %d[s].", duration.getSeconds());
	}

}
