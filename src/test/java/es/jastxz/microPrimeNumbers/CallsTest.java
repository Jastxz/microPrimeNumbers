package es.jastxz.microPrimeNumbers;

import es.jastxz.microPrimeNumbers.model.CribaRequest;
import es.jastxz.microPrimeNumbers.util.Util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CallsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /v0/polar - Should return polar coordinates")
    void testPolarCoordinatesSuccess() throws Exception {
        CribaRequest request = new CribaRequest(100);

        mockMvc.perform(post("/v0/polar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.puntos").isArray());
    }

    @Test
    @DisplayName("POST /v0/diff - Should return prime differences")
    void testPrimeDiffSuccess() throws Exception {
        CribaRequest request = new CribaRequest(100);

        mockMvc.perform(post("/v0/diff")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.diferencias").isArray());
    }

    @Test
    @DisplayName("POST /v0/chart - Should return chart response")
    void testPrimeChartSuccess() throws Exception {
        CribaRequest request = new CribaRequest(100);

        mockMvc.perform(post("/v0/chart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.primesPoints").isArray());
    }

    @Test
    @DisplayName("POST /v0/polar - Should return 400 when number is 0")
    void testBadRequest() throws Exception {
        CribaRequest request = new CribaRequest(0);

        mockMvc.perform(post("/v0/polar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("Bad Request"));
    }

    @Test
    @DisplayName("POST /v0/polar - Should return 503 when number exceeds limit")
    void testServiceUnavailable() throws Exception {
        CribaRequest request = new CribaRequest(Util.MAX_NUMBER_LIMIT + 1);

        mockMvc.perform(post("/v0/polar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.status").value("Service Unavailable"));
    }
}
