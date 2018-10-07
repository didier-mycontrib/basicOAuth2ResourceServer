cd /d "%~dp0"

REM avec curl , mode GET par defaut si pas de -d / data
REM             mode POST par defaut avec data (-d ...)
REM             mode PUT si -X PUT et mode DELETE si -X DELETE

REM curl --user myUsername:myPassword ... permet une "BASIC HTTP AUTHENTICATION"
REM curl -H "Authorization: Bearer b1094abc.._ou_autre_jeton" ... si mode "Bearer" / au porteur de jeton

REM si pas d'option -H "Content-Type: application/json" au niveau de la requête
REM alors par défaut logique champ/parametre de formulaire en mode POST
REM avec -d paramName1=valeur1 -d paramName2=valeur2 ... 

set URL=http://localhost:8081/basic-oauth-server/index.html

REM simple GET REQUEST:
REM curl -v %URL% 
REM curl -o out.json %URL%
REM curl %URL%

set GET_ACCESS_TOKEN_URL=clientIdPassword:secret:@localhost:8081/basic-oauth-server/oauth/token

REM en version windows , curl ne gere pas bien les simples quotes 
REM et il faut prefixer les " internes par des \
REM curl %LOGIN_URL% -H "Content-Type: application/json" -d "{\"username\":\"member1\" , \"password\": \"pwd1\"}" 
REM curl %LOGIN_URL% -H "Content-Type: application/json" -d @member1-login-request.json
	curl %GET_ACCESS_TOKEN_URL% -d grant_type=client_credentials
set SPRING-SECURITY-PWD=d8dfc382-e012-491a-8d03-ca6ad9d81083	
	curl %GET_ACCESS_TOKEN_URL% -d grant_type=password -d username=user -d password=%SPRING-SECURITY-PWD%
	
pause