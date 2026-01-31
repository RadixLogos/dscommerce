package com.devsuperior.dscommerce.controllers.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class TokenUtil {
    @Value("${security.client-secret}")
    private String clientSecret;
    @Value("${security.client-id}")
    private String clientId;

    public String getAccessToken(MockMvc mockMvc,String username,String password) throws Exception{
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client-id",clientId);
        params.add("username",username);
        params.add("password",password);
        params.add("grant_type","password");

        ResultActions result = mockMvc.perform(post("/oauth2/token")
                .params(params)
                .with(httpBasic(clientId,clientSecret))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8")).andDo(MockMvcResultHandlers.print());
        String resultString = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    };
}
