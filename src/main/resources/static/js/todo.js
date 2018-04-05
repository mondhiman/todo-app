var todoApp = angular.module('todoApp', []);
todoApp.controller('todoController', function($scope, $http){
	$scope.tasks = [];
	$scope.filterDone = true;
	$scope.filterNotDone = true;
	$scope.addTask = function(newtask){
		if(newtask==undefined || newtask.trim()=='') return;
		var task = {}; task.name = newtask;
		$http.post("/api", task).then(function(response){
			console.log('inserted!');
			$scope.tasks.push(response.data);
		});
		$scope.newtask = '';
	};
	$scope.deleteTask = function(index, task){
		$scope.tasks.splice(index, 1);
		$http.delete("/api/"+task.id).then(function(){
			console.log('deleted!');
		});
	};
	$scope.selectTasks = function(){
		$http.get("/api").then(function(response){
			$scope.tasks = response.data;
			console.log("selected: "+$scope.tasks.length);
		});
	};
	$scope.filterTasks = function(task){
		if($scope.filterDone && task.done) return true;
		else if($scope.filterNotDone && !task.done) return true;
		else return false;
	};
	$scope.selectTasks();
});

todoApp.controller('listController', function($scope, $http){
	$scope.edit = false;
	$scope.editTask = function(task){
		$http.put("/api", task).then(function(){
			console.log('updated!');
		});
	};
	$scope.toggleEdit = function($scope){
		$scope.edit = !$scope.edit; 
	};
	$scope.toogleDone = function(task){
		task.done = !task.done;
		$scope.editTask(task);
	}
});

