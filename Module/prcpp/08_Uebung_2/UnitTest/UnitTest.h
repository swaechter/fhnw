#ifndef UNITTEST_H
#define UNITTEST_H

#include <QtCore/QObject>
#include <QtTest/QTest>

class UnitTest : public QObject {

Q_OBJECT

private slots:

	void testCreation();

	void testContains();

	void testEmpty();

	void testSize();

	void testMerge();

	void testDifference();

	void testIntersect();

	void testMoveSemantics();

	void testCreationOrderedSet();

	void testSmallerOrderedSet();

	void testLargerOrderedSet();

	void testMergeOrderedSet();
};

#endif // UNITTEST_H
