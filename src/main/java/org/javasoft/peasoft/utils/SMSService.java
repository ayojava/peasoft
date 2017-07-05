/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.peasoft.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import lombok.extern.slf4j.Slf4j;
import org.javasoft.peasoft.entity.data.SMSData;
import org.javasoft.peasoft.entity.settings.SMSSettings;

/**
 *
 * @author ayojava
 */
@Slf4j
public class SMSService {

    private final SMSSettings smsSettings;

    public SMSService(SMSSettings smsSettings) {
        this.smsSettings = smsSettings;
    }

    public String checkCreditBalance() throws Exception {
        String urlString = smsSettings.getSource() + ":" + smsSettings.getPort() + "/CreditCheck/checkcredits?";
        URL destinationURL = new URL(urlString);
        HttpURLConnection httpConnection = (HttpURLConnection) destinationURL.openConnection();
        httpConnection.setRequestMethod("POST");
        httpConnection.setDoInput(true);
        httpConnection.setDoOutput(true);
        httpConnection.setUseCaches(false);

        String encodedMessage = buildURLCreditBalance();
        String dataBuffer,output="";
        
        DataOutputStream dataStreamToServer = new DataOutputStream(httpConnection.getOutputStream());
        dataStreamToServer.writeBytes(encodedMessage);
        dataStreamToServer.flush();
        dataStreamToServer.close();
        
        BufferedReader dataStreamFromUrl = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
        while ((dataBuffer = dataStreamFromUrl.readLine()) != null) {
            output += dataBuffer;
        }
        dataStreamFromUrl.close();
        
        return output;
    }

    public String sendSMSMessage(SMSData smsDATA) throws Exception {
       
        String urlString = smsSettings.getSource() + ":" + smsSettings.getPort() + "/bulksms/bulksms?";
        URL destinationURL = new URL(urlString);
        HttpURLConnection httpConnection = (HttpURLConnection) destinationURL.openConnection();
        httpConnection.setRequestMethod("POST");
        httpConnection.setDoInput(true);
        httpConnection.setDoOutput(true);
        httpConnection.setUseCaches(false);

        String encodedMessage = buildURLMessage(smsDATA);
        
        String dataBuffer,output="";
        
        DataOutputStream dataStreamToServer = new DataOutputStream(httpConnection.getOutputStream());
        dataStreamToServer.writeBytes(encodedMessage);
        dataStreamToServer.flush();
        dataStreamToServer.close();
        
        BufferedReader dataStreamFromUrl = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
        while ((dataBuffer = dataStreamFromUrl.readLine()) != null) {
            output += dataBuffer;
        }
        dataStreamFromUrl.close();
        
        return output;
    }

    private String buildURLMessage(SMSData smsDATA) throws Exception {
        StringBuilder urlEncodeString = new StringBuilder();

        urlEncodeString = urlEncodeString.append("username=").append(URLEncoder.encode(smsSettings.getUsername(), "UTF-8"))
                .append("&password=").append(URLEncoder.encode(smsSettings.getPassword(), "UTF-8"))
                .append("&type=").append(URLEncoder.encode(smsSettings.getType(), "UTF-8"))
                .append("&dlr=").append(URLEncoder.encode(smsSettings.isDlr() ? "1" : "0", "UTF-8"))
                .append("&destination=").append(URLEncoder.encode(smsDATA.getRecipientPhoneNo(), "UTF-8"))
                .append("&source=").append(URLEncoder.encode("PEAFOUNDATN", "UTF-8"))
                .append("&message=").append(URLEncoder.encode(smsDATA.getMessage(), "UTF-8"));

        return urlEncodeString.toString();
    }

    private String buildURLCreditBalance() throws Exception {
        StringBuilder urlEncodeString = new StringBuilder();

        urlEncodeString = urlEncodeString.append("username=").append(URLEncoder.encode(smsSettings.getUsername(), "UTF-8"))
                .append("&password=").append(URLEncoder.encode(smsSettings.getPassword(), "UTF-8"));

        return urlEncodeString.toString();
    }

}
