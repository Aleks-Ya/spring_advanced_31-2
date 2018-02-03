package booking.web.controller;

import booking.BaseWebTest;
import booking.domain.Auditorium;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static booking.web.controller.AuditoriumController.ENDPOINT;
import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Aleksey Yablokov
 */
@ContextConfiguration(classes = {AuditoriumController.class})
public class AuditoriumControllerTest extends BaseWebTest {

    @Test
    public void createAuditorium() throws Exception {
        String expAuditoriumName = "Room";
        String expSeatsNumber = String.valueOf(1000);
        String expVipSeats = "1,2,3,4";

        ResultActions resultActions = mvc.perform(post(ENDPOINT)
                .param("auditoriumName", expAuditoriumName)
                .param("seatsNumber", expSeatsNumber)
                .param("vipSeats", expVipSeats)
        );

        MvcResult mvcResult = resultActions.andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Matcher m = Pattern.compile("Id: (\\d+)").matcher(content);
        assertTrue(m.find());
        long auditoriumId = Long.parseLong(m.group(1));

        resultActions
                .andExpect(status().isCreated())
                .andExpect(content().string(format(LoginControllerTest.ANONYMOUS_HEADER +
                                "<h1>Auditorium is created</h1>\n" +
                                "<p>Id: %d</p>\n" +
                                "<p>Name: Room</p>\n" +
                                "<p>Seats number: 1,000</p>\n" +
                                "<p>VIP seats: 1,2,3,4</p>",
                        auditoriumId
                )));

        Auditorium actAuditorium = auditoriumService.getById(auditoriumId);
        assertThat(actAuditorium.getName(), equalTo(expAuditoriumName));
        assertThat(Integer.toString(actAuditorium.getSeatsNumber()), equalTo(expSeatsNumber));
        assertThat(actAuditorium.getVipSeats(), equalTo(expVipSeats));
    }

    @Test
    public void getAuditoriums() throws Exception {
        Auditorium blueHall = testObjects.createBlueHall();
        Auditorium redHall = testObjects.createRedHall();
        mvc.perform(get("/auditorium"))
                .andExpect(status().isOk())
                .andExpect(content().string(format(LoginControllerTest.ANONYMOUS_HEADER +
                                RootControllerTest.NAVIGATOR +
                                "<h1>Auditoriums</h1>\n" +
                                "<p>Auditorium</p>\n" +
                                "<p>Id: %d</p>\n" +
                                "<p>Name: Blue hall</p>\n" +
                                "<p>Seats number: 1,000</p>\n" +
                                "<p>VIP seats: 1,2,3,4,5</p><hr/>\n" +
                                "<p>Auditorium</p>\n" +
                                "<p>Id: %d</p>\n" +
                                "<p>Name: Red hall</p>\n" +
                                "<p>Seats number: 500</p>\n" +
                                "<p>VIP seats: 1</p><hr/>\n",
                        blueHall.getId(),
                        redHall.getId())));
    }

