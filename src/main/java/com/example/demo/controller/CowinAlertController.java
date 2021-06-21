package com.example.demo.controller;

import java.time.Duration;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Vaccine;
import com.example.demo.response.VaccineInfo;
import com.example.demo.service.CowinService;
import com.example.demo.service.SmsSenderService;
import com.example.demo.util.CowinConstants;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
public class CowinAlertController {

	@Autowired
	private CowinService cowinService;

	@Autowired
	private SmsSenderService smsSenderService;

	@GetMapping(value = "/cowinUpdate/notifyme/{pincode}/{date}/{mobile}")
	public ResponseEntity<Flux<String>> getUpdateBySms(@PathVariable String pincode, @PathVariable String date,
			@PathVariable String mobile) {

		Vaccine vaccine = null;
		try {
			vaccine = cowinService.getVaccineUpdate(pincode, date);
		} catch (Exception e) {
			System.out.println("Exception While Getting Update");
		}

		Stream<String> msg = null;

		if (CowinConstants.VACCINE_AVAILABLE.equalsIgnoreCase(vaccine.getStatus())) {
			msg = Stream.generate(() -> "Vaccine Available     |     ");
			try {
				if(!CollectionUtils.isEmpty(vaccine.getVaccineInfo())) {
					for(VaccineInfo vaccineInfo : vaccine.getVaccineInfo()) {
						smsSenderService.sendSms(mobile, vaccineInfo);
					}
				}
			
			System.out.println("Vaccine Available :: SMS functionality executed");
			}catch(Exception e){
				System.out.println("Exception at SMS sending");
				e.printStackTrace();
			}
		} else
			msg = Stream.generate(() -> "Vaccine Not Available     |     ");

		Flux<String> fromStream = Flux.fromStream(msg);

		Flux<Long> interval = Flux.interval(Duration.ofSeconds(10));

		Flux<Tuple2<Long, String>> zip = Flux.zip(interval, fromStream);

		Flux<String> map = zip.map(Tuple2::getT2);

		ResponseEntity<Flux<String>> response = new ResponseEntity<Flux<String>>(map, HttpStatus.OK);

		return response;

	}

	@GetMapping(value = "/cowinUpdate/status/{pincode}/{date}")
	public String getUpdate(@PathVariable String pincode, @PathVariable String date) {
		Vaccine vaccine = null;
		try {
			vaccine = cowinService.getVaccineUpdate(pincode, date);
		} catch (Exception e) {
			System.out.println("Exception While Getting Update");
		}

		if (CowinConstants.VACCINE_AVAILABLE.equalsIgnoreCase(vaccine.getStatus()))
			return "Vaccine Available";
		else
			return "Vaccine Not Available";
	}
}
