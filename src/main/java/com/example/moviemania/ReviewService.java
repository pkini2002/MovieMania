package com.example.moviemania;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReviewService {
     private ReviewRepository reviewRepository;
     @Autowired
     private MongoTemplate mongoTemplate;
     public Review createReview(String reviewBody,String imdbId){
         // adding a review
         Review review=reviewRepository.insert(new Review(reviewBody, LocalDateTime.now(),LocalDateTime.now()));

         // Adding the review to an appropriate movie
         mongoTemplate.update(Movie.class)
                 .matching(Criteria.where("imdbId").is(imdbId))
                 .apply(new Update().push("reviewIds").value(review)).first();
         return review;
     }
}
