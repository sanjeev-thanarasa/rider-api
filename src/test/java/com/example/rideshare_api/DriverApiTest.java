package com.example.rideshare_api;

import com.example.rideshare_api.driver.Driver;
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
class DriverApiTest {

  @Autowired
  MockMvc mvc;
  @Autowired
  ObjectMapper om;

  @Test
  void create_and_get_driver() throws Exception {
    // create
    Driver d = new Driver();
    d.setName("Kamal");
    d.setAddress("Colombo");
    d.setVehicleNumber("ABC-1234");

    String json = om.writeValueAsString(d);

    String location = mvc.perform(post("/api/drivers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", notNullValue()))
        .andReturn().getResponse().getContentAsString();

    // parse id
    Driver created = om.readValue(location, Driver.class);
    Long id = created.getId();

    // get
    mvc.perform(get("/api/drivers/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Kamal"))
        .andExpect(jsonPath("$.vehicleNumber").value("ABC-1234"));
  }

  @Test
  void validation_error_on_create_driver() throws Exception {
    // name is required -> expect 400
    String json = "{\"name\":\"\",\"address\":\"\",\"vehicleNumber\":\"123456789012345678901\"}";
    mvc.perform(post("/api/drivers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.fieldErrors.name", containsString("required")));
  }

  @Test
  void list_and_count_drivers() throws Exception {
    mvc.perform(get("/api/drivers"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", isA(java.util.List.class)));
    mvc.perform(get("/api/drivers/count"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.count", greaterThanOrEqualTo(0)));
  }

  @Test
  void edit_and_delete_driver() throws Exception {
    // create first
    Driver d = new Driver();
    d.setName("Suren");
    d.setAddress("Kandy");
    d.setVehicleNumber("DEF-5678");
    String json = new ObjectMapper().writeValueAsString(d);

    String body = mvc.perform(post("/api/drivers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    Long id = new ObjectMapper().readValue(body, Driver.class).getId();

    // edit
    d.setName("Suren Updated");
    json = new ObjectMapper().writeValueAsString(d);
    mvc.perform(put("/api/drivers/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Suren Updated"));

    // delete
    mvc.perform(delete("/api/drivers/{id}", id))
        .andExpect(status().isOk());

    // get -> 404
    mvc.perform(get("/api/drivers/{id}", id))
        .andExpect(status().isNotFound());
  }
}
