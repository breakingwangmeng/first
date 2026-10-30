package com.basicframework.common.exception;

import com.basicframework.common.result.ErrorCode;
import com.basicframework.common.result.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void businessExceptionMapsToItsCode() throws Exception {
        mockMvc.perform(post("/test/business"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.BAD_CREDENTIALS.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.BAD_CREDENTIALS.getMessage()));
    }

    @Test
    void notFoundBusinessExceptionMapsToHttpNotFound() throws Exception {
        mockMvc.perform(post("/test/not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.getCode()));
    }

    @Test
    void validationErrorMapsToBadRequest() throws Exception {
        mockMvc.perform(post("/test/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.message").value(containsString("name")));
    }

    @Test
    void unknownExceptionMapsToInternalError() throws Exception {
        mockMvc.perform(post("/test/boom"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value(ErrorCode.INTERNAL_ERROR.getCode()));
    }

    @RestController
    @RequestMapping("/test")
    static class TestController {

        @PostMapping("/business")
        public Result<Void> business() {
            throw new BusinessException(ErrorCode.BAD_CREDENTIALS);
        }

        @PostMapping("/not-found")
        public Result<Void> notFound() {
            throw new BusinessException(ErrorCode.NOT_FOUND, "资源不存在");
        }

        @PostMapping("/validate")
        public Result<Void> validate(@Valid @RequestBody NameRequest request) {
            return Result.ok();
        }

        @PostMapping("/boom")
        public Result<Void> boom() {
            throw new IllegalStateException("boom");
        }
    }

    static class NameRequest {

        @NotBlank(message = "name 不能为空")
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
