package com.example.ratingsystem.application.services;

import com.example.ratingsystem.application.ports.Rating.LoadRatingPort;
import com.example.ratingsystem.application.ports.User.LoadUserPort;
import com.example.ratingsystem.domain.models.Rating;
import com.example.ratingsystem.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final LoadRatingPort loadRatingPort;
    private final LoadUserPort loadUserPort;

    public Short getAverageRating(Integer id) {
        List<Rating> ratings = loadRatingPort.loadByTargetUserId(id);
        double avg = ratings.stream()
                .mapToInt(Rating::getScore)
                .average()
                .orElse(0.0);
        return (short) avg;
    }

    public List<User> getTopSellers(Integer count) {
        return loadUserPort.loadAll().stream()
                .sorted((u1, u2) -> {
                    short avg1 = getAverageRating(u1.getId());
                    short avg2 = getAverageRating(u2.getId());
                    return Short.compare(avg2, avg1);
                })
                .limit(count)
                .toList();
    }

}
