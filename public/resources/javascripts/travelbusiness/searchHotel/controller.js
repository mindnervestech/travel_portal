travelBusiness.controller('HomePageController', function ($scope,$http,$filter) {

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


travelBusiness.controller('PageController', function ($scope,$http,$filter,ngDialog) {
	
	var sortdata = 1;
   $scope.sortData = function(){
	  console.log(sortdata);
	   if(sortdata == 0){
		   sortdata = 1;
		   $scope.sortdata = "minRate";
	   }else{
		   sortdata = 0;
		   $scope.sortdata = "-minRate";
	   }
	}
	
	
	$scope.init = function(hotellist){
		
		angular.forEach(hotellist, function(obj, index){ ///hotel_profile
			$scope.img = "/searchHotelInfo/getHotelImagePath/"+hotellist[index].supplierCode+"?d="+new Date().getTime();
			hotellist[index].imgPaths = $scope.img;
			/*var arr = hotellist[index].currencyName.split(" - ");
			hotellist[index].currencyShort = arr[0];*/
			
			var minRate = 0;
			angular.forEach(hotellist[index].hotelbyRoom,function(value,key){
				angular.forEach(value.hotelRoomRateDetail,function(value1,key1){
					angular.forEach(value1.rateDetailsNormal,function(value2,key2){
						console.log(key);
						
						if(value2.adult == "1 Adult"){
							if(key == 0){
								minRate = value2.rateAvg;
							}
							console.log(value2.rateAvg);
							if(value2.rateAvg < minRate){
							    	minRate = value2.rateAvg;
							}							
						}
						
					});
					
				});	
				
			});
			hotellist[index].minRate = minRate;
			
		});
		console.log(hotellist);
		$scope.hotellistInfo = hotellist;
	}
	
	$scope.ShowFullImg = function(index){
		console.log(index);
		$scope.hotelIF = $scope.hotellistInfo[index];
		console.log($scope.hotelIF);
		
		ngDialog.open({
			template: '/assets/resources/html/ajax/slideshow-popup.html',
			scope : $scope,
			//controller:'hoteProfileController',
			className: 'ngdialog-theme-default'
		});
	}
	
	
	$scope.dateWiseInfo = function(index){
		console.log(index);
		$scope.hotelIF = $scope.hotellistInfo[index];
		console.log($scope.hotelIF);
		//$scope.roominfo = $scope.hotellistInfo[pIndex].hotelbyRoom[index];
		//console.log($scope.roominfo);
		$scope.rateDatedetail = [];
		$http.get('/getDatewiseHotelRoom/'+$scope.hotelIF.checkIn+"/"+$scope.hotelIF.checkOut+"/"+$scope.hotelIF.nationality+"/"+$scope.hotelIF.supplierCode+"/"+$scope.hotelIF.hotelbyRoom[0].roomId)
		.success(function(response){
			console.log(response);
			$scope.ratedetail = response;
			console.log($scope.ratedetail);
			var total = 0;
			var flag=0;
			angular.forEach($scope.ratedetail.hotelbyDate,function(value,key){
				
				var arr = value.date.split("-");
				var datevalue = (arr[1]+"/"+arr[0]+"/"+arr[2])
				$scope.datevalue1 = $filter('date')(new Date(datevalue), "EEE,MMM,dd,yyyy");
				var arr = $scope.datevalue1.split(",");
				$scope.day = arr[0];
				$scope.month = arr[1];
				$scope.date = arr[2];
				if(value.flag == 1){
					flag = value.flag;
					console.log(value.flag);
					}
				angular.forEach(value.roomType,function(value1,key1){					
					angular.forEach(value1.hotelRoomRateDetail,function(value2,key2){
						angular.forEach(value2.rateDetails,function(value3,key3){
							console.log(value.currencyShort);
							if(value3.adult == "1 Adult"){
								total = total+value3.rateValue;
								$scope.rateDatedetail.push({
									rate:value3.rateValue,
									meal:value3.mealTypeName,
									flag:value.flag,
									fulldate:value.date,
									day:$scope.day,
								    month:$scope.month,
								    currency:value.currencyShort,
								    date:$scope.date
									
								});
							}							
						});
					  });
					
				    });
				if(value.roomType.length == "0"){
					$scope.rateDatedetail.push({
						flag:value.flag,
						currency:value.currencyShort,
						fulldate:value.date,
						day:$scope.day,
						month:$scope.month,
						date:$scope.date
						
					});
				}
			     });
			$scope.flag = flag;
			$scope.total = total;
			console.log($scope.rateDatedetail);
			console.log(total);
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	
		
		
		ngDialog.open({
			template: '/assets/resources/html/show_Date_wise_info.html',
			scope : $scope,
			//controller:'hoteProfileController',
			className: 'ngdialog-theme-default'
		});
	}
	
	$scope.showAdult=function(roomid){
		$scope.rateDatedetail = [];
		
		$http.get('/getDatewiseHotelRoom/'+$scope.hotelIF.checkIn+"/"+$scope.hotelIF.checkOut+"/"+$scope.hotelIF.nationality+"/"+$scope.hotelIF.supplierCode+"/"+roomid)
		.success(function(response){
			console.log(response);
			$scope.ratedetail = response;
			console.log($scope.ratedetail);
			var total = 0;
			var flag=0;
			angular.forEach($scope.ratedetail.hotelbyDate,function(value,key){
				
				var arr = value.date.split("-");
				var datevalue = (arr[1]+"/"+arr[0]+"/"+arr[2])
				$scope.datevalue1 = $filter('date')(new Date(datevalue), "EEE,MMM,dd,yyyy");
				var arr = $scope.datevalue1.split(",");
				$scope.day = arr[0];
				$scope.month = arr[1];
				$scope.date = arr[2];
				if(value.flag == 1){
					flag = value.flag;
					console.log(value.flag);
					}
				angular.forEach(value.roomType,function(value1,key1){					
					angular.forEach(value1.hotelRoomRateDetail,function(value2,key2){
						angular.forEach(value2.rateDetails,function(value3,key3){
							console.log(value.currencyShort);
							if(value3.adult == "1 Adult"){
								total = total+value3.rateValue;
								$scope.rateDatedetail.push({
									rate:value3.rateValue,
									meal:value3.mealTypeName,
									flag:value.flag,
									fulldate:value.date,
									day:$scope.day,
								    month:$scope.month,
								    currency:value.currencyShort,
								    date:$scope.date
									
								});
							}							
						});
					  });
					
				    });
				if(value.roomType.length == "0"){
					$scope.rateDatedetail.push({
						flag:value.flag,
						currency:value.currencyShort,
						fulldate:value.date,
						day:$scope.day,
						month:$scope.month,
						date:$scope.date
						
					});
				}
			     });
			$scope.flag = flag;
			$scope.total = total;
			console.log($scope.rateDatedetail);
			console.log(total);
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	
		
		
	}
	
	
	$scope.showRate=function(adultValue){
				
		$scope.rateDatedetail = [];
		var total = 0;
		var flag=0;
		angular.forEach($scope.ratedetail.hotelbyDate,function(value,key){
			var arr = value.date.split("-");
			var datevalue = (arr[1]+"/"+arr[0]+"/"+arr[2])
			$scope.datevalue1 = $filter('date')(new Date(datevalue), "EEE,MMM,dd,yyyy");
			var arr = $scope.datevalue1.split(",");
			$scope.day = arr[0];
			$scope.month = arr[1];
			$scope.date = arr[2];
			if(value.flag == 1){
				flag = value.flag;
				console.log(value.flag);
				}
			angular.forEach(value.roomType,function(value1,key1){
				angular.forEach(value1.hotelRoomRateDetail,function(value2,key2){
					angular.forEach(value2.rateDetails,function(value3,key3){
						console.log(value.currencyShort);
						if(value3.adult == adultValue){
							total = total+value3.rateValue;
							$scope.rateDatedetail.push({
								rate:value3.rateValue,
								meal:value3.mealTypeName,
								flag:value.flag,
								fulldate:value.date,
								day:$scope.day,
							    month:$scope.month,
							    currency:value.currencyShort,
							    date:$scope.date
							});
						} 
					});
				  });
			    });
			if(value.roomType.length == "0"){
				$scope.rateDatedetail.push({
					flag:value.flag,
					currency:value.currencyShort,
					fulldate:value.date,
					day:$scope.day,
				    month:$scope.month,
				    date:$scope.date
				});
			}
		     });
		$scope.flag = flag;
		$scope.total = total;
		console.log($scope.rateDatedetail);
		console.log($scope.total);
		
	}
	
	
	
});



travelBusiness.controller('hotelDetailsController', function ($scope,$http,$filter,ngDialog) {
	
	$scope.init = function(hotel){
		
		$scope.hotel = hotel;
		console.log($scope.hotel);
		$scope.GenImg = "/searchHotelGenImg/getHotelGenImg/"+$scope.hotel.supplierCode+"?d="+new Date().getTime();
		$scope.ServImg = "/searchHotelServImg/getHotelServImg/"+$scope.hotel.supplierCode+"?d="+new Date().getTime();
		$scope.RoomImg = "/searchHotelRoomImg/getHotelRoomImg/"+$scope.hotel.supplierCode+"?d="+new Date().getTime();
		$scope.LobbyImg = "/searchHotelLobbyImg/getHotelLobbyImg/"+$scope.hotel.supplierCode+"?d="+new Date().getTime();
		$scope.LSImg = "/searchHotelLSImg/getHotelLSImg/"+$scope.hotel.supplierCode+"?d="+new Date().getTime();
		$scope.MapImg = "/searchHotelMapImg/getHotelMapImg/"+$scope.hotel.supplierCode+"?d="+new Date().getTime();
	}
	
	
	
	$scope.hotel_amenities = function(){
		$scope.amenities_check = [];
		$http.get("/hotelamenities").success(function(response){
			$scope.amenities=response;
			console.log($scope.amenities);
			
		
			$http.get('/findAmenitiesData/'+$scope.hotel.supplierCode).success(function(response) {
				console.log(response);
				angular.forEach($scope.amenities, function(obj, index){
					angular.forEach(response, function(obj1, index){
						if ((obj.amenitiesCode == obj1.amenitiesCode)) {
							$scope.amenities_check.push({
								amenitiesCode:obj.amenitiesCode,
								amenitiesName:obj.amenitiesNm,
								amenitiesIcon:obj.amenitiesicon
							});
							
							
						};
					});
				});
				console.log($scope.amenities_check);
			});
		});
		
	}
	
	$scope.business_amenities = function(){
		
		$scope.business_check = [];
		
		$http.get("/business").success(function(response){
			$scope.business=response;
		
		$http.get('/findAmenitiesData/'+$scope.hotel.supplierCode).success(function(response) {
			angular.forEach($scope.business, function(obj, index){
				angular.forEach(response, function(obj1, index){

					if ((obj.amenitiesCode == obj1.amenitiesCode)) {
						$scope.business_check.push({
							amenitiesCode:obj.amenitiesCode,
							amenitiesName:obj.amenitiesNm,
							amenitiesIcon:obj.amenitiesicon
						});
						
					};
				});
			});
			console.log($scope.business_check);
		});
	});
	}
	
	$scope.sport_amenities = function(){
		
		$scope.leisure_sport_check = [];
		
		$http.get("/leisureSport").success(function(response){
			$scope.leisureSport=response;
		
		$http.get('/findAmenitiesData/'+$scope.hotel.supplierCode).success(function(response) {
			angular.forEach($scope.leisureSport, function(obj, index){
				angular.forEach(response, function(obj1, index){
					if ((obj.amenitiesCode == obj1.amenitiesCode)) {
						$scope.leisure_sport_check.push({
							amenitiesCode:obj.amenitiesCode,
							amenitiesName:obj.amenitiesNm,
							amenitiesIcon:obj.amenitiesicon
						});
						
					};
				});
			});
			console.log($scope.leisure_sport_check);
			});
		});
	}
	
	$scope.availability = function(){
		
		$scope.rateDatedetail = [];
		$http.get('/getDatewiseHotelRoom/'+$scope.hotel.checkIn+"/"+$scope.hotel.checkOut+"/"+$scope.hotel.nationality+"/"+$scope.hotel.supplierCode+"/"+$scope.hotel.hotelbyRoom[0].roomId)
		.success(function(response){
			console.log(response);
			$scope.ratedetail = response;
			console.log($scope.ratedetail);
			var total = 0;
			var flag =0;
			angular.forEach($scope.ratedetail.hotelbyDate,function(value,key){
				
				var arr = value.date.split("-");
				var datevalue = (arr[1]+"/"+arr[0]+"/"+arr[2])
				$scope.datevalue1 = $filter('date')(new Date(datevalue), "EEE,MMM,dd,yyyy");
				var arr = $scope.datevalue1.split(",");
				$scope.day = arr[0];
				$scope.month = arr[1];
				$scope.date = arr[2];
				console.log("------");
				console.log(value.flag);
				if(value.flag == 1){
					flag = value.flag;
					console.log(value.flag);
					}
				angular.forEach(value.roomType,function(value1,key1){					
					angular.forEach(value1.hotelRoomRateDetail,function(value2,key2){
						angular.forEach(value2.rateDetails,function(value3,key3){
							console.log(value.currencyShort);
							if(value3.adult == "1 Adult"){
								total = total+value3.rateValue;
								$scope.rateDatedetail.push({
									rate:value3.rateValue,
									meal:value3.mealTypeName,
									flag:value.flag,
									currency:value.currencyShort,
									fulldate:value.date,
									day:$scope.day,
								    month:$scope.month,
								    date:$scope.date
									
								});
							}							
						});
					  });
					
				    });
				if(value.roomType.length == "0"){
					$scope.rateDatedetail.push({
						flag:value.flag,
						fulldate:value.date,
						currency:value.currencyShort,
						day:$scope.day,
						month:$scope.month,
						date:$scope.date
						
					});
				}
			     });
			
			$scope.total = total;
			$scope.flag = flag;
			console.log($scope.rateDatedetail);
			console.log(total);
			console.log(flag);
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	}
	
	$scope.showAdultRoomView=function(roomid){
		$scope.rateDatedetail = [];
		
		$http.get('/getDatewiseHotelRoom/'+$scope.hotel.checkIn+"/"+$scope.hotel.checkOut+"/"+$scope.hotel.nationality+"/"+$scope.hotel.supplierCode+"/"+roomid)
		.success(function(response){
			console.log(response);
			$scope.ratedetail = response;
			console.log($scope.ratedetail);
			var total = 0;
			var flag =0;
			angular.forEach($scope.ratedetail.hotelbyDate,function(value,key){
				
				var arr = value.date.split("-");
				var datevalue = (arr[1]+"/"+arr[0]+"/"+arr[2])
				$scope.datevalue1 = $filter('date')(new Date(datevalue), "EEE,MMM,dd,yyyy");
				var arr = $scope.datevalue1.split(",");
				$scope.day = arr[0];
				$scope.month = arr[1];
				$scope.date = arr[2];
				console.log("------");
				console.log(value.flag);
				if(value.flag == 1){
					console.log(value.flag);
				flag = value.flag;
				console.log(value.flag);
				}
				angular.forEach(value.roomType,function(value1,key1){					
					angular.forEach(value1.hotelRoomRateDetail,function(value2,key2){
						angular.forEach(value2.rateDetails,function(value3,key3){
							console.log(value.currencyShort);
							if(value3.adult == "1 Adult"){
								total = total+value3.rateValue;
								$scope.rateDatedetail.push({
									rate:value3.rateValue,
									meal:value3.mealTypeName,
									currency:value.currencyShort,
									flag:value.flag,
									fulldate:value.date,
									day:$scope.day,
								    month:$scope.month,
								    date:$scope.date
									
								});
							}							
						});
					  });
					
				    });
				if(value.roomType.length == "0"){
					$scope.rateDatedetail.push({
						flag:value.flag,
						fulldate:value.date,
						currency:value.currencyShort,
						day:$scope.day,
						month:$scope.month,
						date:$scope.date
						
					});
				}
			     });
			
			$scope.total = total;
			$scope.flag = flag;
			console.log($scope.rateDatedetail);
			console.log(total);
			console.log(flag);
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
	}
	
	
	$scope.showRateAdultwise=function(adultValue){
		
		$scope.rateDatedetail = [];
		var total = 0;
		var flag = 0;
		angular.forEach($scope.ratedetail.hotelbyDate,function(value,key){
			var arr = value.date.split("-");
			var datevalue = (arr[1]+"/"+arr[0]+"/"+arr[2])
			$scope.datevalue1 = $filter('date')(new Date(datevalue), "EEE,MMM,dd,yyyy");
			var arr = $scope.datevalue1.split(",");
			$scope.day = arr[0];
			$scope.month = arr[1];
			$scope.date = arr[2];
			console.log("------");
			console.log(value.flag);
			if(value.flag == 1){
				flag = value.flag;
				console.log(value.flag);
				}
			angular.forEach(value.roomType,function(value1,key1){
				angular.forEach(value1.hotelRoomRateDetail,function(value2,key2){
					angular.forEach(value2.rateDetails,function(value3,key3){
						console.log(value.currencyShort);
						if(value3.adult == adultValue){
							total = total+value3.rateValue;
							$scope.rateDatedetail.push({
								rate:value3.rateValue,
								meal:value3.mealTypeName,
								currency:value.currencyShort,
								flag:value.flag,
								fulldate:value.date,
								day:$scope.day,
							    month:$scope.month,
							    date:$scope.date
							});
						} 
					});
				  });
			    });
			if(value.roomType.length == "0"){
				$scope.rateDatedetail.push({
					flag:value.flag,
					fulldate:value.date,
					currency:value.currencyShort,
					day:$scope.day,
				    month:$scope.month,
				    date:$scope.date
				});
			}
		     });
		
		$scope.total = total;
		$scope.flag = flag;
		console.log($scope.rateDatedetail);
		console.log($scope.total);
		console.log(flag);
	}
	
	
});

