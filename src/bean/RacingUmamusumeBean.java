package bean;

import java.sql.Date;

public class RacingUmamusumeBean {

	private final boolean isExclusive; // 排他的であるか
	private final int racingSuitNo; // 勝負服番号
	private final String name; // 名前
	private final String parameter; // パラメーター
	private final Date appeared; // 勝負服登録日
	private final int umadexNo; // 図鑑番号

	public static RacingUmamusumeBean create(boolean isExclusive, int racingSuitNo, String name, String parameter, Date appeared, int umadexNo) {
		if (name == null) {
			throw new NullPointerException("名前がnullです");
		}

		if (racingSuitNo < 0 || umadexNo < 0) {
			throw new IllegalArgumentException("勝負服番号もしくは図鑑番号が0未満です");
		}

		return new RacingUmamusumeBean(isExclusive, racingSuitNo, name, parameter, appeared, umadexNo);
	}

	private RacingUmamusumeBean(boolean isExclusive, int racingSuitNo, String name, String parameter, Date appeared, int umadexNo) {
		this.isExclusive = isExclusive;
		this.racingSuitNo = racingSuitNo;
		this.name = name;
		this.parameter = parameter;
		this.appeared = appeared;
		this.umadexNo = umadexNo;
	}

	public boolean isExclusive() {
		return isExclusive;
	}

	public int racingSuitNo() {
		return racingSuitNo;
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

	public int umadexNo() {
		return umadexNo;
	}

}
