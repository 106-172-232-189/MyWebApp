package bean;

public class UmamusumeBean {

	private final boolean isExclusive; // 排他的であるか
	private final int umadexNo; // 図鑑番号
	private final String name; // 名前
	private final String parameter; // パラメーター
	private final int racingSuitNo; // 勝負服番号

	public static UmamusumeBean create(boolean isExclusive, int umadexNo, String name, String parameter, int racingSuitNo) {
		if (name == null) {
			throw new NullPointerException("名前がnullです");
		}

		if (umadexNo < 0 || racingSuitNo < 0) {
			throw new IllegalArgumentException("勝負服番号もしくは図鑑番号が0未満です");
		}

		return new UmamusumeBean(isExclusive, umadexNo, name, parameter, racingSuitNo);
	}

	private UmamusumeBean(boolean isExclusive, int umadexNo, String name, String parameter, int racingSuitNo) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.isExclusive = isExclusive;
		this.umadexNo = umadexNo;
		this.name = name;
		this.parameter = parameter;
		this.racingSuitNo = racingSuitNo;
	}

	public boolean isExclusive() {
		return isExclusive;
	}

	public int umadexNo() {
		return umadexNo;
	}

	public String name() {
		return name;
	}

	public String parameter() {
		return parameter;
	}

	public int racingSuitNo() {
		return racingSuitNo;
	}
}
