package carrental.carrentalweb.controller;

import carrental.carrentalweb.entities.PickupPoint;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.LinkedList;


import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
class PickupPointControllerTest {

  @Autowired
  private MockMvc mockMvc;

/*
  this.mockMvc
      .perform(
  post("/signup")
                 .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                 .content(input))
      .andDo(print())
      // Except HTTP Response 200 OK
      .andExpect(status().isOk())
      // Except HTML Document
      .andExpect(content().string(containsString("<!DOCTYPE html>")));
        */

  @Test
  void index() throws Exception {
/*
    Matcher<PickupPoint> expectedPp = allOf(
        hasProperty("id", equalTo(null)),
        hasProperty("addressId", equalTo(null)),
        hasProperty("createdAt", equalTo(null)),
        hasProperty("updatedAt", equalTo(null)));

        LinkedList<PickupPoint> expectedList = new LinkedList<>();
        expectedList.add(expectedPp);
    this.mockMvc
        .perform(get("/index"))
            .andDo(print())
        // Except HTTP Response 200 OK
        .andExpect(status().isOk())
        // Except HTML Document
        .andExpect((ResultMatcher) content().string(containsString("<!DOCTYPE html>")))
        .andExpect(model().attribute("pickupPoints", expectedList));
*/

  }

  @Test
  void newPickupPoint() {
  }

  @Test
  void createPickupPoint() {
  }

  @Test
  void updatePickupPoint() {
  }

  @Test
  void editPickupPoint() {
  }

  @Test
  void deletePickupPoint() {
  }
}