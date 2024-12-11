package store.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Promotion {
    private String name;
    private int buy;
    private int get;
    private LocalDate startDate;
    private LocalDate endDate;

    public Promotion(List<String> promotionInfo) {
        this.name = promotionInfo.get(0);
        this.buy = Integer.parseInt(promotionInfo.get(1));
        this.get = Integer.parseInt(promotionInfo.get(2));
        this.startDate = dateFormatter(promotionInfo.get(3));
        this.endDate = dateFormatter(promotionInfo.get(4));
    }

    public String getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    private LocalDate dateFormatter(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }
}
