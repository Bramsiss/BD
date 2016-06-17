package ru.troshkov.db.domain.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by ivan on 16.06.2016.
 */
@Getter
@AllArgsConstructor
public enum PeopleStatus {
    JUDGE("Судья"),
    TURNKEY("Надзиратель"),
    FIGHTER("Боец"),
    PRISONER("Заключённый");

    private String description;
}
