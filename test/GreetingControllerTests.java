package hello;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GreetingControllerTests {

    @Autowired
    private MockMvc mockMvc;


    private RequestBuilder request;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new GreetingController()).build();
        request = null;
    }

    @Test
    public void testGet() throws Exception{
        request = get("/api/tasks/");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));;
    }

    @Test
    public void testPost() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");
        Date date = new Date();
        Greeting greeting = new Greeting(3,"Restful API homework",sdf.format(date));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBook = objectMapper.writeValueAsString(greeting);

        request = post("/api/tasks/")
                .param("id", "3")
                .param("content", "Restful API homework")
                .param("createdTime", sdf.format(date));
        mockMvc.perform(request)
                .andExpect(content().string(equalTo("success")));

        // 3、get获取user列表，应该有刚才插入的数据
        request = get("/api/tasks/");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[{\"id\":3,\"content\":\"Restful API homework\",\"createdTime\":\""+sdf.format(date)+"\"}]")));
        request = post("/api/tasks/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBook.getBytes());
    }

    @Test
    public void testDelete() throws Exception{
        request = delete("/api/tasks/1");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("success")));

        String respStr = mockMvc.perform(get("/api/tasks/"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")))
                .andReturn().getResponse().getContentAsString();
        System.out.println("testDelete.resp:"+respStr);

    }

    @Test
    public void noParamGreetingShouldReturnDefaultMessage() throws Exception {

        this.mockMvc.perform(get("/greeting")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello, World!"));
    }

    @Test
    public void paramGreetingShouldReturnTailoredMessage() throws Exception {

        this.mockMvc.perform(get("/greeting").param("name", "Spring Community"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello, Spring Community!"));
    }

}
