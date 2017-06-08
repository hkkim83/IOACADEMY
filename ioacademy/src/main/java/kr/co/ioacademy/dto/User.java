package kr.co.ioacademy.dto;

public class User {
	private String id;
	private String email;
	private String name;
	private String tel_no;
	private String tel_no1;
	private String tel_no2;
	private String tel_no3;
	private String email_yn;
	private String priority;
	private String join_date;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel_no() {
		return tel_no;
	}
	public void setTel_no(String tel_no) {
		this.tel_no = tel_no;
	}

	public String getTel_no1() {
		return tel_no1;
	}
	public void setTel_no1(String tel_no1) {
		this.tel_no1 = tel_no1;
	}
	public String getTel_no2() {
		return tel_no2;
	}
	public void setTel_no2(String tel_no2) {
		this.tel_no2 = tel_no2;
	}
	public String getTel_no3() {
		return tel_no3;
	}
	public void setTel_no3(String tel_no3) {
		this.tel_no3 = tel_no3;
	}
	public String getEmail_yn() {
		return email_yn;
	}
	public void setEmail_yn(String email_yn) {
		this.email_yn = email_yn;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
}
