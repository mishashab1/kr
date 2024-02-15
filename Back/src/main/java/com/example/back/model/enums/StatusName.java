package com.example.back.model.enums;

public enum StatusName {
    PASSED(1), // ПРОЙДЕНО
    FAILED(2), // НЕ_ПРОЙДЕНО
    CHECKED(3),// ПРОВЕРЕНО
    NOT_CHECKED(4); // НЕ_ПРОВЕРЕНО

    private final int id;
    StatusName(int id) {this.id = id;}
    public int getId() {return id;}
}
