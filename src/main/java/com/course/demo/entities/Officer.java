package com.course.demo.entities;


public record Officer(Integer id,
                      Rank rank,
                      String firstName,
                      String lastName) {
}
