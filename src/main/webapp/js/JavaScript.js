var Model = function (){

}
Model.prototype = {
    set data (data){

        this._data = data;

        var i = 0;
        var h=0;
        var n=0;
        this._map = new Map();
        this._children = new Map();
        while (i < this.data.tasks.entry.length){
            var str = JSON.stringify(this.data.tasks.entry[i].key.task.id);
            this.map.set(str,this.data.tasks.entry[i].key);
            this.children.set(str,this.data.tasks.entry[i].value);
            i++;
        }
        this.listener();
    },
    get data () {return this._data;},
    set listener(listener){this._listener = listener;},
    get listener () {return this._listener;},
    set map (map) {this._map = map;},
    set children (children) {this._children = children;},
    get map () {return this._map;},
    get children () {return this._children;}
}
Model.prototype.show = function(){
    console.log(this.data);
    console.log(this.listener);
}

Model.prototype.toString = function(){
    return this.data;

}
Model.prototype.addMap = function(i,Id){
    this._map[i] = Id;
}

Model.prototype.getElementById = function(key){
//key = '{"height":1,"number":1}';
    var task = {};
    if (this.map.has(key)){
        task = this.map.get(key);
    }
    return task;
}

Model.prototype.getChildren = function(key){
    var tasks = null;
    if (this.children.has(key)){
        tasks = this.children.get(key);
    }
    return tasks;
}

Model.prototype.deleteElement = function(key){
    if (this.map.has(key)){
        data = this.data;
        var i = 0;
        while(i<data.tasks.entry.length){
            if(JSON.stringify(data.tasks.entry[i].key.task.id)===(key)){
                var k = i;
                data.subTasks.entry.splice(k,1);
                this.map.delete(key);
                console.log(this.map);
            }
            i++;
        }
        this.data = data;
    }
}

Model.prototype.getHead = function(){
    return(model.getElementById('{"height":1,"number":1}'));
}

Model.prototype.showMap = function(){
    console.log('map', this.map);
}

Model.prototype.showChildren = function() {
    console.log('children', this.children);
}

Model.prototype.getTreeChildren = function(id){

    var childrenResult = this.getChildren(id);
    if (childrenResult!=null&&childrenResult!=""){
        var str = "[";
        var children = jQuery.makeArray(JSON.parse(childrenResult));
        var i = 0;
        while (i<children.length){
            var elem = this.getElementById(JSON.stringify(children[i].task.id));
            if(this.getChildren(JSON.stringify(children[i].task.id))!=""){

                var child = [{"name": elem.task.name
                    ,"parent": this.getElementById(id).task.name,
                    "children": this.getTreeChildren(JSON.stringify(elem.task.id))
                }];
//console.log('tytyt',JSON.stringify(child));
                str = str + JSON.stringify(child);
            }else{
                var child = [{"name": elem.task.name
                    ,"parent": this.getElementById(id).task.name
                }];
                str = str + JSON.stringify(child);
            }
            i++;
        }
    }
    return str + "]";
}

var obj=null;
var model=new Model(null,null);
model.show();
model.listener = function (){
    model.show();
}

var shw = document.getElementById("shw");

//перезаполнение модели и заполнение таблицы
function refresh() {
    var promise = requestModule.getTask(1).then(function (obj) {
        console.dir(JSON.stringify(obj));
        model.data=obj;
        fillTable(model.data.tasks.entry);
        model.getTreeChildren(1);
        document.getElementById('inputHeight').value = '';
        document.getElementById('inputNumber').value = '';
        document.getElementById('inputName').value = '';
        document.getElementById('inputExecutor').value = '';
    });
}

shw.onclick = function (ev4) {
    var promise = requestModule.getTask(taskName.value).then(function(obj) {
        console.log('obj',obj);
        model.data=obj;
    });
}

/*
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
*/

document.addEventListener("DOMContentLoaded", function(event) {
    var promise = requestModule.getTask(1).then(function (obj) {
        console.dir(model.data);
        refresh();
    });
});
var tbl=[
    {"name": "Eve",   "parent": ""},
    {"name": "Cain",  "parent": "Eve"},
    {"name": "Seth",  "parent": "Eve"},
    {"name": "Enos",  "parent": "Seth"},
    {"name": "Noam",  "parent": "Seth"},
    {"name": "Abel",  "parent": "Eve"},
    {"name": "Awan",  "parent": "Eve"},
    {"name": "Enoch", "parent": "Awan"},
    {"name": "Azura", "parent": "Eve"}
]

