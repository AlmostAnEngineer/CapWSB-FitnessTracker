package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Entity
@Table(name = "trainings")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Training {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @Getter
    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Setter
    @Getter
    @Column(name = "end_time", nullable = false)
    private Date endTime;

    @Setter
    @Getter
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "activity_type", nullable = false)
    private ActivityType activityType;

    @Setter
    @Getter
    @Column(name = "distance")
    private double distance;

    @Setter
    @Getter
    @Column(name = "average_speed")
    private double averageSpeed;

    public Training(
            final User user,
            final Date startTime,
            final Date endTime,
            final ActivityType activityType,
            final double distance,
            final double averageSpeed) {
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
    }

    public Training(
            Long id,
            final User user,
            final Date startTime,
            final Date endTime,
            final ActivityType activityType,
            final double distance,
            final double averageSpeed) {
        this.id = id;
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
    }

}