#include "UnitTest.h"

#include <iostream>
#include <MySet.h>
#include <OrderedSet.h>

using namespace std;

QTEST_MAIN(UnitTest)

void UnitTest::testCreation() {
	Set s1;
	Set s11(s1);
	const int set2[] = {1, 2, 3};
	Set s2(set2, sizeof(set2) / sizeof(int));
	Set s21(s2);
	Set s22 = s2;
	const int set3[] = {4, 5, 5, 6};
	Set s3(set3, sizeof(set3) / sizeof(int));

	QVERIFY(s11 == s1);

	QVERIFY(s21 == s2);
	QVERIFY(s22 == s2);

	QVERIFY(s1.size() == 0);

	QVERIFY(s2.size() == 3);
	QVERIFY(s2[0] == 1);
	QVERIFY(s2[1] == 2);
	QVERIFY(s2[2] == 3);

	QVERIFY(s3.size() == 3);
	QVERIFY(s3[0] == 4);
	QVERIFY(s3[1] == 5);
	QVERIFY(s3[2] == 6);
}

void UnitTest::testContains() {
	const int set1[] = {1, 2, 3};
	Set s1(set1, sizeof(set1) / sizeof(int));
	QVERIFY(s1.contains(2));
	QVERIFY(!s1.contains(0));
	QVERIFY(!Set().contains(1));

	const int set2[] = {2, 1};
	Set s2(set2, sizeof(set2) / sizeof(int));
	QVERIFY(s1.containsAll(s2));
	QVERIFY(!s2.containsAll(s1));
	QVERIFY(!Set().containsAll(s1));
	QVERIFY(s1.containsAll(Set()));
	QVERIFY(Set().containsAll(Set()));
}

void UnitTest::testEmpty() {
	QVERIFY(Set().isEmpty());

	const int set1[] = {1, 2, 3};
	Set s1(set1, sizeof(set1) / sizeof(int));
	QVERIFY(!s1.isEmpty());
}

void UnitTest::testSize() {
	QVERIFY(Set().size() == 0);

	const int set1[] = {1, 2, 3};
	Set s1(set1, sizeof(set1) / sizeof(int));
	QVERIFY(s1.size() == 3);
}

void UnitTest::testMerge() {
	const int set1[] = {1, 2, 3};
	Set s1(set1, sizeof(set1) / sizeof(int));
	const int set2[] = {3, 2, 4};
	Set s2(set2, sizeof(set2) / sizeof(int));
	const int set3[] = {1, 2, 3, 4};
	Set s3(set3, sizeof(set3) / sizeof(int));

	QVERIFY(Set::merge(s1, s2) == s3);
	QVERIFY(Set::merge(s1, Set()) == s1);
	QVERIFY(Set::merge(Set(), s1) == s1);
	QVERIFY(Set::merge(Set(), Set()) == Set());
}

void UnitTest::testDifference() {
	const int set1[] = {1, 2, 3};
	Set s1(set1, sizeof(set1) / sizeof(int));
	const int set2[] = {3, 2, 4};
	Set s2(set2, sizeof(set2) / sizeof(int));
	const int set3[] = {1};
	Set s3(set3, sizeof(set3) / sizeof(int));
	const int set4[] = {4};
	Set s4(set4, sizeof(set4) / sizeof(int));

	QVERIFY(Set::difference(s1, s2) == s3);
	QVERIFY(Set::difference(s2, s1) == s4);
	QVERIFY(Set::difference(Set(), s1) == Set());
	QVERIFY(Set::difference(s1, Set()) == s1);
	QVERIFY(Set::difference(Set(), Set()) == Set());
}

void UnitTest::testIntersect() {
	const int set1[] = {1, 2, 3};
	Set s1(set1, sizeof(set1) / sizeof(int));
	const int set2[] = {3, 2, 4};
	Set s2(set2, sizeof(set2) / sizeof(int));

	QVERIFY(Set::intersection(s1, s2) == Set::intersection(s2, s1));
	QVERIFY(Set::intersection(Set(), s1) == Set());
	QVERIFY(Set::intersection(s1, Set()) == Set());
	QVERIFY(Set::intersection(Set(), Set()) == Set());
}

