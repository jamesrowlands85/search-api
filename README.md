To run execute gradle jib jibDockerBuild
Then `docker run --name search-api -d -p 8080:8080 search-api`
Then you should be able to hit:
```
curl -X POST \
  http://localhost:8080/search-for-second-result \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -d '{
	"query": "help"
}'
```
If you do not pass a query you will receive a 400 bad request
If you do not hit the correct path will get a 404 not found

Needs tests for service