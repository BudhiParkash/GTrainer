package com.skywalkers.gtrainer.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;

import in.aabhasjindal.otptextview.OtpTextView;


public class OtpReciver extends BroadcastReceiver {

    private static OtpTextView otp_EditText;
    public void setOtpEditText(OtpTextView otpEditText){
        OtpReciver.otp_EditText = otpEditText;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        try
        {
        SmsMessage[] smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for (SmsMessage smsMessage : smsMessages) {
            String message_body = smsMessage.getMessageBody();

            String getOtp = message_body.split("is ")[1];
            otp_EditText.setOTP(getOtp);
        }
        }catch (Exception e){}

    }
}