void UnitTest::testMoveSemantics() {
	// test move intersection
	const int set1[] = {1, 2, 3};
	Set s1(set1, sizeof(set1) / sizeof(int));
	const int set2[] = {3, 2, 4};
	Set s2(set2, sizeof(set2) / sizeof(int));
	const int set3[] = {2, 3};
	Set s3(set3, sizeof(set3) / sizeof(int));
	QVERIFY(Set::intersection(s1, std::move(s2)) == s3);
	QVERIFY(s2 == s3);
	QVERIFY(s2.size() == s3.size());

	// test move difference
	// s1 und s3 sollten nicht veraendert worden sein, lediglich s2
	const int set4[] = {1};
	Set s4(set4, sizeof(set4) / sizeof(int));
	QVERIFY(Set::difference(std::move(s1), std::move(s3)) == s4);
	QVERIFY(s1 == s4);
	QVERIFY(s1.size() == s4.size());
}

void UnitTest::testCreationOrderedSet() {
	OrderedSet s1;
	OrderedSet s11(s1);
	const int set2[] = {3, 2, 1};
	OrderedSet s2(set2, sizeof(set2) / sizeof(int));
	OrderedSet s21(s2);
	OrderedSet s22 = s2;
	const int set3[] = {4, 5, 6, 5};
	OrderedSet s3(set3, sizeof(set3) / sizeof(int));

	QVERIFY(s11 == s1);

	QVERIFY(s21 == s2);
	QVERIFY(s22 == s2);

	QVERIFY(s2.size() == 3);
	QVERIFY(s2[0] == 1);
	QVERIFY(s2[1] == 2);
	QVERIFY(s2[2] == 3);

	QVERIFY(s3.size() == 3);
	QVERIFY(s3[0] == 4);
	QVERIFY(s3[1] == 5);
	QVERIFY(s3[2] == 6);
}

void UnitTest::testSmallerOrderedSet() {
	const int set1[] = {1, 2, 5, 6, 7, 8};
	OrderedSet s1(set1, sizeof(set1) / sizeof(int));

	const int set2[] = {1, 2, 5};
	OrderedSet s2(set2, sizeof(set2) / sizeof(int));

	OrderedSet s3 = s1.getSmaller(6);

	QVERIFY(s3.size() == 3);
	QVERIFY(s3 == s2);
	QVERIFY(s3[0] == 1);
	QVERIFY(s3[1] == 2);
	QVERIFY(s3[2] == 5);
}

void UnitTest::testLargerOrderedSet() {
	const int set1[] = {1, 2, 5, 6, 7, 8};
	OrderedSet s1(set1, sizeof(set1) / sizeof(int));

	const int set2[] = {6, 7, 8};
	OrderedSet s2(set2, sizeof(set2) / sizeof(int));

	OrderedSet s3 = s1.getLarger(5);

	QVERIFY(s3.size() == 3);
	QVERIFY(s3 == s2);
	QVERIFY(s3[0] == 6);
	QVERIFY(s3[1] == 7);
	QVERIFY(s3[2] == 8);
}

void UnitTest::testMergeOrderedSet() {
	const int set1[] = {1, 2, 3};
	OrderedSet s1(set1, sizeof(set1) / sizeof(int));
	const int set2[] = {2, 3, 4};
	OrderedSet s2(set2, sizeof(set2) / sizeof(int));
	const int set3[] = {1, 2, 3, 4};
	OrderedSet s3(set3, sizeof(set3) / sizeof(int));

	QVERIFY(OrderedSet::merge(s1, s2) == s3);
	QVERIFY(OrderedSet::merge(s2, s1) == s3);
	QVERIFY(OrderedSet::merge(s1, Set()) == s1);
	QVERIFY(OrderedSet::merge(OrderedSet(), s1) == s1);

	QVERIFY(OrderedSet::merge(s3, s1) == s3);
	QVERIFY(OrderedSet::merge(s3, s2) == s3);

	QVERIFY(OrderedSet::merge(OrderedSet(), Set()) == Set());
	QVERIFY(OrderedSet::merge(OrderedSet(), OrderedSet()) == OrderedSet());
}
