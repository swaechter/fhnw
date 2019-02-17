#! /bin/bash
cd "$(dirname "$0")"

# Delete the library
rm -f ../../../libmatrix.so

# Generate the JNI header
javah -classpath ../java -o Matrix.h Matrix

# Create the build directory
mkdir -p build

# Comppile the library
pushd build
cmake .. -DCMAKE_BUILD_TYPE=Release
make
popd
