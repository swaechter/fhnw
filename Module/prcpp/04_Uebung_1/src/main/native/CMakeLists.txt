cmake_minimum_required(VERSION 3.0)
project(matrix)

find_package(JNI REQUIRED)

add_library(matrix SHARED Matrix.cpp)
target_include_directories(matrix PUBLIC ${JAVA_INCLUDE_PATH})
target_include_directories(matrix PUBLIC ${JAVA_INCLUDE_PATH2})
set_target_properties(matrix PROPERTIES LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/../../../)
