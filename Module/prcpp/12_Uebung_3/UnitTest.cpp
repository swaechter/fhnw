#include "UnitTest.h"

QTEST_MAIN(UnitTest)

using namespace std;

void UnitTest::testExampleCode() {
	Vector<double, 4> A({1, 2, 3, 4});
	Vector<double, 4> B({2, 1, 0, 1});
	Vector<double, 4> D;
	auto e = (A - B) * (A + B);
	D = e;
	cout << D << endl;
	// [-3,3,9,15]
	B = {3, 0, 2, 5};
	cout << e << endl;
	// [-8,4,5,-9]
	B = {4, 3, 3, -2};
	cout << e[1] << endl;
	// -5
}

void UnitTest::testSingleBasicOperations() {
#ifdef AUFGABE1
	Vector<double, 5> A({1, 2, 3, 4, 5});
	Vector<double, 5> B({1, 4, 9, 16, 25});
	Vector<double, 5> C({2, 6, 12, 20, 30});

	QVERIFY(A * A == B);
	QVERIFY(B[4] == (A * A)[4]);

	QVERIFY(B / A == A);
	QVERIFY(A[3] == (B / A)[3]);

	QVERIFY(C == A + B);
	QVERIFY(C[2] == (B + A)[2]);

	QVERIFY(C - A == B);
	QVERIFY(A[1] == (C - B)[1]);
#endif
}

void UnitTest::testNestedBasicOperations() {
#ifdef AUFGABE1
	Vector<double, 5> A({1, 2, 3, 4, 5});
	Vector<double, 5> B({1, 4, 9, 16, 25});
	Vector<double, 5> C({2, 6, 12, 20, 30});

	QVERIFY(A * B * C == C * B * A);
	QVERIFY(A * B * C == B * C * A);
	QVERIFY(A * B * C == B * A * C);

	QVERIFY(B * C / A == B / A * C);
	QVERIFY(C * B / A == B * C / A);

	QVERIFY((A + B) * A == C * A);
	QVERIFY(A * C == A * (B + A));
	QVERIFY(A * (A + B) / A == C);
	QVERIFY((C + B) / A == C / A + B / A);

	QVERIFY((A + B) - B == A);
	QVERIFY(A == (A - B) + B);
	QVERIFY((A + B) - (C - A) == A);
	QVERIFY(A + B + B + A == C + C - B + B - (A + A) + A + A);

	QVERIFY((A * B)[1] == (C / A)[4] + (C / A)[0]);
#endif
}

void UnitTest::testSingleScalarProduct() {
#ifdef AUFGABE2
	Vector<double, 5> A({1, 2, 3, 4, 5});
	Vector<double, 5> B({0.5, 1.0, 1.5, 2.0, 2.5});
	Vector<double, 5> C({2, 3, 4, 5, 6});
	Vector<double, 5> D({3, 3, 3, 3, 3});

	QVERIFY(A * 0.5 == B);
	QVERIFY(0.5 * A == B);
	QVERIFY(B == A * 0.5);
	QVERIFY(B == 0.5 * A);

	QVERIFY(A / 2.0 == B);
	QVERIFY((2.0 / A)[3] == B[0]);
	QVERIFY(B == A / 2.0);
	QVERIFY(B[1] == (2.0 / A)[1]);

	QVERIFY(A + 1.0 == C);
	QVERIFY(1.0 + A == C);
	QVERIFY(C == A + 1.0);
	QVERIFY(C == 1.0 + A);

	QVERIFY(C - 1.0 == A);
	QVERIFY((5.0 - A)[1] == C[1]);
	QVERIFY(A == C - 1.0);
	QVERIFY(C[0] == (5.0 - A)[2]);

	auto e = (A + C + Vector<double, 5>({3, 1, -1, -3, -5})) / 3.0;
	QVERIFY((e == Vector<double, 5>({2, 2, 2, 2, 2})));
	auto f = A + D;
	A = f;
	QVERIFY(e == D);

	A[1] = 0;
	QVERIFY(!(e == D));
#endif
}

void UnitTest::testNestedScalarProduct() {
#ifdef AUFGABE2
	Vector<double, 5> A({1, 2, 3, 4, 5});
	Vector<double, 5> B({1, 4, 9, 16, 25});
	Vector<double, 5> C({2, 6, 12, 20, 30});

	QVERIFY(A + B + B + A == 2.0 * C);
	QVERIFY(C * 2.0 == (B + B) + (A + A));
	QVERIFY(3.0 * (A + B) - (B + A) * 2.0 == C);

	QVERIFY(3.0 + (A + B) - 3.0 == C);
	QVERIFY(2.0 * (C - B) / 2.0 + B == (A * 4.0 + 4.0 * B) / 4.0);

	QVERIFY(((A + B) / C == Vector<double, 5>({1, 1, 1, 1, 1})));
	QVERIFY((((A + B) / C) * 9.0)[3] == B[2]);
	QVERIFY(B[4] == (25.0 * ((C + B) / A) / 11.0)[4]);
#endif
}

void UnitTest::testDotProduct() {
#ifdef AUFGABE3
	Vector<double, 5> A({1, 2, 3, 4, 5});
	Vector<double, 5> B({1, 4, 9, 16, 25});
	Vector<double, 5> C({2, 6, 12, 20, 30});
	double d = A * *B;

	QVERIFY(d == B * *A);
	QVERIFY(double(A**B) == 1.0 + 8 + 27 + 64 + 125);
	d = A * *(A + B);
	QVERIFY(d == (B + A) * *A);
	d = (B + A) * *A;
	QVERIFY(A * *(A + B) == d);
	QVERIFY((A * *B) * (A + B) / (B * *A) == C);
	QVERIFY((A * *B) * C - B * (B * *A) == A * (A * *B));
	QVERIFY((A * A) * *(B * B) == (A * B) * *(B * A));
	QVERIFY(A * *B * 2.0 == 2.0 * B * *A);
	d = A * *B * 2.0;
	QVERIFY(d == 2.0 * B * *A);
	d = 2.0 * B * *A;
	QVERIFY(2.0 * B * *A == d);
	d = 2.0 + A * *B - 2.0;
	QVERIFY(d == B * *A);
#endif
}
