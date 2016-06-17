package ru.troshkov.db.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.troshkov.db.domain.constants.PeopleStatus;

import java.util.Date;

/**
 * Created by ivan on 16.06.2016.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class People extends Entry {
    private String firstName;
    private String lastName;
    private String middleName;
    private PeopleStatus status;
    private Date birthday;
}