function fillTable(t) {
    console.dir(t);
    var tasks = t;
    console.dir(JSON.stringify(tasks));
    var tasksList = document.getElementById("tasksList");
    tasksList.innerHTML='';
    for (var j = 0; j < tasks.length; j++) {
        var tr = document.createElement('TR');
        var fields = ['height', 'number', 'name', 'executor', 'spentTime', 'status'];
        for (var i = 0; i < fields.length; i++) {
            var td = document.createElement('TD');
            //var val=(tasks[j]['value']!='')?JSON.parse(tasks[j]['value']):'';
            //var parentId=(tasks[j]['parentId']!='')?JSON.parse(tasks[j]['parentId']):'';
            switch (fields[i]) {
                case 'height':
                    td.innerHTML = (tasks[j]['key']['task']['id']).height;
                    break;
                case 'number':
                    td.innerHTML = (tasks[j]['key']['task']['id']).number;
                    break;
/*                case 'parentId':
                    td.innerHTML = parentId?parentId.height:'';
                    break;
                case 'parentId':
                    td.innerHTML = parentId?parentId.number:'';
                    break;*/
                case 'name':
                    td.innerHTML = (tasks[j]['key']['task']).name;
                    break;
                case 'executor':
                    td.innerHTML = (tasks[j]['key']['task']).executor;
                    break;
                case 'spentTime':
                    td.innerHTML = (tasks[j]['key']['task']).spentTime;
                    break;
                case 'status':
                    td.innerHTML = (tasks[j]['key']['task']).status;
                    break;
            }
            /*if (i<2) {
                    var pId = tasks[j]['key']['parentId']?JSON.parse(tasks[j]['key']['parentId']):''; //выдираем значения из строки Id
                    td.innerHTML = pId[fields[i]] || '';
            }
            else {
                td.innerHTML = tasks[j]['key']['task'][fields[i]];
            }*/
            td.setAttribute('data-field', fields[i]);

            tr.appendChild(td);
        }
        var tdDel = document.createElement('TD');
        tdDel.innerHTML = '<span class="delrow">&#10008;</span>';
        tr.appendChild(tdDel);
        tasksList.appendChild(tr);
    }
}

//обратчик нажатия
var tasksList = document.querySelector('#tasksList');
tasksList.onclick = function (event) {
    if (event.target.tagName === 'TD'){
        var dataRow = event.target.parentElement;
        for (var i = 0; i < dataRow.childNodes.length; i++){
            console.dir(dataRow.childNodes[i]);
            if (dataRow.childNodes[i].getAttribute('data-field') == 'executor') {
                document.getElementById("inputExecutor").value = dataRow.childNodes[i].innerHTML;
            }
            if (dataRow.childNodes[i].getAttribute('data-field') == 'name') {
                document.getElementById("inputName").value = dataRow.childNodes[i].innerHTML;
            }
            if (dataRow.childNodes[i].getAttribute('data-field') == 'number') {
                document.getElementById("inputNumber").value = dataRow.childNodes[i].innerHTML;
            }
            if (dataRow.childNodes[i].getAttribute('data-field') == 'height') {
                document.getElementById("inputHeight").value = dataRow.childNodes[i].innerHTML;
            }
        }
    }
    if (event.target.classList.contains('delrow')){
        var taskRow = event.target.parentElement.parentElement;
        var height = taskRow.cells[0].innerHTML;
        var number = taskRow.cells[1].innerHTML;
        var name = taskRow.cells[2].innerHTML;

        var taskId = JSON.stringify({"height":height,"number":number});
        requestModule.deleteTask(taskId, name);
        refresh();
    }
}
function showMsg(msgText) {
    var msgDiv = document.getElementById('msg');
    msgDiv.style.display = 'block';
    msgDiv.innerHTML = msgText;
    setTimeout(function () {
        msgDiv.style.display = 'none';

    }, 3000);
}

//переменные
var taskExecutor = document.getElementById("inputExecutor");
var taskNum = document.getElementById("inputNumber");
var taskHeight = document.getElementById("inputHeight");
var taskName = document.getElementById("inputName");

// update
var updt = document.getElementById("updt");
updt.onclick = function (ev3) {
    alert(taskExecutor.value + " " + taskName.value + " " + taskHeight.value + " " + taskNum.value);
    var taskId = {"height":taskHeight.value,"number":taskNum.value};
    var taskDTO = JSON.stringify({"task":{"id": taskId,"name":taskName.value,"executor":taskExecutor.value,"spentTime":0,"status":"NOT_STARTED"},"parentId":{} ,"taskTreeName":"create tasktreenamemethod"});
    alert(taskDTO);
    var task = requestModule.updateTask(taskDTO).then(refresh());
    console.dir(JSON.stringify(task));
}

//удаление task
var dlt = document.getElementById("dlt");
dlt.onclick = function (ev2) {
    var taskId = JSON.stringify({"height":taskHeight.value,"number":taskNum.value});
    var task = requestModule.deleteTask(taskId, taskName.value).then(refresh());
    console.dir(task);
}

//create
var add = document.getElementById("addTask");
add.onclick = function (ev7) {
    var parent = JSON.stringify({"height":taskHeight.value, "number":taskNum.value});
    var taskDTO = JSON.stringify({"task":{"id":{},"name":taskName.value,"executor":taskExecutor.value,"spentTime":0,"status":"NOT_STARTED"},"parentId":parent ,"taskTreeName":"create tasktreenamemethod"});
    //var taskDTO =JSON.stringify({"task":{"id":{"height":3,"number":1},"name":"3","executor":"subsubTask","spentTime":0,"status":"NOT_STARTED"},"parentId":"{\"height\":2, \"number\":1}","taskTreeName":"create tasktreenamemethod"});
    var task = requestModule.createTask(taskDTO).then(refresh());
}


