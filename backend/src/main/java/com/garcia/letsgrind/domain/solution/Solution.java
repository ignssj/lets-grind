package com.garcia.letsgrind.domain.solution;

import java.util.UUID;

import com.garcia.letsgrind.domain.problem.Problem;
import com.garcia.letsgrind.domain.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "solutions")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Solution {

    @Id
    @GeneratedValue
    private UUID id;
    private String language;
    private String code;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

}
