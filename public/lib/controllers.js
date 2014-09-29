angular.module('travel_portal_hotel_profile').controller("generalInfoProfileController",['$scope', '$http',function($scope, $http,getCityNameservice){
	   
	
	
	$scope.countries = [];
	   $http.get("/countries").success(function(response) {
		   console.log("getting countries...");
		   $scope.countries = response;
	   });                
	   
	   
	   $scope.savegeneralinfo = function() {
			console.log($scope.generalInfo);
			$http.post('/saveGeneralInfo', $scope.generalInfo).success(function(data){
				console.log('success');
				/*$scope.searchBasicRate(currentPage);
				$('#myModal').modal('hide');*/
			}).error(function(data, status, headers, config) {
				console.log('ERROR');
			});
		};
	   
	    $scope.onStateselect = function() {
			console.log($scope.generalInfo.countryCode.countryCode);
			
			$http.get('getCityname/'+$scope.generalInfo.countryCode.countryCode)
			.success(function(data){
				if(data) {
					$scope.cityname = data;
					console.log($scope.cityname);
					
				} else {
					$scope.cityname = [];
				}
			});
			
			}
	   
	   
    }]);
