package ua.training.api.dto;


import lombok.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
public class StatisticsDto {

    private BigDecimal earningsLastMonth;

    private BigDecimal earningsYear;

    private Long deliversNumber;

    private Long deliversNumberYear;

    private Map<Integer, Long> numberOfOrdersByForYear = new HashMap<>();

    private Map<Integer, BigDecimal> earningsOfOrdersByForYear = new HashMap<>();

}
