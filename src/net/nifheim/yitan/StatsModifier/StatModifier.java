package net.nifheim.yitan.StatsModifier;

public class StatModifier {
	int id;
	StatType statType;
	StatModifierType smt;
	double value;
	Long duration;
	Long startTime;
	Long endTime;
	String description;
	public StatModifier(int id,StatType statType, StatModifierType smt, double value, Long duration) {
		super();
		this.id = id;
		this.statType = statType;
		this.smt = smt;
		this.value = value;
		this.duration = duration;
		this.startTime = System.currentTimeMillis();
		this.description = "";
		this.endTime = startTime+duration;
	}
	public StatModifier(int id,StatType statType, StatModifierType smt, double value, Long duration,String description) {
		super();
		this.id = id;
		this.statType = statType;
		this.smt = smt;
		this.value = value;
		this.duration = duration;
		this.startTime = System.currentTimeMillis();
		this.description = description;
		this.endTime = startTime+duration;
	}
	
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public StatType getStatType() {
		return statType;
	}
	public void setStatType(StatType statType) {
		this.statType = statType;
	}
	public StatModifierType getSmt() {
		return smt;
	}
	public void setSmt(StatModifierType smt) {
		this.smt = smt;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	

}
