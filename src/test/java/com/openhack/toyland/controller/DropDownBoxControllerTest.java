package com.openhack.toyland.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.openhack.toyland.IntegrationTest;

public class DropDownBoxControllerTest extends IntegrationTest {

    @DisplayName("drop down box 용 skill 목록 get 테스트")
    @Test
    void getSkill() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/api/skills")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        String actualResponse = "{\n"
            + "    \"data\": [\n"
            + "        {\n"
            + "            \"id\": 1,\n"
            + "            \"name\": \"java\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 2,\n"
            + "            \"name\": \"javascript\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 3,\n"
            + "            \"name\": \"typescript\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 4,\n"
            + "            \"name\": \"python\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 5,\n"
            + "            \"name\": \"c++\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 6,\n"
            + "            \"name\": \"c#\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 7,\n"
            + "            \"name\": \"c\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 8,\n"
            + "            \"name\": \"golang\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 9,\n"
            + "            \"name\": \"kotlin\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 10,\n"
            + "            \"name\": \"swift\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 11,\n"
            + "            \"name\": \"dart\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 12,\n"
            + "            \"name\": \"php\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 13,\n"
            + "            \"name\": \"r\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 14,\n"
            + "            \"name\": \"ruby\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 15,\n"
            + "            \"name\": \"rust\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 16,\n"
            + "            \"name\": \"scratch\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 17,\n"
            + "            \"name\": \"spring\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 18,\n"
            + "            \"name\": \"spring boot\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 19,\n"
            + "            \"name\": \"nodejs\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 20,\n"
            + "            \"name\": \"django\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 21,\n"
            + "            \"name\": \"flask\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 22,\n"
            + "            \"name\": \".net\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 23,\n"
            + "            \"name\": \"ruby on rails\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 24,\n"
            + "            \"name\": \"laravel\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 25,\n"
            + "            \"name\": \"rocket\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 26,\n"
            + "            \"name\": \"react\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 27,\n"
            + "            \"name\": \"react native\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 28,\n"
            + "            \"name\": \"flutter\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 29,\n"
            + "            \"name\": \"thymeleaf\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 30,\n"
            + "            \"name\": \"docker\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 31,\n"
            + "            \"name\": \"docker-compose\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 32,\n"
            + "            \"name\": \"kubernetes\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 33,\n"
            + "            \"name\": \"grafana\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 34,\n"
            + "            \"name\": \"prometheus\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 35,\n"
            + "            \"name\": \"kibana\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 36,\n"
            + "            \"name\": \"elastic search\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 37,\n"
            + "            \"name\": \"aws\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 38,\n"
            + "            \"name\": \"gcp\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 39,\n"
            + "            \"name\": \"azure\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 40,\n"
            + "            \"name\": \"ncp\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 41,\n"
            + "            \"name\": \"kakao i could\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 42,\n"
            + "            \"name\": \"jenkins\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 43,\n"
            + "            \"name\": \"travis ci\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 44,\n"
            + "            \"name\": \"github\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 45,\n"
            + "            \"name\": \"vue\"\n"
            + "        }\n"
            + "    ]\n"
            + "}";
        JSONAssert.assertEquals(actualResponse, mvcResult.getResponse().getContentAsString(), true);
    }

    @DisplayName("drop down box 용 organization 목록 get 테스트")
    @Test
    void getOrganization() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/api/organizations")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        String actualResponse = "{\n"
            + "    \"reposponse\": [\n"
            + "        {\n"
            + "            \"id\": 1,\n"
            + "            \"name\": \"SW중심대학 공동해커톤\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 2,\n"
            + "            \"name\": \"YAPP\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 3,\n"
            + "            \"name\": \"SOPT\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 4,\n"
            + "            \"name\": \"DDD\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 5,\n"
            + "            \"name\": \"디프만\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 6,\n"
            + "            \"name\": \"Mash-up\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 7,\n"
            + "            \"name\": \"Fun.d\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 8,\n"
            + "            \"name\": \"프로그라피\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 9,\n"
            + "            \"name\": \"NEXTERS\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 10,\n"
            + "            \"name\": \"피로그래밍\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 11,\n"
            + "            \"name\": \"DnD\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 12,\n"
            + "            \"name\": \"네이버 핵데이\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 13,\n"
            + "            \"name\": \"우아한테크캠프\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 14,\n"
            + "            \"name\": \"우아한테크코스\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 15,\n"
            + "            \"name\": \"멋쟁이 사자처럼\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 16,\n"
            + "            \"name\": \"수업 프로젝트\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 17,\n"
            + "            \"name\": \"부트캠프 결과물\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 18,\n"
            + "            \"name\": \"해커톤\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 19,\n"
            + "            \"name\": \"졸업 작품\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 20,\n"
            + "            \"name\": \"지인들과 함께\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 21,\n"
            + "            \"name\": \"개인 개발\"\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": 22,\n"
            + "            \"name\": \"그 외\"\n"
            + "        }\n"
            + "    ]\n"
            + "}";

        JSONAssert.assertEquals(actualResponse, mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8),
            true);
    }

    @DisplayName("drop down box 용 category 목록 get 테스트")
    @Test
    void getCategory() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/api/categories")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        String actualResponse = "{\n"
            + "    \"reposponse\": [\n"
            + "        \"WEB\",\n"
            + "        \"APP\",\n"
            + "        \"IOS\",\n"
            + "        \"ANDROID\",\n"
            + "        \"AI\",\n"
            + "        \"ML\",\n"
            + "        \"BIG_DATA\"\n"
            + "    ]\n"
            + "}";

        JSONAssert.assertEquals(actualResponse, mvcResult.getResponse().getContentAsString(), true);
    }

    @DisplayName("drop down box 용 period 목록 get 테스트")
    @Test
    void getPeriod() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/api/periods")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        String actualResponse = "{\n"
            + "    \"reposponse\": [\n"
            + "        \"LESS_THAN_A_DAY\",\n"
            + "        \"LESS_THAN_THREE_DAYS\",\n"
            + "        \"LESS_THAN_A_WEEK\",\n"
            + "        \"LESS_THAN_TWO_WEEKS\",\n"
            + "        \"LESS_THAN_A_MONTH\",\n"
            + "        \"LESS_THAN_THREE_MONTHS\",\n"
            + "        \"LESS_THAN_SIX_MONTHS\",\n"
            + "        \"LESS_THAN_A_YEAR\",\n"
            + "        \"MORE_THAN_A_YEAR\"\n"
            + "    ]\n"
            + "}";

        JSONAssert.assertEquals(actualResponse, mvcResult.getResponse().getContentAsString(), true);
    }
}
