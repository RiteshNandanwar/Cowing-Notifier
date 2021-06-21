package com.example.demo.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.entity.Center;
import com.example.demo.entity.Root;
import com.example.demo.entity.Session;
import com.example.demo.entity.Vaccine;
import com.example.demo.response.VaccineInfo;
import com.example.demo.util.CowinConstants;

@Service
public class CowinService {
	
	private static String status;

	private Root getCenters(String pincode, String date) {

		WebClient webClient = WebClient.create();

		Root response = webClient.get().uri(
				"https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode={pcd}&date={dt}",
				pincode, date)
				.retrieve()
				.bodyToMono(Root.class)
				.block();

		return response;
	}

	public Vaccine getVaccineUpdate(String pincode, String date) {
		
		Vaccine vaccine = new Vaccine();
		status = CowinConstants.VACCINE_NOT_AVAILABLE;
		vaccine.setStatus(status);
		
		Root root = getCenters(pincode, date);
		
		List<VaccineInfo> vaccineInfoList = new ArrayList<>();
		for(Center center : root.getCenters()) {
			Session session = center.getSessions().get(0);
			
			VaccineInfo vaccineInfo = new VaccineInfo(); 
			
			if(session.getAvailable_capacity() != 0) {
				status = CowinConstants.VACCINE_AVAILABLE;
				vaccineInfo.setAvailable_capacity(session.getAvailable_capacity());
				vaccineInfo.setCenterName(center.getName());
				vaccineInfo.setDose_1(session.getAvailable_capacity_dose1());				
				vaccineInfo.setDose_2(session.getAvailable_capacity_dose2());	
				vaccineInfoList.add(vaccineInfo);
			}
			if(!CollectionUtils.isEmpty(vaccineInfoList))
				vaccine.setVaccineInfo(vaccineInfoList);
			vaccine.setStatus(status);
		}
		
		return vaccine;
		
//		List<Session> sessions = root.getCenters().get(0).getSessions();
//		
//		//check vaccine available or not
//			if(sessions.get(0).getAvailable_capacity() != 0) {
//				status = CowinConstants.VACCINE_AVAILABLE;
//			}
//							
//		int available_capacity = 10;
//		
//		System.out.println("Vaccine Available :"+available_capacity);
//		
//		return status;
	}
}
