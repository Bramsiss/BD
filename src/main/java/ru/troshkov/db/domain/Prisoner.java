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
public class Prisoner extends Entry {
    private People people;
    private Crime crime;
    private Type fighterType;
    private Date start;
    private Date end;
}
