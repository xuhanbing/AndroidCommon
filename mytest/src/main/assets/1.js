function show() {
	var e = document.getElementById("text");
	window.test.print(e.value);
}

function show(string) {
	console.log("string from android:" + string);
	var e = document.getElementById("text");
	window.test.print(e.value);
}

var table_name = "info";
function initDatabase() {
	try {
		if (!window.openDatabase) {
			alert('Databases are not supported by your browser');
		} else {
			var shortName = 'db';
			var version = '1.0';
			var displayName = 'yarin db';
			var maxSize = 100000; // in bytes
			db = openDatabase(shortName, version, displayName, maxSize);
			createTables();
			selectAll();
		}
	} catch (e) {
		if (e == 2) {
			// Version mismatch.
			console.log("Invalid database version.");
		} else {
			console.log("Unknown error " + e + ".");
		}
		return;
	}
}

function createTables() {
	db
			.transaction(function(transaction) {
				transaction
						.executeSql(
								'CREATE TABLE IF NOT EXISTS  ' + table_name + '(id INTEGER NOT NULL PRIMARY KEY autoincrement, name TEXT NOT NULL,desc TEXT NOT NULL,time text not null);',
								[], nullDataHandler, errorHandler);
			});
}

function insertData() {
	count = 1
	db
			.transaction(function(transaction) {
				// Starter data when page is initialized
				for (i = 0; i < count; i++) {
					name = "xxx";
					var data = [ name, 'I am ' + name,
							new Date().toGMTString() ];

					transaction
							.executeSql(
									"INSERT INTO " + table_name + "(name, desc, time) VALUES (?, ?, ?)",
									[ data[0], data[1], data[2] ]);
				}

			});
	
	selectAll();
}

function errorHandler(transaction, error) {
	if (error.code == 1) {
		// DB Table already exists
	} else {
		// Error is a human-readable string.
		console.log('Oops.  Error was ' + error.message + ' (Code '
				+ error.code + ')');
		dataSelectHandler(transaction, new Array());
	}
	return false;
}

function nullDataHandler() {
	console.log("SQL Query Succeeded");
}

function selectAll() {
	db.transaction(function(transaction) {
		transaction.executeSql("SELECT * FROM " + table_name + ";", [], dataSelectHandler,
				errorHandler);
	});
}

function dataSelectHandler(transaction, results) {
	// Handle the results
	table = document.getElementById("table");
	table.createCaption().innerHTML="查询到结果";
	count = table.rows.length;
	// 删除所有
//	for ( ; 1 < table.rows.length;) {
//		table.deleteRow(table.rows。length-1);
//	}
	
	
	for ( var i = 0; i < results.rows.length; i++) {
		var row = results.rows.item(i);
		var newFeature = new Object();
		newFeature.id = row['id'];
		newFeature.name = row['name'];
		newFeature.decs = row['desc'];
		newFeature.time = row['time'];

		index = table.rows.length;
		var row = table.insertRow(index);
		/*
		 * for (j=0;j<newFeature.length;j++) {
		 * row.insertCell(j).innerHTML=newFeature[j]; }
		 */
		row.insertCell(0).innerHTML = newFeature.id;
		row.insertCell(1).innerHTML = newFeature.name;
		row.insertCell(2).innerHTML = newFeature.decs;
		row.insertCell(3).innerHTML = newFeature.time;

		// document.getElementById("id").innerHTML = "id:"
		// + newFeature.id;
		// document.getElementById("name").innerHTML = "name:"
		// + newFeature.name;
		// document.getElementById("desc").innerHTML = "desc:"
		// + newFeature.decs;
		// document.getElementById("time").innerHTML = "time:"
		// + newFeature.time;
	}
}


function updateData(id) {
	db.transaction(function(transaction) {
		var data = [ 'mod', 'I am mod' ];
		transaction.executeSql("UPDATE " + table_name + " SET name=?, desc=? WHERE id = ?",
				[ data[0], data[1], id]);
	});
	selectAll();
}

function deleteData(id) {
	db.transaction(function(transaction) {
		transaction.executeSql("delete from " +  table_name + " where id=?", [id], nullDataHandler,
				errorHandler);
	});
	selectAll();
}

function ddeleteTables() {
	db.transaction(function(transaction) {
		transaction.executeSql("DROP TABLE " + table_name +";", [], nullDataHandler,
				errorHandler);
	});
	console.log("Table 'page_settings' has been dropped.");
	selectAll();
}

function initLocalStorage() {
	if (window.localStorage) {
		textarea = document.getElementById("textarea");
		value = localStorage["value"];
		if (""!=value && undefined!=value)
			textarea.value = localStorage["value"];
		
		textarea.addEventListener("keyup", function() {
			window.localStorage["value"] = this.value;
			window.localStorage["time"] = new Date().getTime();
		}, false);
	} else {
		alert("LocalStorage are not supported in this browser.");
	}
}

window.onload = function() {
	initDatabase();
	initLocalStorage();
	get_location();
}

// 获取当前地理位置
// navigator.geolocation.getCurrentPosition(success_callback_function,
// error_callback_function, position_options)
// 持续获取地理位置
// navigator.geolocation.watchPosition(success_callback_function,
// error_callback_function, position_options)
// 清除持续获取地理位置事件
// navigator.geolocation.clearWatch(watch_position_id)

// 定位
function get_location() {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(show_map, handle_error, {
			enableHighAccuracy : true,
			maximumAge : 1000,
			timeout : 15000
		});
	} else {
		alert("Your browser does not support HTML5 geoLocation");
	}
}

function show_map(position) {
	var latitude = position.coords.latitude;
	var longitude = position.coords.longitude;
	var city = position.coords.city;
	// telnet localhost 5554
	// geo fix -82.411629 28.054553
	// geo fix -121.45356 46.51119 4392
	// geo nmea
	// $GPGGA,001431.092,0118.2653,N,10351.1359,E,0,00,,-19.6,M,4.1,M,,0000*5B
	document.getElementById("Latitude").innerHTML = "latitude:" + latitude;
	document.getElementById("Longitude").innerHTML = "longitude:" + longitude;
	document.getElementById("City").innerHTML = "city:" + city;
}

function handle_error(err) {
	switch (err.code) {
	case 1:
		alert("permission denied");
		break;
	case 2:
		alert("the network is down or the position satellites can't be contacted");
		break;
	case 3:
		alert("time out");
		break;
	default:
		alert("unknown error");
		break;
	}
}
