package it.unipv.sfw.model.request;

public class Request {

	private String description, id_s, id_v;

	private int id_r, id_c;

	public Request(int id_r, String description, String id_s, int id_c, String id_v) {
		this.id_r = id_r;
		this.description = description;
		this.id_s = id_s;
		this.id_c = id_c;
		this.id_v = id_v;

	}

	public int getId_r() {
		return id_r;
	}

	public void setId_r(int id_r) {
		this.id_r = id_r;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId_s() {
		return id_s;
	}

	public void setId_s(String id_s) {
		this.id_s = id_s;
	}

	public int getId_c() {
		return id_c;
	}

	public void setId_c(int id_c) {
		this.id_c = id_c;
	}

	public String getId_v() {
		return id_v;
	}

	public void setId_v(String id_v) {
		this.id_v = id_v;
	}

}
