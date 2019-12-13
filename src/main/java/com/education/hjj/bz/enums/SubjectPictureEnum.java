package com.education.hjj.bz.enums;

public enum SubjectPictureEnum {

	CHINESE("语文", "Chinese.png"),
	MATH("数学", "mathematics.png"),
	ENGLISH("英语", "english.png"),
	PHYSICS("物理", "physics.png"),
	CHEMISTRY("化学", "chemistry.png"),
	BIOLOGY("生物", "biology.png"),
	OLYMPIAD("奥数", "olympiadMath.png"),
	GEOGRAPHY("地理", "geography.png"),
	POLITICS("政治", "politics.png"),
	HISTORY("历史", "history.png"),
	USERIDENTITY("高数", "advanced Math.png"),
	FLUTE("长笛", "flute.png"),
	VOCALMUSIC("声乐", "history.png"),
	ZITHER("古筝", "art.png"),
	ART("美术", "art.png"),
	JAPANESE("日语", "japanese.png"),
	GERMAN("德语", "german.png"),
	FRENCH("法语", "french.png"),
	KOREAN("韩语", "korean.png"),
	MOREBRANCH("更多", "moreBranch.png"),
	;

	private String code;

    private String value;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	SubjectPictureEnum(String code, String value) {
		this.value = value;
		this.code = code;
	}


}
