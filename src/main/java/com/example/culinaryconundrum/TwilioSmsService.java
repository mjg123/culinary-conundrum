package com.example.culinaryconundrum;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioSmsService {

    public TwilioSmsService(@Value("${twilio.api-key}") String apiKey,
                            @Value("${twilio.api-secret}") String apiSecret) {
        Twilio.init(apiKey, apiSecret);
    }

    public void send(String userNumber, String botNumber, String text) {
        Message.creator(new PhoneNumber(userNumber),
                        new PhoneNumber(botNumber),
                        text)
                .create();
    }
}
