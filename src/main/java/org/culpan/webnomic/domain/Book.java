package org.culpan.webnomic.domain;

import javax.persistence.*;

/**
 * Created by USUCUHA on 7/14/2016.
 */
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Version
    private Integer version;
}
