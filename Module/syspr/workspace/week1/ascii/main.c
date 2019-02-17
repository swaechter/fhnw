#include <stdio.h>
#include <stdbool.h>

const int ROW_INDEX = 32;

const int COLUMN_INDEX = 3;

const char *const specialcharacters[] = {
    "NUL",
    "SOH",
    "STX",
    "ETX",
    "E0T",
    "ENQ",
    "ACK",
    "BEL",
    "BS",
    "TAB",
    "LF",
    "VT",
    "FF",
    "CR",
    "SO",
    "SI",
    "DLE",
    "DC1",
    "DC2",
    "DC3",
    "DC4",
    "NAK",
    "SYN",
    "ETB",
    "CAN",
    "EM",
    "SUB",
    "ESC",
    "FS",
    "GS",
    "RS",
    "US",
    "SPACE"
};


bool is_special_character(int i) {
    return i <= ROW_INDEX;
}

void display_ascii_header() {
    printf("Dex Hx Oct   Char | Dex Hx Oct  Char | Dex Hx Oct  Char\n");
    printf("-------------------------------------------------------\n");
}

void display_ascii_item(int number, int column) {
    if (is_special_character(number)) {
        printf("%3d %02x %03o %6s ", number, number, number, specialcharacters[number]);
    } else {
        printf("%3d %02x %03o %6c ", number, number, number, number);
    }
    printf(column == COLUMN_INDEX - 1 ? "\n" : "|");
}

void display_ascii_table() {
    display_ascii_header();
    for (int row = 0; row < ROW_INDEX; row++) {
        for (int column = 0; column < COLUMN_INDEX; column++) {
            display_ascii_item(column * ROW_INDEX + row, column);
        }
    };
}

int main() {
    display_ascii_table();
    return 0;
}
