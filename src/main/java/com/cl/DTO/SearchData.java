package com.cl.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Builder
@Getter @Setter
public class SearchData {
    private Long id;
    private Double LAT;
    private Double LNT;
    private Timestamp searchTime;
    // 기본 생성자
    public SearchData() {
    }

    public SearchData(Long id, Double LAT, Double LNT, Timestamp searchTime) {
        this.id = id;
        this.LAT = LAT;
        this.LNT = LNT;
        this.searchTime = searchTime;
    }
}
