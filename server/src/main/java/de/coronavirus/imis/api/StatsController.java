package de.coronavirus.imis.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import de.coronavirus.imis.api.dto.AggregationResultZip;
import de.coronavirus.imis.services.StatsService;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;

    @GetMapping
    public List<AggregationResultZip> getForZip(@RequestParam("lowerBoundsZip") String lowerBounds,
            @RequestParam("upperBoundsZips") String upperBounds
    ) {
        return statsService.resultZipList(lowerBounds, upperBounds);
    }
}
