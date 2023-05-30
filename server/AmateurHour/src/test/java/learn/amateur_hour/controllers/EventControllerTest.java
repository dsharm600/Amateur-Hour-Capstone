package learn.amateur_hour.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import learn.amateur_hour.data.AppUserRepository;
import learn.amateur_hour.data.EventRepository;
import learn.amateur_hour.models.AppUser;
import learn.amateur_hour.models.Event;
import learn.amateur_hour.security.JwtConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTest {

    @MockBean
    EventRepository repository;

    @MockBean
    AppUserRepository appUserRepository;

    @Autowired
    MockMvc mvc;

    @Autowired
    JwtConverter jwtConverter;

    String token;

    @BeforeEach
    void setup() {
        AppUser appUser = new AppUser(1,"Thenue", "EMPTY BIO","P@ssw0rd!", "newUser@email.com",false, List.of("User"));
        when(appUserRepository.findByUsername("Thenue")).thenReturn(appUser);

        token = jwtConverter.getTokenFromUser(appUser);
    }

    @Test
    void findFutureEventsByStateGives200WhenGood() throws Exception {
        List<Event> expected = List.of(makeEvent());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String expectedJson = mapper.writeValueAsString(expected);

        var request = get("/api/event/WI/1")
                .header("Authorization", "Bearer " + token);

        when(repository.findFutureEventsByState(any(), anyInt())).thenReturn(expected);

        mvc.perform(request).andExpect(status().isOk()).andExpect(content().json(expectedJson));
    }

    @Test
    void findPastEvents() throws Exception {
        List<Event> expected = List.of(makeEvent());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String expectedJson = mapper.writeValueAsString(expected);

        var request = get("/api/event/history/1")
                .header("Authorization", "Bearer " + token);

        when(repository.findPastEvents(anyInt())).thenReturn(expected);

        mvc.perform(request).andExpect(status().isOk()).andExpect(content().json(expectedJson));
    }

    @Test
    void findById() throws Exception {
        Event expected = makeEvent();
        makeEvent().setEventId(1);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String expectedJson = mapper.writeValueAsString(expected);

        var request = get("/api/event/1")
                .header("Authorization", "Bearer " + token);

        when(repository.findById(anyInt())).thenReturn(expected);

        mvc.perform(request).andExpect(status().isOk()).andExpect(content().json(expectedJson));
    }

    @Test
    void addis400WhenEmpty() throws Exception {
        var request = post("/api/event")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void addIs201WhenValid() throws Exception {
        Event event = makeEvent();
        Event expected = makeEvent();
        expected.setEventId(1);

        when(repository.add(any())).thenReturn(expected);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String eventJson = mapper.writeValueAsString(event);
        String expectedJson = mapper.writeValueAsString(expected);

        var request = post("/api/event")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(eventJson);

        mvc.perform(request).andExpect(status().isCreated()).andExpect(content().json(expectedJson));
    }

    @Test
    void addIs400WhenInvalid() throws Exception {
        Event event = new Event();
        event.setEventId(1);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String eventJson = mapper.writeValueAsString(event);

        var request = post("/api/event")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(eventJson);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateIs204WhenValid() throws Exception {
        Event event = makeEvent();
        event.setEventId(1);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String eventJson = mapper.writeValueAsString(event);

        var request = put("/api/event/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(eventJson);

        when(repository.update(any())).thenReturn(true);

        mvc.perform(request).andExpect(status().isNoContent());
    }

    @Test
    void updateIs400WhenInvalid() throws Exception {
        Event event = makeEvent();
        event.setEventId(1);
        event.setHost("");
        event.setCapacity(2);


        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String eventJson = mapper.writeValueAsString(event);

        var request = put("/api/event/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(eventJson);

        mvc.perform(request).andExpect(status().isBadRequest());
    }

    private Event makeEvent() {
        Event event = new Event();
        event.setEventId(0);
        event.setHost("AmyCooks");
        event.setEventDescription("Test");
        event.setEventNotes("ya");
        event.setEventAddress("aa");
        event.setCity("Milwaukee");
        event.setState("WI");
        event.setEventDate(LocalDateTime.now().plusDays(7));
        event.setTitle("Testing");
        event.setCapacity(10);
        event.setRatings(new ArrayList<>());
        event.setTags(new ArrayList<>());

        return event;
    }
    
    @Test
    void cancel() {
        //TODO
    }
}
