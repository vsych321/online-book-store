package bookstore.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bookstore.dto.bookdto.BookDto;
import bookstore.dto.bookdto.CreateBookRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Sql(scripts = {"classpath:database/books_categories/delete-books_categories.sql",
        "classpath:database/books/delete-books.sql",
        "classpath:database/categories/delete-categories.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"classpath:database/categories/add-categories.sql",
        "classpath:database/books/add-books.sql",
        "classpath:database/books_categories/insert-books-and-categories.sql"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
    protected static MockMvc mockMvc;
    private static ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("Get book by id")
    void getBookById_ValidRequest_Success() throws Exception {
        Long id = 1L;

        MvcResult result = mockMvc.perform(get("/books/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("title", "Test book 1")
                .hasFieldOrPropertyWithValue("author", "Test author 1")
                .hasFieldOrPropertyWithValue("isbn", "12423541535")
                .hasFieldOrPropertyWithValue("price", BigDecimal.TEN)
                .hasFieldOrPropertyWithValue("description", "Test book description 1")
                .hasFieldOrPropertyWithValue("coverImage", "cover_image");
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Create a new book")
    void createBook_ValidRequest_Success() throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto("title", "author",
                "isbn", BigDecimal.TEN, "description",
                "cover_image", List.of(1L));

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        System.out.println(jsonRequest);

        MvcResult result = mockMvc.perform(post("/books")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("title", "title")
                .hasFieldOrPropertyWithValue("author", "author")
                .hasFieldOrPropertyWithValue("isbn", "isbn")
                .hasFieldOrPropertyWithValue("price", BigDecimal.TEN)
                .hasFieldOrPropertyWithValue("description", "description")
                .hasFieldOrPropertyWithValue("coverImage", "cover_image");
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Update book by id")
    void updateBook_ValidId_Success() throws Exception {
        Long id = 1L;
        CreateBookRequestDto requestDto = new CreateBookRequestDto("title", "author",
                "isbn", BigDecimal.TEN, "description",
                "cover_image", List.of(1L));

        MvcResult result = mockMvc.perform(put("/books/" + id)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("title", "title")
                .hasFieldOrPropertyWithValue("author", "author")
                .hasFieldOrPropertyWithValue("isbn", "isbn")
                .hasFieldOrPropertyWithValue("price", BigDecimal.TEN)
                .hasFieldOrPropertyWithValue("description", "description")
                .hasFieldOrPropertyWithValue("coverImage", "cover_image");
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Delete book by id")
    void deleteBookById_ValidId_Success() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/books/" + id))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
