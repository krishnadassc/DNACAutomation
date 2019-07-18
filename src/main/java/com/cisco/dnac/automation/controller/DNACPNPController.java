package com.cisco.dnac.automation.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.dnac.common.CommonUrl;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(CommonUrl.PNP_URL)
public class DNACPNPController {

	  @RequestMapping(value = CommonUrl.PNP_AUTOMATE_URL, method = RequestMethod.POST,
	          consumes = "application/json", produces = "application/json")
	  @ResponseBody
	  public String doPNP(@ApiParam @RequestBody String payload) {
	    try {

	    } catch (Exception e) {

	    }
	    return null;
	  }
}
