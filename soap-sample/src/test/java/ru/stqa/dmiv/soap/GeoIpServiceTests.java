package ru.stqa.dmiv.soap;

import com.lavasoft.GeoIPService;
import com.lavasoft.GetIpLocation;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GeoIpServiceTests {

  @Test
  public void testMyIp(){

    String ipLocation = new GeoIPService().getGeoIPServiceSoap12().getIpLocation20("178.49.83.2");
//            .getIpLocation("178.49.83.2");

    Assert.assertEquals(ipLocation, "Бердск");
  }
}
