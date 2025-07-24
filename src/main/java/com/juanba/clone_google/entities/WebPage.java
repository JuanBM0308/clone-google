package com.juanba.clone_google.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_web_page")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Builder
public class WebPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String url;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private boolean isActive;

}
