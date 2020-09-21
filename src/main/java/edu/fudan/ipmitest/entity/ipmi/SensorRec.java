package edu.fudan.ipmitest.entity.ipmi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SensorRec {
  private String name;
  private String value;
  private String unit;
  private String rate;

  /**
   * 服务器传感器信息
   * @param name 传感器名称
   * @param value 值
   * @param unit 单位
   * @param rate 频率单位
   */
  public SensorRec(String name, String value, String unit, String rate) {
    this.name = name;
    this.value = value;
    this.unit = unit;
    this.rate = rate;
  }

  public SensorRec() {}
}
