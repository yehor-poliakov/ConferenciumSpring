package com.poliakov.springbootconference.dto;

import lombok.Data;

@Data
public class ConferenceSearchFilters {
    private boolean showPast;
    private boolean showFuture;
    private ConferenceSortType orderby;

    public ConferenceSearchFilters() {
        this.showPast = true;
        this.showFuture = true;
        this.orderby = ConferenceSortType.DateDesc;
    }
}
