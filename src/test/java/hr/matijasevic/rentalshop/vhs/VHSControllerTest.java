package hr.matijasevic.rentalshop.vhs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VHSControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAll() throws Exception {
        Map<String,Object> body = new HashMap<>();
        body.put("username","admin");
        body.put("password","admin");

        MvcResult mvcResult = mockMvc.perform(post("/authentication/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        response = response.replace("{\"jwt\":\"", "");
        String token = response.replace("\"}", "");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/vhs")
                .with(csrf())
                .header("Authorization","Bearer " + token)

                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));

    }

    @Test
    void getVHSById() throws Exception {
        Map<String,Object> body = new HashMap<>();
        body.put("username","admin");
        body.put("password","admin");

        MvcResult mvcResult = mockMvc.perform(post("/authentication/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        System.out.println(response);
        response = response.replace("{\"jwt\":\"", "");
        String token = response.replace("\"}", "");

        Long TEST_ID = 5L;
        String TEST_NAME = " The Lion King";
        String TEST_RATING = "G";

        this.mockMvc.perform(get("/api/vhs/" + TEST_ID)
                .with(csrf())
                .header("Authorization","Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
                .andExpect(jsonPath("$.id").value(TEST_ID))
                .andExpect(jsonPath("$.name").value(TEST_NAME))
                .andExpect(jsonPath("$.rating").value(TEST_RATING));

    }

}