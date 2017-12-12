var http = require('http')
  , fs   = require('fs')
  , url  = require('url')
  , port = 8080;

var server = http.createServer (function (req, res) {
  var uri = url.parse(req.url)

  switch( uri.pathname ) {
    case '/':
      sendFile(res, 'public/index.html')
      break
    case '/index.html':
      sendFile(res, 'public/index.html')
      break
    case '/public/img/clayton.png':
      sendFile(res, 'public/img/clayton.png')
      break
    case '/public/img/jon.png':
      sendFile(res, 'public/img/jon.png')
      break
    case '/public/css/style.css':
      sendFile(res, 'public/css/style.css', 'text/css')
      break
    case '/public/img/joshua.png':
      sendFile(res, 'public/img/joshua.png')
      break
    case '/public/img/karsten.png':
      sendFile(res, 'public/img/karsten.png')
      break
    case '/public/img/krystal.png':
      sendFile(res, 'public/img/krystal.png')
      break
    case '/public/img/mitchell.png':
      sendFile(res, 'public/img/mitchell.png')
      break
    case '/public/img/niko.png':
      sendFile(res, 'public/img/niko.png')
      break
    case '/public/img/sai.png':
      sendFile(res, 'public/img/sai.png')
      break
    case '/public/img/thomas.png':
      sendFile(res, 'public/img/thomas.png')
      break
    case '/public/img/tung.png':
      sendFile(res, 'public/img/tung.png')
      break
    case '/public/img/wong.png':
      sendFile(res, 'public/img/wong.png')
      break
    case '/public/img/yianni.png':
      sendFile(res, 'public/img/yianni.png')
      break
    




  default:
      res.end('404 not found')
  }
})

server.listen(process.env.PORT || port);
console.log('listening on 8080')

// subroutines

function sendFile(res, filename, contentType) {
  contentType = contentType || 'text/html';

  fs.readFile(filename, function(error, content) {
    res.writeHead(200, {'Content-type': contentType})
    res.end(content, 'utf-8')
  })

}
