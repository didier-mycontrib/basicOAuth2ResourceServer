cd /d "%~dp0"

set URL=http://localhost:8082/basic-oauth-resource/index.html
set URL2=http://localhost:8082/basic-oauth-resource/myapi/info/1

curl  %URL%
curl  %URL2%
	
pause