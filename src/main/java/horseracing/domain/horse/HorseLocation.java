package horseracing.domain.horse;

import java.util.Objects;

public final class HorseLocation {
	private static final int MIN_NUMBER = 0;
	private static final int MAX_NUMBER = 9;
	private static final int MINIMUM_FOR_MOVE = 5;
	private static int MOVE_UNIT = 1;

	private final int location;

	private HorseLocation(int location) {
		if (location < 0) {
			throw new IllegalArgumentException("[ERROR] 위치값은 음수일 수 없습니다.");
		}
		this.location = location;
	}

	public static HorseLocation from(int location) {
		return new HorseLocation(location);
	}

	public int getLocation() {
		return location;
	}

	public HorseLocation increaseLocation(int number) {
		if (number < MIN_NUMBER || number > MAX_NUMBER) {
			throw new IllegalArgumentException("[ERROR] 숫자는 0부터 9까지만 가능합니다.");
		}
		if (number >= MINIMUM_FOR_MOVE) {
			return HorseLocation.from(location + MOVE_UNIT);
		}
		return HorseLocation.from(location);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		HorseLocation that = (HorseLocation)o;
		return getLocation() == that.getLocation();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getLocation());
	}
}
