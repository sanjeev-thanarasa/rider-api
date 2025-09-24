package com.example.rideshare_api;

import com.example.rideshare_api.passenger.Passenger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PassengerApiTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  ObjectMapper om;

  @Test
  void create_get_edit_delete_passenger() throws Exception {
    Passenger p = new Passenger();
    p.setName("Nisha");
    p.setPhone("+94771234567");

    String json = om.writeValueAsString(p);

    String createdBody = mvc.perform(post("/api/passengers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", notNullValue()))
        .andReturn().getResponse().getContentAsString();

    Long id = om.readValue(createdBody, Passenger.class).getId();

    // get
    mvc.perform(get("/api/passengers/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Nisha"));

    // edit
    p.setName("Nisha U");
    p.setPhone("+94 77 123 4567");
    json = om.writeValueAsString(p);
    mvc.perform(put("/api/passengers/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Nisha U"));

    // count + list
    mvc.perform(get("/api/passengers/count"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.count", greaterThanOrEqualTo(1)));

    mvc.perform(get("/api/passengers"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", isA(java.util.List.class)));

    // delete
    mvc.perform(delete("/api/passengers/{id}", id))
        .andExpect(status().isOk());
  }

  @Test
  void validation_error_on_phone() throws Exception {
    String json = "{\"name\":\"A\",\"phone\":\"bad-phone\"}";
    mvc.perform(post("/api/passengers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.fieldErrors.phone", containsString("invalid phone")));
  }
}
