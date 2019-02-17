#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct address {
    char name[50];
    char street[100];
    short number;
    long zip;
    char town[50];
};

typedef struct address address_t;

typedef address_t *address_ptr_t;

typedef char *string_t;

typedef short number_t;

typedef long zip_t;

typedef void *params_t;

void copy_address_by_values(address_ptr_t address, string_t name, string_t street, number_t number, zip_t zip, string_t town) {
    strcpy(address->name, name);
    strcpy(address->street, street);
    strcpy(address->town, town);
    address->number = number;
    address->zip = zip;
}

address_ptr_t copy_address_by_address(address_ptr_t addressout, address_ptr_t addressin) {
    copy_address_by_values(addressout, addressin->name, addressin->street, addressin->number, addressin->zip, addressin->town);
    return addressout;
}

address_ptr_t create_address(string_t name, string_t street, number_t number, zip_t zip, string_t town) {
    address_ptr_t address = (address_t *) malloc(sizeof(address_t));
    copy_address_by_values(address, name, street, number, zip, town);
    return address;
}

address_ptr_t delete_address(address_ptr_t address) {
    free(address);
    return NULL;
}

void display_address(address_ptr_t address) {
    printf("===== Address =====\n");
    printf("Name %s\n", address->name);
    printf("Street %s\n", address->street);
    printf("Number %d\n", address->number);
    printf("Zip %ld\n", address->zip);
    printf("Town %s\n", address->town);
}

int main(void) {
    // Define the address values
    string_t name = "Simon Wächter";
    string_t street = "Somewhere Street";
    string_t town = "Basel";
    number_t number = 42;
    zip_t zip = 4053;

    // Create an address
    address_ptr_t address1 = create_address(name, street, number, zip, town);

    // Copy the address
    address_ptr_t address2 = create_address("Name", "Street", 100, 5, "Town");
    copy_address_by_values(address2, name, street, number, zip, town);

    // Copy the address into the other address
    address_ptr_t address3 = copy_address_by_address(address2, address1);
    printf("The pointer address of the copied address is %p to %p is %p\n", address1, address2, address3);

    // Copy the parameters into the other address
    /*params_t params[] = {&name, &street, &number, &zip, &town};
    copy_address_by_array(address2, params);*/

    // Delete the addresses
    address1 = delete_address(address1);
    address2 = delete_address(address2);
    printf("The pointer addresses of the deleted addresses are %p and %p\n", address1, address2);

    // Create the addresses on the stack
    int studentnumber = 10;
    address_ptr_t stackaddresses[studentnumber];
    for (int i = 0; i < studentnumber; i++) {
        char nameprefix[] = "Name ";
        char realname[50] = {};
        sprintf(realname, "%s%d", nameprefix, i);
        stackaddresses[i] = create_address(realname, "Street", 100, 4000, "Town");
        display_address(stackaddresses[i]);
    }

    // Create the addresses as array on the heap
    address_ptr_t heapaddresses = malloc(studentnumber * sizeof(address_t));
    for (int i = 0; i < studentnumber; i++) {
        char nameprefix[] = "Name ";
        char realname[50] = {};
        sprintf(realname, "%s%d", nameprefix, i);
        copy_address_by_values(&heapaddresses[i], realname, "Street", 100, 4000, "Town");
        display_address(&heapaddresses[i]);
    }

    // Create the addresses as pointer array on the heap
    address_ptr_t *heappointeraddresses = malloc(studentnumber * sizeof(address_ptr_t));
    for (int i = 0; i < studentnumber; i++) {
        char nameprefix[] = "Name ";
        char realname[50] = {};
        sprintf(realname, "%s%d", nameprefix, i);
        heappointeraddresses[i] = create_address(realname, "Street", 100, 4000, "Town");
        display_address(heappointeraddresses[i]);
    }

    // Delete the addresses
    for (int i = 0; i < studentnumber; i++) {
        stackaddresses[i] = delete_address(stackaddresses[i]);
    }

    // Delete the addresses;
    free(heapaddresses);

    // Delete the addresses
    for (int i = 0; i < studentnumber; i++) {
        heappointeraddresses[i] = delete_address(heappointeraddresses[i]);
    }
    return 0;
}
