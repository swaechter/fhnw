#include <iostream>
#include <MySet.h>

using namespace std;

int main(int argc, char *argv[]) {
	Set s1;
	Set s11(s1);
	const int set2[] = {1, 2, 3};
	Set s2(set2, sizeof(set2) / sizeof(int));
	Set s21(s2);
	Set s22 = s2;
	const int set3[] = {4, 5, 5, 6};
	Set s3(set3, sizeof(set3) / sizeof(int));
	return 0;
}
