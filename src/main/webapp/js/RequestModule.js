var requestModule = (function () {
    return {
        getTask: function start (taskName) {
            return new Promise(function (resolve, reject) {

                var xml = new XMLHttpRequest();
                xml.open('GET', 'http://localhost:9909/webapi/myresource/task/' + taskName, true);
                //  xml.send();
                xml.onreadystatechange = function () {
                    if (xml.readyState != 4) return;
                    if (xml.status >= 200 && xml.status < 300) {
                        console.log('text', xml.responseText);
                        resolve(JSON.parse(xml.responseText));

                    } else {
                        var error = new Error(this.statusText);
                        error.code = this.status;
                        reject(error);

                    }
                }
                xml.onerror = function () {
                    reject(new Error("Network Error"));
                };
                xml.send();
            });
        },

        updateTask: function start (taskDTO) {
            return new Promise(function (resolve, reject) {

                var xml = new XMLHttpRequest();
                xml.open('POST', 'http://localhost:9909/webapi/myresource/update', true);
                xml.setRequestHeader('Content-Type', 'application/json');
                xml.onreadystatechange = function () {
                    if (xml.readyState != 4) return;
                    if (xml.status >= 200 && xml.status < 300) {
                        console.log('text', xml.responseText);
                        resolve();

                    } else {
                        var error = new Error(this.statusText);
                        error.code = this.status;
                        reject(error);
                    }
                }
                xml.onerror = function () {
                    reject(new Error("Network Error"));
                };
                console.dir(JSON.stringify(taskDTO));
                xml.send(taskDTO);
            });
        },


        deleteTask: function start (taskId, taskName) {
            return new Promise(function (resolve, reject) {

                var xml = new XMLHttpRequest();
                xml.open('DELETE', 'http://localhost:9909/webapi/myresource/delete/' + taskId + '/' + taskName, true);
                //  xml.send();
                xml.onreadystatechange = function () {
                    if (xml.readyState != 4) return;
                    if (xml.status >= 200 && xml.status < 300) {
                        //console.log('text', xml.responseText);
                        var txt=xml.responseText;
                        resolve(txt);

                    } else {
                        var error = new Error(this.statusText);
                        error.code = this.status;
                        reject(error);
                    }
                }
                xml.onerror = function () {
                    reject(new Error("Network Error"));
                };
                xml.send();
            });
        },

        createTask: function start (taskDTO) {
            return new Promise(function (resolve, reject) {

                var xml = new XMLHttpRequest();
                xml.open('POST', 'http://localhost:9909/webapi/myresource/create/', true);
                xml.setRequestHeader('Content-Type', 'application/json');
                xml.onreadystatechange = function () {
                    if (xml.readyState != 4) return;
                    if (xml.status >= 200 && xml.status < 300) {
                        console.log('text', xml.responseText);
                        resolve();

                    } else {
                        var error = new Error(this.statusText);
                        error.code = this.status;
                        //reject(error);
                    }
                }
                xml.onerror = function () {
                    reject(new Error("Network Error"));
                };
                //console.dir(JSON.stringify(taskDTO));
                xml.send(taskDTO);
            });                                             // update get || content type || id string
        }
    }
}());

/*updateTask: function (taskDTO) {
    xml.setRequestHeader('Content-Type', 'application/json');
    xml.open('POST', 'http://localhost:9909/webapi/myresource/update', true);
    var body = JSON.stringify(taskDTO);
    var xml = new XMLHttpRequest();
    xml.onreadystatechange = function () {
        if (xml.readyState != 4) return;
    }
    xml.send(body);
    return xml;
},*/

/*deleteTask: function (taskName, taskId) {
    var xml = new XMLHttpRequest();
    xml.onreadystatechange = function () {
        if (xml.readyState != 4) return;
        JSON.parse(xml.responseText);
    }
    xml.open('DELETE', 'http://localhost:9909/webapi/myresource/delete/{taskId}/{taskName}' + taskName + '/' + taskId, true); //++
    xml.send();
    return xml;
},*/