package edu.fudan.ipmitest.service.impl;

import edu.fudan.ipmitest.entity.ipmi.SensorRec;
import edu.fudan.ipmitest.service.IPMIService;
import edu.fudan.ipmitest.utils.ipmi.ChassisControlRunner;
import edu.fudan.ipmitest.utils.ipmi.GetAllSensorReadingsRunner;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IPMIServiceImpl implements IPMIService {

  @Override
  public Map<Integer, SensorRec> getSensorsData(String hostname, String username, String password, int port) {
    Map<Integer, SensorRec> allSensorsData =
            GetAllSensorReadingsRunner.getAllSensorsData(hostname, username, password, port);
    if (allSensorsData == null) {
      throw new RuntimeException("未获取到传感器信息！");
    }
    return allSensorsData;
  }

  @Override
  public boolean getPowerState(String hostname, String username, String password, int port) {
    try {
      return ChassisControlRunner.getChassisPowerState(hostname, username, password, port);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean powerOn(String hostname, String username, String password, int port) {
    try {
      return ChassisControlRunner.powerOn(hostname, username, password, port);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean powerOff(String hostname, String username, String password, int port) {
    try {
      return ChassisControlRunner.powerOff(hostname, username, password, port);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}
