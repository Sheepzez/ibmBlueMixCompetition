"use strict";
// request message on server
function getResults() {
	var data = document.getElementById("text").value;
	xhrPost(dataUrl, "text", data, function(responseText){
		document.getElementById('input').innerHTML = "";
		document.getElementById('result').innerHTML = resultHeader + responseText;
	}, function(err){
		console.log(err);
	});
}

//utilities
function createXHR(){
	if(typeof XMLHttpRequest != 'undefined'){
		return new XMLHttpRequest();
	}else{
		try{
			return new ActiveXObject('Msxml2.XMLHTTP');
		}catch(e){
			try{
				return new ActiveXObject('Microsoft.XMLHTTP');
			}catch(e){}
		}
	}
	return null;
}
function xhrGet(url, key, data, callback, errback){
	var xhr = new createXHR();
	xhr.open("GET", url + "?" + key + "=" + encodeURIComponent(data), true);
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){
			if(xhr.status == 200){
				callback(xhr.responseText);
			}else{
				errback('service not available');
			}
		}
	};
	xhr.timeout = 3000;
	xhr.ontimeout = errback;
	xhr.send();
}

function xhrPost(url, key, data, callback, errback) {
	var xhr = new createXHR();
	xhr.open("POST", url, true);
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){
			if(xhr.status == 200){
				callback(xhr.responseText);
			}else{
				errback('service not available');
			}
		}
	};
	xhr.ontimeout = errback;
	xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhr.send(key + "=" + encodeURIComponent(data));
}
function parseJson(str){
	return window.JSON ? JSON.parse(str) : eval('(' + str + ')');
}
function prettyJson(str){
	// If browser does not have JSON utilities, just print the raw string value.
	return window.JSON ? JSON.stringify(JSON.parse(str), null, '  ') : str;
}

