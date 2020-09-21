package edu.fudan.ipmitest.controller;

import edu.fudan.ipmitest.ResponseBase;
import edu.fudan.ipmitest.entity.ipmi.SensorRec;
import edu.fudan.ipmitest.service.IPMIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class IPMIController extends BaseController{
  final IPMIService ipmiService;

  @Autowired
  public IPMIController(IPMIService ipmiService) {
    this.ipmiService = ipmiService;
  }

  @GetMapping(value = "/ipmi/powerstate")
  public ResponseBase<String> getPowerState(@RequestParam("hostname") String hostname,
                                            @RequestParam("username") String username,
                                            @RequestParam("password") String password,
                                            @RequestParam("port") int port) {
    boolean powerState = ipmiService.getPowerState(hostname, username, password, port);

    ResponseBase<String> response = new ResponseBase<>();
    response.setCode(200);
    response.setErrorCode(null);
    response.setMessage("ok");
    response.setTimestamp(System.currentTimeMillis());
    response.setResult(powerState ? "up" : "down");
    return response;
  }

  @GetMapping(value = "/ipmi/poweron")
  public ResponseBase<String> powerOn(@RequestParam("hostname") String hostname,
                                      @RequestParam("username") String username,
                                      @RequestParam("password") String password,
                                      @RequestParam("port") int port) {
    boolean powerState = ipmiService.powerOn(hostname, username, password, port);

    ResponseBase<String> response = new ResponseBase<>();
    response.setCode(200);
    response.setErrorCode(null);
    response.setMessage("ok");
    response.setTimestamp(System.currentTimeMillis());
    response.setResult(powerState ? "up" : "down");
    return response;
  }

  @GetMapping(value = "/ipmi/poweroff")
  public ResponseBase<String> powerOff(@RequestParam("hostname") String hostname,
                                       @RequestParam("username") String username,
                                       @RequestParam("password") String password,
                                       @RequestParam("port") int port) {
    boolean powerState = ipmiService.powerOff(hostname, username, password, port);

    ResponseBase<String> response = new ResponseBase<>();
    response.setCode(200);
    response.setErrorCode(null);
    response.setMessage("ok");
    response.setTimestamp(System.currentTimeMillis());
    response.setResult(powerState ? "up" : "down");
    return response;
  }

  @GetMapping(value = "/ipmi/getData")
  public ResponseBase<Map<Integer, SensorRec>> getData(@RequestParam("hostname") String hostname,
                                                       @RequestParam("username") String username,
                                                       @RequestParam("password") String password,
                                                       @RequestParam("port") int port) {
    Map<Integer, SensorRec> sensorsData = ipmiService.getSensorsData(hostname, username, password, port);
    System.out.println(hostname +" --- " + username + " --- " + password + " --- " + port);
    return success(sensorsData);
  }
}
