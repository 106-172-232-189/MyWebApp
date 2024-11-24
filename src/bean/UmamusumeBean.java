package bean;

public class UmamusumeBean {

	private final boolean isExclusive; // 排他的であるか
	private final Integer noA; // 図鑑番号
	private final String name; // 名前
	private final String parameter; // パラメーター
	private final Integer noB; // 勝負服番号

	public static UmamusumeBean create(boolean isExclusive, Integer noA, String name, String parameter, Integer noB) {
		if (noA == null || noB == null) {
			throw new NullPointerException("noがnullです");
		}

		return new UmamusumeBean(isExclusive, noA, name, parameter, noB);
	}

	private UmamusumeBean(boolean isExclusive, Integer no, String name, String parameter, Integer noB) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.isExclusive = isExclusive;
		this.noA = no;
		this.name = name;
		this.parameter = parameter;
		this.noB = noB;
	}

	public boolean isExclusive() {
		return isExclusive;
	}

	public Integer noA() {
		return noA;
	}

	public String name() {
		return name;
	}

	public String parameter() {
		return parameter;
	}

	public Integer noB() {
		return noB;
	}
}