    @Test
    public void getById() throws Exception {
        Auditorium auditorium = auditoriumService.create(new Auditorium("Meeting room",
                500, Arrays.asList(1, 2, 3)));
        mvc.perform(get("/auditorium/id/" + auditorium.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(format(LoginControllerTest.ANONYMOUS_HEADER +
                                "<h1>Auditorium</h1>\n" +
                                "<p>Id: %d</p>\n" +
                                "<p>Name: Meeting room</p>\n" +
                                "<p>Seats number: 500</p>\n" +
                                "<p>VIP seats: 1,2,3</p>",
                        auditorium.getId())));
    }

    @Test
    public void getAuditoriumByName() throws Exception {
        Auditorium auditorium = auditoriumService.create(new Auditorium("Relax room",
                500, Arrays.asList(1, 2, 3)));
        mvc.perform(get("/auditorium/name/" + auditorium.getName()))
                .andExpect(status().isOk())
                .andExpect(content().string(format(
                        LoginControllerTest.ANONYMOUS_HEADER +
                                "<h1>Auditorium</h1>\n" +
                                "<p>Id: %d</p>\n" +
                                "<p>Name: Relax room</p>\n" +
                                "<p>Seats number: 500</p>\n" +
                                "<p>VIP seats: 1,2,3</p>",
                        auditorium.getId())));
    }

    @Test
    public void seatsNumberByAuditoriumNameGet() throws Exception {
        String auditoriumName = UUID.randomUUID().toString();
        auditoriumService.create(new Auditorium(auditoriumName, 500, Arrays.asList(1, 2, 3)));
        mvc.perform(get(format("/auditorium/name/%s/seatsNumber", auditoriumName)))
                .andExpect(status().isOk())
                .andExpect(content().string(format(
                        LoginControllerTest.ANONYMOUS_HEADER +
                                "<h1>Seats number</h1>\n" +
                                "<p>Auditorium: %s</p>\n" +
                                "<p>Seats number: 500</p>", auditoriumName)));
    }

    @Test
    public void seatsNumberByAuditoriumIdGet() throws Exception {
        String auditoriumName = UUID.randomUUID().toString();
        Auditorium auditorium = auditoriumService.create(new Auditorium(auditoriumName,
                500, Arrays.asList(1, 2, 3)));
        mvc.perform(get(format("/auditorium/id/%s/seatsNumber", auditorium.getId())))
                .andExpect(status().isOk())
                .andExpect(content().string(format(LoginControllerTest.ANONYMOUS_HEADER +
                        "<h1>Seats number</h1>\n" +
                        "<p>Auditorium: %s</p>\n" +
                        "<p>Seats number: 500</p>", auditoriumName)));
    }

    @Test
    public void vipSeatsByAuditoriumNameGet() throws Exception {
        Auditorium auditorium = testObjects.createBlueHall();
        mvc.perform(get(format("/auditorium/name/%s/vipSeats", auditorium.getName())))
                .andExpect(status().isOk())
                .andExpect(content().string(LoginControllerTest.ANONYMOUS_HEADER +
                        "<h1>VIP seats</h1>\n" +
                        "<p>Auditorium: Blue hall</p>\n" +
                        "<p>VIP seats: 1,2,3,4,5</p>"));
    }

    @Test
    public void vipSeatsByAuditoriumIdGet() throws Exception {
        Auditorium auditorium = auditoriumService.create(new Auditorium("Red room",
                500, Arrays.asList(1, 2, 3)));
        mvc.perform(get(format("/auditorium/id/%d/vipSeats", auditorium.getId())))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        LoginControllerTest.ANONYMOUS_HEADER +
                                "<h1>VIP seats</h1>\n" +
                                "<p>Auditorium: Red room</p>\n" +
                                "<p>VIP seats: 1,2,3</p>"));
    }

    @Test
    public void deleteExistsAuditorium() throws Exception {
        String auditoriumName = UUID.randomUUID().toString();
        Auditorium auditorium = auditoriumService.create(new Auditorium(auditoriumName,
                500, Arrays.asList(1, 2, 3)));
        mvc.perform(delete(format("/auditorium/id/%s/delete", auditorium.getId())))
                .andExpect(status().isOk())
                .andExpect(content().string(format(LoginControllerTest.ANONYMOUS_HEADER +
                                "<h1>Auditorium is deleted</h1>\n" +
                                "<p>Id: %d</p>\n" +
                                "<p>Name: %s</p>\n" +
                                "<p>Seats number: 500</p>\n" +
                                "<p>VIP seats: 1,2,3</p>",
                        auditorium.getId(), auditoriumName)));
    }

    @Test
    public void deleteNotExistsAuditorium() throws Exception {
        int notExistsAuditoriumId = 1234567;
        mvc.perform(delete(format("/auditorium/id/%s/delete", notExistsAuditoriumId)))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "<h1>An error occurred</h1>\n" +
                                "<p>Auditorium is not found by id=1234567</p>"));
    }
}