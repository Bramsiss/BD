package ru.troshkov.db.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by ivan on 16.06.2016.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Fight extends Entry {
    private People first;
    private People second;
    private People judge;
    private Date date;
    private Arena arena;
    private Boolean firstResult;
    private Boolean secondResult;
    private String name;
}
