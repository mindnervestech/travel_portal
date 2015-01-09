travelBusiness.controller('HomePageController', function ($scope,$http) {

	$http.get("/searchCountries").success(function(response) {
		
		$scope.searchCountries = response;
	}); 
	


	$scope.onCountryChange = function() {
				console.log("for city");
		$http.get('/searchCities/'+$scope.searchby.country)
		.success(function(data){
			if(data) {
				console.log("----");
				console.log(data);
				$scope.searchCities = [];
				for(var i = 0; i<data.length; i++){
					$scope.searchCities.push({
						cityCode:data[i].id,
						cityName:data[i].name
					});
				}
			} else {
				$scope.searchCities.splice(0);
			}
		});
	}
	
	$http.get("/searchStarrating").success(function(response) {
		$scope.starrating = response;
	});
	
	$scope.searchHotel = function(){
		console.log($scope.searchby);
		
		$http.post('/searchHotelInfo',$scope.searchby).success(function(data){
								
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
		/*$http.get('/searchHotelInfo/'+$scope.searchby.city+"/"+$scope.searchby.checkIn+"/"+$scope.searchby.checkOut+"/"+$scope.searchby.stras+"/"+$scope.searchby.nationality)
		.success(function(response){
			console.log(response);
		});*/
	}
	
	$http.get("/searchNationality").success(function(response) {
		console.log(response);
		$scope.searchNationality = response;
	}); 
	
});


travelBusiness.controller('PageController', function ($scope,$http,ngDialog) {
	
	$scope.init = function(hotellist){
		
		angular.forEach(hotellist, function(obj, index){ ///hotel_profile
			$scope.img = "/searchHotelInfo/getHotelImagePath/"+hotellist[index].supplierCode+"?d="+new Date().getTime();
			hotellist[index].imgPaths = $scope.img;
		});
		console.log(hotellist);
		$scope.hotellistInfo = hotellist;
	}
	
	//$scope.ratedetail=[];
	$scope.dateWiseInfo = function(pIndex,index){
		console.log(index);
		$scope.hotelIF = $scope.hotellistInfo[pIndex];
		console.log($scope.hotelIF);
		$scope.roominfo = $scope.hotellistInfo[pIndex].hotelbyRoom[index];
		console.log($scope.roominfo);
		$scope.rateDatedetail = [];
		
		$http.get('/getDatewiseHotelRoom/'+$scope.hotelIF.checkIn+"/"+$scope.hotelIF.checkOut+"/"+$scope.hotelIF.nationality+"/"+$scope.hotelIF.supplierCode+"/"+$scope.roominfo.roomId)
		.success(function(response){
			console.log(response);
			$scope.ratedetail = response;
			console.log($scope.ratedetail);
			
			angular.forEach($scope.ratedetail.hotelbyDate,function(value,key){
				angular.forEach(value.roomType,function(value1,key1){
					angular.forEach(value1.hotelRoomRateDetail,function(value2,key2){
						angular.forEach(value2.rateDetails,function(value3,key3){
							if(value3.adult == "1 Adult"){
								console.log(value3.rateValue);
								$scope.rateDatedetail.push({
									rate:value3.rateValue,
									meal:value3.mealTypeName,
									date:value3.date
								});
							}							
						});
					  });
				    });
				
				//$scope.rateDatedetail[key].date = value.date;
			     });
					
			console.log($scope.rateDatedetail);
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
		//console.log($scope.ratedetail);
		
		
		
		
		ngDialog.open({
			template: '/assets/resources/html/show_Date_wise_info.html',
			scope : $scope,
			//controller:'hoteProfileController',
			className: 'ngdialog-theme-default'
		});
	}
	
	
	
	$scope.showRate=function(adultValue){
		console.log(adultValue);
		console.log($scope.roominfo);
		
				
		$scope.rateDatedetail = [];
		angular.forEach($scope.ratedetail.hotelbyDate,function(value,key){
			angular.forEach(value.roomType,function(value1,key1){
				angular.forEach(value1.hotelRoomRateDetail,function(value2,key2){
					angular.forEach(value2.rateDetails,function(value3,key3){
						if(value3.adult == adultValue){
							console.log(value3.rateValue);
							$scope.rateDatedetail.push({
								rate:value3.rateValue,
								meal:value3.mealTypeName,
								date:value3.date
							});
						} else {
							$scope.rateDatedetail.push({
								
								date:value3.date
							});
						}
					});
				  });
			    });
			//$scope.rateDatedetail[key]=value.date;
		     });
		
		
		/*angular.forEach($scope.hotelIF.hotelbyDate,function(value1,key1){
			angular.forEach(value1.roomType,function(value2,key2){

				if(value2.roomId == $scope.roominfo.roomId){
					angular.forEach(value2.hotelRoomRateDetail[0].rateDetails,function(value3,key3){
						if(value3.adult == adultValue){
							console.log(value3.rateValue);
							$scope.ratedetail.push({
								rate:value3.rateValue,
								meal:value3.mealTypeName,
								date:value1.date
							});
						}
					});
				}
			});		
			
		});*/
		
		console.log($scope.ratedetail);
		
	}
	
	
});


