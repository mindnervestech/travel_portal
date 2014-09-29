angular.module('travel_portal_hotel_profile').
	controller("generalInfoProfileController",['$scope', '$http',function($scope, $http){
	
	$scope.generalInfo = {};
	$scope.countries = [];
	$scope.cities = [];
	$http.get("/countries").success(function(response) {
		console.log("getting countries...");
		$scope.countries = response;
	});                

	$scope.savegeneralinfo = function() {
		console.log($scope.generalInfo);
		$http.post('/saveGeneralInfo', $scope.generalInfo).success(function(data){
			console.log('success');
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	};

	$scope.onCountryChange = function() {
		console.log($scope.generalInfo.countryCode.countryCode);
		$http.get('/cities/'+$scope.generalInfo.countryCode.countryCode)
		.success(function(data){
			if(data) {
				$scope.cities = data;
				console.log($scope.cities);
			} else {
				$scope.cities = [];
			}
		});

	}


}]);
