#!/bin/sh
# apk源文件路径
origin=$1
output=$2
java -Djava.library.path=. -jar ./signapk.jar ./platform.x509.pem ./platform.pk8 ${origin} ${output}