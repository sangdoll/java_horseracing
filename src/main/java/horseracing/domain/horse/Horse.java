package horseracing.domain.horse;

import java.util.Objects;

public final class Horse {
	private final HorseName horseName;
	private HorseLocation horseLocation;

	private Horse(String name) {
		this(name, 0);
	}

	private Horse(String name, int location) {
		this.horseName = HorseName.from(name);
		this.horseLocation = HorseLocation.from(location);
	}

	public static Horse getFromName(String name) {
		return new Horse(name, 0);
	}

	public static Horse getFromNameAndLocation(String name, int location) {
		return new Horse(name, location);
	}

	public void move(int number) {
		this.horseLocation = horseLocation.increaseLocation(number);
	}

	public String getName() {
		return horseName.getName();
	}

	public int getLocation() {
		return horseLocation.getLocation();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Horse horse = (Horse)o;
		return Objects.equals(horseName, horse.horseName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(horseName);
	}
}
