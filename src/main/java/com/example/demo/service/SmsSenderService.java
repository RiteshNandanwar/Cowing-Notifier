package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.entity.SmsResponse;
import com.example.demo.response.VaccineInfo;

@Service
public class SmsSenderService {

	public void sendSms(String mobile, VaccineInfo vaccineInfo) {

		WebClient webClient = WebClient.create();

		String message = getFormattedMessage(vaccineInfo);
		System.out.println(message);
		SmsResponse response = webClient.get().uri(
				"https://www.fast2sms.com/dev/bulkV2?authorization=VOKpody4vmRJr6t7YzGfQ8FAlIk3gqNPeHxDMaZ0cE9sUi2hbWcGKyAHspR6CwDz5eMjqrIg4YS0VUEZ&route=v3&sender_id=COWIN-Notifier&message={message}&language=english&flash=0&numbers={,mobile}",
				message, mobile).retrieve().bodyToMono(SmsResponse.class).block();
		System.out.println(response.getMessage());
	}

	private String getFormattedMessage(VaccineInfo vaccineInfo) {
		String headline = "Vaccine%20Available";
		String total = vaccineInfo.getAvailable_capacity().toString();
		String centerName = vaccineInfo.getCenterName().replaceAll(" ", "%20");
		String dose1 = vaccineInfo.getDose_1().toString();
		String dose2 = vaccineInfo.getDose_2().toString();
		String message = "%0ACenter%20Name%20=%20" + centerName + "%0ATotal%20Capacity%20=%20" + total
				+ "%0ADose%201%20=%20" + dose1 + "%0ADose%202%20=%20" + dose2;
		return headline + message;
	}
}
