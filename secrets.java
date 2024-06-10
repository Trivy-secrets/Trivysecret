@@ -7,7 +7,7 @@ const port = 3000;
const aws_access_key_id='AKIAIO5FODNN7EXAMPLE';
const server = http.createServer((req, res) => {
  res.statusCode = 200;
  res.setHeader('Content-Type', 'text/plain');
  res.end('Hello World1');
  res.end('Hello World100');
});
