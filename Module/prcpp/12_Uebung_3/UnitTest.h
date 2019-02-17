#ifndef UNITTEST_H
#define UNITTEST_H

#include <QtCore/QObject>
#include <QtTest/QTest>
#include <iostream>

#include "Vector.h"
#include "Expression.h"

#define AUFGABE1
#define AUFGABE2
//#define AUFGABE3

using namespace std;

class UnitTest: public QObject {

  Q_OBJECT

  private slots:

	void testExampleCode();
	void testSingleBasicOperations();
	void testNestedBasicOperations();
	void testSingleScalarProduct();
	void testNestedScalarProduct();
	void testDotProduct();
};

#endif // UNITTEST_H
