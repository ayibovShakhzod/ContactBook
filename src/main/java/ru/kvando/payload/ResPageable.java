package ru.kvando.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResPageable {

    private Integer totalPages;

    private Long totalElements;

    private Integer currentPage;

    private Object object;
}
