package horseracing.domain.horse;

import java.util.Objects;

public final class HorseName {
	private final String name;

	private HorseName(String name) {
		if (!name.matches("[가-힣]{5}")) {
			throw new IllegalArgumentException("[ERROR] 말의 이름은 5글자여야 합니다.");
		}
		this.name = name;
	}

	public static HorseName from(String name) {
		return new HorseName(name);
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		HorseName horseName = (HorseName)o;
		return Objects.equals(getName(), horseName.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}
}
