package com.basicframework.common.result;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PageResultTest {

    @Test
    void ofCarriesAllFields() {
        PageResult<String> result = PageResult.of(List.of("a", "b"), 42, 2, 10);

        assertThat(result.getList()).containsExactly("a", "b");
        assertThat(result.getTotal()).isEqualTo(42);
        assertThat(result.getPage()).isEqualTo(2);
        assertThat(result.getSize()).isEqualTo(10);
    }
}
