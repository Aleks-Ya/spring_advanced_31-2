package booking.web.controller;

import booking.BaseWebTest;
import booking.util.JsonUtil;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = UserBatchUploadController.class)
public class UserBatchUploadControllerTest extends BaseWebTest {

    @Test
    public void batchUpload() throws Exception {
        String email1 = "stenev@gmail.com";
        String email2 = "julia@gmail.com";

        assertThat(userService.getAll(), emptyIterable());

        String fileContent1 = JsonUtil.format("{" +
                "'name': 'Steven'," +
                "'email': '%s'," +
                "'birthday': '1990-07-03'," +
                "'password': 'pass'" +
                "}", email1
        );
        MockMultipartFile multipartFile1 = new MockMultipartFile(UserBatchUploadController.PART_NAME,
                "filename1.json", MediaType.APPLICATION_JSON_VALUE, fileContent1.getBytes());

        String fileContent2 = JsonUtil.format("{" +
                "  'name': 'Julia'," +
                "  'email': '%s'," +
                "  'birthday': '2012-09-11'," +
                "  'password': 'pass'" +
                "}", email2
        );
        MockMultipartFile multipartFile2 = new MockMultipartFile(UserBatchUploadController.PART_NAME,
                "filename2.json", MediaType.APPLICATION_JSON_VALUE, fileContent2.getBytes());

        MockMultipartHttpServletRequestBuilder multipartBuilder = MockMvcRequestBuilders
                .multipart(UserBatchUploadController.BATCH_UPLOAD_ENDPOINT)
                .file(multipartFile1)
                .file(multipartFile2);

        mvc.perform(multipartBuilder).andExpect(status().isOk());

        assertNotNull(userService.getByEmail(email1));
        assertNotNull(userService.getByEmail(email2));
    }
}