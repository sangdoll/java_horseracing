package horseracing.domain;

import java.util.Objects;

public class User {
	private final String userName;
	private final int balance;

	public User(String userName, int balance) {
		if (balance < 0) {
			throw new IllegalArgumentException("[ERROR] 잔고는 0 이상이어야 합니다.");
		}
		this.userName = userName;
		this.balance = balance;
	}

	public String getUserName() {
		return userName;
	}

	public int getBalance() {
		return balance;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User)o;
		return getBalance() == user.getBalance() && Objects.equals(getUserName(), user.getUserName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUserName(), getBalance());
	}
}
