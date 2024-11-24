package bean;

import java.sql.Date;

public class RacingUmamusumeBean {

	private final Integer no; // 勝負服番号
	private final String name; // 名前
	private final String parameter; // パラメーター
	private final Date appeared; // 勝負服登録日

	public static RacingUmamusumeBean create(Integer no, String name, String parameter, Date appeared) {
		if (no == null) {
			throw new NullPointerException("noがnullです");
		}

		return new RacingUmamusumeBean(no, name, parameter, appeared);
	}

	private RacingUmamusumeBean(Integer no, String name, String parameter, Date appeared) {
		this.no = no;
		this.name = name;
		this.parameter = parameter;
		this.appeared = appeared;
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

}
