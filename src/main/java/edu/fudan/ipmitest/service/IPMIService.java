package edu.fudan.ipmitest.service;


import edu.fudan.ipmitest.entity.ipmi.SensorRec;

import java.util.Map;

public interface IPMIService {
  /**
   * 获取传感器信息
   * @param hostname ip地址或者主机域名
   * @param username BMC用户名
   * @param password BMC密码
   * @return
   */
  Map<Integer, SensorRec> getSensorsData (String hostname, String username, String password, int port);

  /**
   * 获取开、关机状态
   * @param hostname ip地址或者主机域名
   * @param username BMC用户名
   * @param password BMC密码
   * @return
   */
  boolean getPowerState(String hostname, String username, String password, int port);

  /**
   * 远程开机
   * @param hostname
   * @param username
   * @param password
   * @return
   */
  boolean powerOn(String hostname, String username, String password, int port);

  /**
   * 远程关机
   * @param hostname
   * @param username
   * @param password
   * @return
   */
  boolean powerOff(String hostname, String username, String password, int port);
}
