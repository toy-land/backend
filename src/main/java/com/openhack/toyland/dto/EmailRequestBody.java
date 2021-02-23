package com.openhack.toyland.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailRequestBody {

    private String senderAddress = "no_reply@toy-land.co.kr";
    private String title = "[토이랜드] 프로젝트 사용 불가능 기간 알림 서비스";
    private String body = "당신의 토이 프로젝트가 ${SLEEP_DAYS}일 동안 잠자고 있어요~~ㅠㅠ";
    private List<EmailParticipant> recipients;
    private boolean individual = true;
    private boolean advertising = false;

    public EmailRequestBody(List<EmailParticipant> recipients) {
        this.recipients = recipients;
    }
}
