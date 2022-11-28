package carrental.carrentalweb.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matcher;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import carrental.carrentalweb.entity_factories.TestUserFactory;


/*
 * @SpringBootTest
 * Define the class as Test class.
 * 
 * @TestMethodOrder(OrderAnnotation.class)
 * Enable's the @Order(int) annotation allowing
 * to set the order the tests should be executed in.
 * 
 * Information about MockMvc:
 * https://spring.io/guides/gs/testing-web/
 */
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /*
     * Method: login();
     * Test if ...
     */
    @Test
    @Order(1)
    public void testLogin() throws Exception {
        this.mockMvc
            .perform(get("/login"))
            .andDo(print())
            // Except HTTP Response 200 OK
            .andExpect(status().isOk())
            // Except HTML Document
            .andExpect(content().string(containsString("<!DOCTYPE html>")));
    }

    /*
     * Method: signup();
     * Test if ...
     */
    @Test
    @Order(2)
    public void testSignup() throws Exception {
        Matcher<Object> expectedUser = allOf(
            hasProperty("username", equalTo(null)),
            hasProperty("password", equalTo(null)),
            hasProperty("email", equalTo(null))
        );

        this.mockMvc
            .perform(get("/signup"))
            .andDo(print())
            // Except HTTP Response 200 OK
            .andExpect(status().isOk())
            // Except HTML Document
            .andExpect(content().string(containsString("<!DOCTYPE html>")))
            // Except User object
            .andExpect(model().attribute("user", expectedUser));
    }

    /*
     * Method: show();
     * Test if ...
     */
    @Test
    @Order(3)
    public void testShow() throws Exception {
        /*
        this.mockMvc
            .perform(get("/user"))
            .andDo(print())
            // Except HTTP Response 200 OK
            .andExpect(status().isOk())
            // Except HTML Document
            .andExpect(content().string(containsString("<!DOCTYPE html>")));
        */
    }

    /*
     * Method: edit();
     * Test if ...
     */
    @Test
    @Order(4)
    public void testEdit() throws Exception {
        /*
        this.mockMvc
            .perform(get("/user/edit"))
            .andDo(print())
            // Except HTTP Response 200 OK
            .andExpect(status().isOk())
            // Except HTML Document
            .andExpect(content().string(containsString("<!DOCTYPE html>")));
        */
    }

    /*
     * Method: create();
     * Test if ...
     */
    @Test
    @Order(5)
    public void testCreate() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValueAsString(TestUserFactory.create());
        String input = objectMapper.writeValueAsString(TestUserFactory.create());
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
    }

    /*
     * Method: update();
     * Test if ...
     */
    @Test
    @Order(6)
    public void testUpdate() throws Exception {
        /*
        this.mockMvc
            .perform(patch("/user"))
            .andDo(print())
            // Except HTTP Response 200 OK
            .andExpect(status().isOk())
            // Except HTML Document
            .andExpect(content().string(containsString("<!DOCTYPE html>")));
        */
    }

    /*
     * Method: updatePassword();
     * Test if ...
     */
    @Test
    @Order(7)
    public void testUpdatePassword() throws Exception {
        /*
        this.mockMvc
            .perform(patch("/user/password"))
            .andDo(print())
            // Except HTTP Response 200 OK
            .andExpect(status().isOk())
            // Except HTML Document
            .andExpect(content().string(containsString("<!DOCTYPE html>")));
        */
    }

    /*
     * Method: disable();
     * Test if ...
     */
    @Test
    @Order(8)
    public void testDisable() throws Exception {
        /*
        this.mockMvc
            .perform(patch("/user/disable"))
            .andDo(print())
            // Except HTTP Response 200 OK
            .andExpect(status().isOk())
            // Except HTML Document
            .andExpect(content().string(containsString("<!DOCTYPE html>")));
        */
    }
}
