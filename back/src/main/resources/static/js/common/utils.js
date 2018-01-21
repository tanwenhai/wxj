function qs(search) {
  var obj = {};
  $.each(search.substr(1).split('&'), function (k, v) {
    var kv = v.split("=");
    obj[kv[0]] = kv[1];
  });

  return obj;
}

var request = {};
request.post = function (url, data, options) {
  return $.ajax({
    'url': url,
    'type': 'POST',
    'data': JSON.stringify(data),
    'dataType': 'json',
    'contentType': 'application/json'
  });
};

request.get = $.getJSON;