package com.iitkharagpur.interiitsports2.datas;

import java.util.Map;
import java.util.Objects;

public class Newsletter_data implements Comparable<Newsletter_data> {
	
	private String heading, body, link, timestamp, direct;
	
	public Newsletter_data(Map<String, Object> map){
		this.heading = Objects.requireNonNull(map.get("Heading")).toString();
		this.body = Objects.requireNonNull(map.get("Body")).toString();
		this.link = Objects.requireNonNull(map.get("Link")).toString();
		this.timestamp = Objects.requireNonNull(map.get("Time")).toString();
		this.direct = Objects.requireNonNull(map.get("Direct")).toString();
	}
	
	public String getHeading() {
		return heading;
	}
	
	public String getBody() {
		return body;
	}
	
	public String getLink() {
		return link;
	}
	
	private String getTimestamp() {
		return timestamp;
	}
	
	public String getDirect() {
		if (!this.direct.startsWith("http://") && !this.direct.startsWith("https://"))
			this.direct = "http://" + this.direct;
		return direct;
	}
	
	@Override
	public int compareTo(Newsletter_data newsletter_data) {
		return (this.getTimestamp().compareTo(newsletter_data.getTimestamp())<0 ? 1 :
			(this.getTimestamp().equals(newsletter_data.getTimestamp()) ? 0 : -1));
	}
}
