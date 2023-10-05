package horseracing.domain.util;

public final class LocationToUIParser {
	private static final String DASH = "-";

	private LocationToUIParser() {
	}

	public static String parse(int location) {
		if (location < 0) {
			throw new IllegalArgumentException("[ERROR] 위치값은 0 이상이어야 합니다.");
		}
		return DASH.repeat(location);
	}
}
