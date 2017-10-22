@echo off
setlocal

cd ../../

call mvn -DfailIfNoTests=false -Dtest=**/integration/**/* test
endlocal

pause