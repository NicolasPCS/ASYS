package com.example.asys.Interfaces;

import com.google.cloud.dialogflow.v2.DetectIntentResponse;

public interface BotReply {
    void callback(DetectIntentResponse returnResponse);
}
