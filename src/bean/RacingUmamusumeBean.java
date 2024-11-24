package bean;

import java.sql.Date;

public class RacingUmamusumeBean {

	private final boolean isExclusive; // 排他的であるか
	private final Integer no; // 勝負服番号
	private final String name; // 名前
	private final String parameter; // パラメーター
	private final Date appeared; // 勝負服登録日
	private final Integer noB; // 図鑑番号

	public static RacingUmamusumeBean create(boolean isExclusive, Integer no, String name, String parameter, Date appeared, Integer noB) {
		if (no == null || noB == null) {
			throw new NullPointerException("noがnullです");
		}

		return new RacingUmamusumeBean(isExclusive, no, name, parameter, appeared, noB);
	}

	private RacingUmamusumeBean(boolean isExclusive, Integer no, String name, String parameter, Date appeared, Integer noB) {
		this.isExclusive = isExclusive;
		this.no = no;
		this.name = name;
		this.parameter = parameter;
		this.appeared = appeared;
		this.noB = noB;
	}

	public boolean isExclusive() {
		return isExclusive;
	}

	public Integer no() {
		return no;
	}

	public String name() {
		return name;
	}

	public String parameter() {
		return parameter;
	}

	public Date appeared() {
		return appeared;
	}

	public Integer noB() {
		return noB;
	}

}
