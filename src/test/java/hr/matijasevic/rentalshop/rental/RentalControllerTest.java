package hr.matijasevic.rentalshop.rental;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class RentalControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllRentals() throws Exception {
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

        mockMvc.perform(MockMvcRequestBuilders.get("/api/rental")
                .with(csrf())
                .header("Authorization","Bearer " + token)

                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));

    }

    @Test
    void getRentalById() throws Exception {
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

        Long TEST_ID = 1L;
        String TEST_RENTALDATE = "2022-03-17";
        String TEST_RETURNDATE = "2022-03-20";

        this.mockMvc.perform(get("/api/rental/" + TEST_ID)
                .with(csrf())
                .header("Authorization","Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
                .andExpect(jsonPath("$.id").value(TEST_ID))
                .andExpect(jsonPath("$.rentalDate").value(TEST_RENTALDATE))
                .andExpect(jsonPath("$.returnDate").value(TEST_RETURNDATE));

    }

    @Test
    void createRental() throws Exception {
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

        Long TEST_USERID = 4L;
        Long TEST_VHSID = 1L;
        Date TEST_RENTALDATE = Date.valueOf("2022-04-16");
        Date TEST_RETURNDATE = Date.valueOf("2022-04-20");
        Integer TEST_PAID = 8;
        Status TEST_STATUS = Status.RETURNED;

        RentalCommand rentalCommand = new RentalCommand(TEST_USERID,TEST_VHSID,
                TEST_RENTALDATE,TEST_RETURNDATE,TEST_STATUS);

        this.mockMvc.perform(
                post("/api/rental")

                        .header("Authorization","Bearer " + token)
                        .with(csrf())
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .content(objectMapper.writeValueAsString(rentalCommand))
                        .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
                .andExpect(jsonPath("$.paid").value(TEST_PAID))
                .andExpect(jsonPath("$.status").value("RETURNED"));
    }

}