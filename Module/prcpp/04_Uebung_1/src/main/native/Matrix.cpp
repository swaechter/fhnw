#include "Matrix.h"

#include <iostream>
#include <cstring>

using namespace std;

void multiply(jdouble matrix1ptr[], jdouble matrix2ptr[], jdouble matrix3ptr[], jint rows1, jint width1, jint width2) {
    jdouble *index1ptr = matrix1ptr;
    jdouble *index2ptr = matrix2ptr;
    jdouble *index3ptr = matrix3ptr;

    for (int i = 0; i < rows1; i++) {
        for (int j = 0; j < width2; j++) {
            *index3ptr = 0;
            index2ptr = matrix2ptr + j;
            for (int k = 0; k < width1; k++) {
                *index3ptr += *(index1ptr + k) * *(index2ptr);
                index2ptr += width2;
            }
            index3ptr++;
        }
        index1ptr += width1;
    }
}

/*
 * Class:     Matrix
 * Method:    powerC
 * Signature: ([D[DIII)V
 */
JNIEXPORT void JNICALL Java_Matrix_powerC(JNIEnv *env, jobject object, jdoubleArray data1, jdoubleArray data2, jint height, jint width, jint k) {
	jdouble *matrix1ptr = env->GetDoubleArrayElements(data1, 0);
	jdouble *matrix2ptr = env->GetDoubleArrayElements(data1, 0);
	jdouble *matrix3ptr = env->GetDoubleArrayElements(data2, 0);

    jdouble *original1 = matrix1ptr;
    jdouble *original2 = matrix3ptr;

    multiply(matrix2ptr, matrix2ptr, matrix3ptr, height, width, width);

    for(int i = 1; i < k - 1; i++) {
        matrix1ptr = matrix3ptr;
        matrix3ptr = original1;
        multiply(matrix1ptr, matrix2ptr, matrix3ptr, height, width, width);
    }

    memcpy(original2, matrix3ptr, sizeof(jdouble) * (height * width));
	env->ReleaseDoubleArrayElements(data1, original1, 0);
	env->ReleaseDoubleArrayElements(data2, matrix2ptr, 0);
    env->ReleaseDoubleArrayElements(data2, original2, 0);
}

/*
 * Class:     Matrix
 * Method:    multiplyC
 * Signature: ([D[D[DIII)V
 */
JNIEXPORT void JNICALL Java_Matrix_multiplyC(JNIEnv *env, jobject object, jdoubleArray data1, jdoubleArray data2, jdoubleArray data3, jint rows1, jint width1, jint width2) {
	jdouble *matrix1ptr = env->GetDoubleArrayElements(data1, 0);
	jdouble *matrix2ptr = env->GetDoubleArrayElements(data2, 0);
	jdouble *matrix3ptr = env->GetDoubleArrayElements(data3, 0);

    multiply(matrix1ptr, matrix2ptr, matrix3ptr, rows1, width1, width2);

	env->ReleaseDoubleArrayElements(data1, matrix1ptr, 0);
	env->ReleaseDoubleArrayElements(data2, matrix2ptr, 0);
	env->ReleaseDoubleArrayElements(data3, matrix3ptr, 0);
}
