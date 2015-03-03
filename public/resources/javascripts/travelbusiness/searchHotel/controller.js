

travelBusiness.controller('AgentBookingController', function ($scope,$http,$filter,ngDialog,$cookieStore) {
	
	
	$scope.pageNumber;
	$scope.pageSize;
	$scope.fromData = "1";
	$scope.toDate="1";
	$scope.status = "1";
	var currentPage = 1;
	var totalPages;
	$scope.flag = 0;
	
	$scope.init = function(hotelAllData){
				
		console.log(hotelAllData);
		
		totalPages = hotelAllData.totalPages;
		currentPage = hotelAllData.currentPage;
		$scope.pageNumber = hotelAllData.currentPage;
		$scope.pageSize = hotelAllData.totalPages;
		$scope.agentBook = hotelAllData.results;
		if(totalPages == 0) {
			$scope.pageNumber = 0;
		}
		
	}
	
	$scope.onNext = function() {
		if(currentPage < totalPages) {
			currentPage++;
			$scope.searchagent(currentPage);
		}
	};
	$scope.onPrev = function() {
		if(currentPage > 1) {
			currentPage--;
			$scope.searchagent(currentPage);
		}
	};
	
	$scope.searchagent = function(page) {
		currentPage = page;
		/*if(angular.isUndefined($scope.title) || $scope.title=="") {
			console.log('inside function');
			$scope.title = " ";
		}*/
		
		currentPage = page;
		console.log(currentPage);
		$http.get("/getagentInfo/"+currentPage+"/"+$scope.fromData+"/"+$scope.toDate+"/"+$scope.status).success(function(response){
			
			console.log(response);
			totalPages = response.totalPages;
			currentPage = response.currentPage;
			$scope.pageNumber = response.currentPage;
			$scope.pageSize = response.totalPages;
			$scope.agentBook = response.results;
			if(totalPages == 0) {
				$scope.pageNumber = 0;
			}
			
		});
	};
	
	
	
	$scope.showoAgentDataDateWise = function(selectDate){
		console.log(selectDate);
		if(selectDate.status == undefined || selectDate.status == ""){
			$scope.status = "1";
		}else{
			$scope.status = selectDate.status;
		}
		if(selectDate.fromDate == undefined || selectDate.fromDate == "" && selectDate.toDate == undefined || selectDate.toDate == ""){
			$scope.fromData ="1";
			$scope.toDate = "1";
			$scope.flag  = 0;
		}else{
			$scope.fromData = selectDate.fromDate;
			$scope.toDate = selectDate.toDate;
			var arr = $scope.toDate.split("-");
			var tDate = (arr[1]+"/"+arr[0]+"/"+arr[2])
			var arr1 =$scope.fromData.split("-");
			var fDate = (arr1[1]+"/"+arr1[0]+"/"+arr1[2])
		
			 var toDate = Date.parse(tDate);
	         var fromDate = Date.parse(fDate);
			
			if(fromDate < toDate){
				$scope.flag  = 0;
			}else{
				$scope.flag = 1;
			}
		}
				
		
		$http.get("/getagentInfo/"+currentPage+"/"+$scope.fromData+"/"+$scope.toDate+"/"+$scope.status).success(function(response){
		
			console.log(response);
			totalPages = response.totalPages;
			currentPage = response.currentPage;
			$scope.pageNumber = response.currentPage;
			$scope.pageSize = response.totalPages;
			$scope.agentBook = response.results;
			if(totalPages == 0) {
				$scope.pageNumber = 0;
			}
		});
	}
	
	
	$scope.rateDatewise = [];
	$scope.showdateWiseView = function(agent){
		console.log(agent);
		$scope.rateDatewise = [];
		$http.get("/getbookDateWise/"+agent).success(function(response){
			console.log(response);
			//$scope.bookinginfo = response;
			
		
			angular.forEach(response,function(value,key){
			var arr = value.cdate.split("-");
			var datevalue = (arr[1]+"/"+arr[0]+"/"+arr[2])
			$scope.datevalue1 = $filter('date')(new Date(datevalue), "EEE,MMM,dd,yyyy");
			var arr = $scope.datevalue1.split(",");
			$scope.day = arr[0];
			$scope.month = arr[1];
			$scope.date = arr[2];
			
			$scope.rateDatewise.push({
				day:$scope.day,
			    month:$scope.month,
			    date:$scope.date,
			    rate:value.rate,
			    meal:value.mealtype
				
			});
			
			});
						
			console.log($scope.rateDatewise);
		});
	}
	
	
	
	$scope.showDetails = function(agentinfo){
		//console.log(agent);
		$scope.agentinfo = agentinfo;
		ngDialog.open({
			template: '/assets/resources/html/agent_booking_details.html',
			scope : $scope,
			//controller:'hoteProfileController',
			className: 'ngdialog-theme-default'
		});
	}
	
	$scope.cancelbooking = function(agentId){
		console.log(agentId);
		$http.get("/getbookingcancel/"+agentId).success(function(response){
			
			//notificationService.success("Confirmed Booking");
			ngDialog.close();
			$http.get("/getagentInfo/"+currentPage+"/"+$scope.fromData+"/"+$scope.toDate+"/"+$scope.status).success(function(response){
				
				console.log(response);
				totalPages = response.totalPages;
				currentPage = response.currentPage;
				$scope.pageNumber = response.currentPage;
				$scope.pageSize = response.totalPages;
				$scope.agentBook = response.results;
				if(totalPages == 0) {
					$scope.pageNumber = 0;
				}
				
			});
		});
		
	}
	
	
});

travelBusiness.controller('HomePageController', function ($scope,$http,$filter,ngDialog,$cookieStore) {

	$scope.loginSuccess = 0;
	$scope.errorMsg = false;
	$scope.loginAgent = function(){
		
		ngDialog.open({
			template: '/assets/resources/html/loginAgent.html',
			scope : $scope,
			//controller:'hoteProfileController',
			className: 'ngdialog-theme-default'
		});
	}
	
	$scope.forgotpass = function(){
		ngDialog.close();
		
		ngDialog.open({
			template: '/assets/resources/html/forgotPassword.html',
			scope : $scope,
			//controller:'hoteProfileController',
			className: 'ngdialog-theme-default'
		});
		
	}
	
	$scope.getpassword = function(email){
		console.log(email);
		$http.get('/getAgentpassword/'+email).success(function(response) {
			if(response == 0){
				$scope.flag = 0;
				ngDialog.close();
			}else{
				$scope.flag = 1;
				
			}
		});
	}
	
	/*$scope.AgentBookingInfo = function(){
		console.log("HIii");
		window.location("AgentBookingInfo/");
	}*/
	
	$scope.loginAgentcheck = function(loginAgentinfo){
	
		console.log(loginAgentinfo);  
		$http.get('/checkAgentinfo/'+loginAgentinfo.loginID+'/'+loginAgentinfo.password+'/'+loginAgentinfo.agentId).success(function(response){
			console.log(response);
			
			if(response == 0){
				$scope.loginSuccess = 0;
				$scope.errorMsg = true;
				console.log($scope.errorMsg);
			}else{
				$scope.Agentresponse = response;
				$scope.AgentId = $scope.Agentresponse.agentCode;
				$scope.AgentCompany = $scope.Agentresponse.companyName;
				$cookieStore.put('AgentId',$scope.AgentId);
				$cookieStore.put('AgentCompany',$scope.AgentCompany);
				$scope.loginSuccess = 1;
				$scope.errorMsg = false;
				ngDialog.close();
			}
			console.log($scope.loginSuccess);
		});
		
	}
	
	
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
	
	}
	
	$http.get("/searchNationality").success(function(response) {
		console.log(response);
		$scope.searchNationality = response;
	}); 

	
});


travelBusiness.controller('PageController', function ($scope,$http,$filter,ngDialog,$cookieStore) {
	
		
	$scope.AgentId = $cookieStore.get('AgentId');
	$scope.AgentCompany = $cookieStore.get('AgentCompany');
	
	console.log($scope.AgentCompany);
	
		
	$scope.init = function(hotelAllData){
		
		angular.forEach(hotelAllData.hotellist, function(obj, index){ ///hotel_profile
			$scope.img = "/searchHotelInfo/getHotelImagePath/"+hotelAllData.hotellist[index].supplierCode+"?d="+new Date().getTime();
			hotelAllData.hotellist[index].imgPaths = $scope.img;
						
		});
		
		console.log(hotelAllData.hotellist);
		$scope.hotelAllData = hotelAllData;
		console.log($scope.hotelAllData);
		$scope.hotellistInfo = hotelAllData.hotellist;
	}
	
	
$http.get("/searchCountries").success(function(response) {
		
		$scope.searchCountries = response;
	}); 

	$scope.onCountryChange = function() {
				console.log("for city");
		$http.get('/searchCities/'+$scope.searchby.countryCode)
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
	
	$http.get("/searchNationality").success(function(response) {
		console.log(response);
		$scope.searchNationality = response;
	}); 
		
	$scope.searchAgainHotel = function(){
		console.log($scope.searchby);
		
		$http.post('/searchAgainHotelInfo', $scope.searchby).success(function(data){
			console.log('success');
			
			angular.forEach(data.hotellist, function(obj, index){ 
				$scope.img = "/searchHotelInfo/getHotelImagePath/"+data.hotellist[index].supplierCode+"?d="+new Date().getTime();
				data.hotellist[index].imgPaths = $scope.img;
							
			});
			
			console.log(data.hotellist);
			$scope.hotelAllData = data;
			console.log($scope.hotelAllData);
			$scope.hotellistInfo = data.hotellist;
			$scope.amenities_check = [];
			$scope.services_check = [];
			$scope.location_check = [];
			/*console.log(jQuery(".filters-container .filters-option a").click(function(e) {
		        e.preventDefault();
		        if (jQuery(this).parent().hasClass("active")) {
		        	jQuery(this).parent().removeClass("active");
		        } else {
		        	jQuery(this).parent().addClass("active");
		        }
		    }));*/
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	}
	$scope.sort = 1;
	$scope.sortByStar = 1;
	
	$scope.sortRating = function(){
		console.log("HIiiii");
		
		 if($scope.sortByStar == 0){
			   $scope.sortByStar = 1;
		   }else{
			   $scope.sortByStar = 0;
		   }
				   
		   console.log($scope.hotellistInfo);
			$scope.findHotelData.checkIn = $scope.hotelAllData.hotellist[0].checkIn;
			$scope.findHotelData.checkOut = $scope.hotelAllData.hotellist[0].checkOut;
			$scope.findHotelData.city = $scope.hotelAllData.hotellist[0].cityCode;
			$scope.findHotelData.nationalityCode = $scope.hotelAllData.hotellist[0].nationality;
			//$scope.findHotelData.id = $scope.hotelAllData.hotellist[0].startRating;
			$scope.findHotelData.amenitiesCheck = $scope.amenities_check;
			$scope.findHotelData.servicesCheck = $scope.services_check;
			$scope.findHotelData.locationCheck = $scope.location_check;
			$scope.findHotelData.sortByRating = $scope.sortByStar;
			$scope.findHotelData.sortData = $scope.sort;
			
				console.log($scope.findHotelData);
			
			$http.post('/findHotelByData', $scope.findHotelData).success(function(data){
				console.log('success');
				console.log(data);
				$scope.hotellistInfo = data.hotellist;
				angular.forEach(data.hotellist, function(obj, index){ 
					$scope.img = "/searchHotelInfo/getHotelImagePath/"+data.hotellist[index].supplierCode+"?d="+new Date().getTime();
					$scope.hotellistInfo[index].imgPaths = $scope.img;
								
				});
				$scope.hotelAllData.totalHotel = data.totalHotel;
				console.log($scope.hotellistInfo);
				
			}).error(function(data, status, headers, config) {
				console.log('ERROR');
			});
	}
	
	   $scope.sortData = function(){
		   if($scope.sort == 0){
			   $scope.sort = 1;
			   
		   }else{
			   $scope.sort = 0;
		   }
		   console.log($scope.sort);
		  		   
		   console.log($scope.hotellistInfo);
			$scope.findHotelData.checkIn = $scope.hotelAllData.hotellist[0].checkIn;
			$scope.findHotelData.checkOut = $scope.hotelAllData.hotellist[0].checkOut;
			$scope.findHotelData.city = $scope.hotelAllData.hotellist[0].cityCode;
			$scope.findHotelData.nationalityCode = $scope.hotelAllData.hotellist[0].nationality;
			//$scope.findHotelData.id = $scope.hotelAllData.hotellist[0].startRating;
			$scope.findHotelData.amenitiesCheck = $scope.amenities_check;
			$scope.findHotelData.servicesCheck = $scope.services_check;
			$scope.findHotelData.locationCheck = $scope.location_check;
			$scope.findHotelData.sortByRating = $scope.sortByStar;
			$scope.findHotelData.sortData = $scope.sort;
			
				console.log($scope.findHotelData);
			
			$http.post('/findHotelByData', $scope.findHotelData).success(function(data){
				console.log('success');
				console.log(data);
				$scope.hotellistInfo = data.hotellist;
				angular.forEach(data.hotellist, function(obj, index){ 
					$scope.img = "/searchHotelInfo/getHotelImagePath/"+data.hotellist[index].supplierCode+"?d="+new Date().getTime();
					$scope.hotellistInfo[index].imgPaths = $scope.img;
								
				});
				$scope.hotelAllData.totalHotel = data.totalHotel;
				console.log($scope.hotellistInfo);
				
			}).error(function(data, status, headers, config) {
				console.log('ERROR');
			});
		   
		   
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
	
	
	$scope.showPromotion = function(roominfo){
		
		$scope.roomPromotion=roominfo;
		
		ngDialog.open({
			template: '/assets/resources/html/promotionInfo.html',
			scope : $scope,
			//controller:'hoteProfileController',
			className: 'ngdialog-theme-default'
		});
	}
	
/*	$scope.dateWiseInfo = function(index){
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
	}*/
	
	/*$scope.showAdult=function(roomid){
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
	
		
		
	}*/
	
	
	/*$scope.showRate=function(adultValue){
				
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
		
	}*/
	
	$scope.services_check = [];
	$scope.servicesWiseHotel = function(index,servicesid){
		console.log("---");
		console.log(index);
		if(jQuery("#services"+index).parent().attr("class") !="active") {
		$scope.services_check.push(servicesid);
		
		}else{
			DeleteService(servicesid);
		}
		console.log($scope.services_check);
	}
	
	DeleteService = function(servicesid){
		angular.forEach($scope.services_check, function(obj, index){
			if (servicesid == obj) {
				$scope.services_check.splice(index, 1);
		       	return;
		    };
		});
	 }
	$scope.location_check = [];
	$scope.locationWiseHotel = function(index,locationid){
		console.log(locationid);
		if(jQuery("#location"+index).parent().attr("class") !="active") {
			$scope.location_check.push(locationid);
		}else{
			Deletelocation(locationid);
		}
		console.log($scope.location_check);
	}
	
	Deletelocation = function(locationid){
		angular.forEach($scope.location_check, function(obj, index){
			if (locationid == obj) {
				$scope.location_check.splice(index, 1);
		       	return;
		    };
		});
	 }
	
	
	$scope.amenities_check = [];
	$scope.amenitiesWiseHotel = function(index,amenitiesCode){
		console.log(amenitiesCode);
		if(jQuery("#amenities"+index).parent().attr("class") !="active") {
			$scope.amenities_check.push(amenitiesCode);
		}else{
			Deleteamenities(amenitiesCode);
		}
		console.log($scope.amenities_check);
	}
	
	Deleteamenities = function(amenitiesCode){
		angular.forEach($scope.amenities_check, function(obj, index){
			if (amenitiesCode == obj) {
				$scope.amenities_check.splice(index, 1);
		       	return;
		    };
		});
	 }
		
	$scope.findHotelData = {};
	$scope.findHotelByData = function(){
		console.log($scope.hotellistInfo);
		$scope.findHotelData.checkIn = $scope.hotelAllData.hotellist[0].checkIn;
		$scope.findHotelData.checkOut = $scope.hotelAllData.hotellist[0].checkOut;
		$scope.findHotelData.city = $scope.hotelAllData.hotellist[0].cityCode;
		$scope.findHotelData.nationalityCode = $scope.hotelAllData.hotellist[0].nationality;
		//$scope.findHotelData.id = $scope.hotelAllData.hotellist[0].startRating;
		$scope.findHotelData.amenitiesCheck = $scope.amenities_check;
		$scope.findHotelData.servicesCheck = $scope.services_check;
		$scope.findHotelData.locationCheck = $scope.location_check;
		$scope.findHotelData.sortData = $scope.sort;
		
			console.log($scope.findHotelData);
		
		$http.post('/findHotelByData', $scope.findHotelData).success(function(data){
			console.log('success');
			console.log(data);
			$scope.hotellistInfo = data.hotellist;
			angular.forEach(data.hotellist, function(obj, index){ 
				$scope.img = "/searchHotelInfo/getHotelImagePath/"+data.hotellist[index].supplierCode+"?d="+new Date().getTime();
				$scope.hotellistInfo[index].imgPaths = $scope.img;
							
			});
			$scope.hotelAllData.totalHotel = data.totalHotel;
			console.log($scope.hotellistInfo);
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	}
	
	
	
	
});



travelBusiness.controller('hotelDetailsController', function ($scope,$http,$filter,ngDialog) {
	
	$scope.init = function(hotel){
		
		$scope.hotel = hotel;
		
		$scope.GenImg = "/searchHotelGenImg/getHotelGenImg/"+$scope.hotel.supplierCode+"?d="+new Date().getTime();
		$scope.ServImg = "/searchHotelServImg/getHotelServImg/"+$scope.hotel.supplierCode+"?d="+new Date().getTime();
		$scope.RoomImg = "/searchHotelRoomImg/getHotelRoomImg/"+$scope.hotel.supplierCode+"?d="+new Date().getTime();
		$scope.LobbyImg = "/searchHotelLobbyImg/getHotelLobbyImg/"+$scope.hotel.supplierCode+"?d="+new Date().getTime();
		$scope.LSImg = "/searchHotelLSImg/getHotelLSImg/"+$scope.hotel.supplierCode+"?d="+new Date().getTime();
		$scope.MapImg = "/searchHotelMapImg/getHotelMapImg/"+$scope.hotel.supplierCode+"?d="+new Date().getTime();
	
		angular.forEach(hotel.hotelbyRoom, function(obj, index){ 
			$scope.roomImg = "/hoteldetailpage/getHotelRoomImagePath/"+hotel.hotelbyRoom[index].roomId+"?d="+new Date().getTime();
			$scope.hotel.hotelbyRoom[index].roomimgPaths = $scope.roomImg;
						
		});
		console.log($scope.hotel);
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
	
	
	
	$scope.showRateAdultwise=function(adultValue,roomNo){
		if(roomNo == undefined){
			roomNo = 1;
		}
		console.log(roomNo);
		console.log(adultValue);
		
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
			/*if(value.flag == 1){
				flag = value.flag;
				console.log(value.flag);
				}*/
			angular.forEach(value.roomType,function(value1,key1){
				angular.forEach(value1.hotelRoomRateDetail,function(value2,key2){
					if(value2.flag == 1){
						flag = value2.flag;
					}
					
					/*if(value2.allotmentmarket.allocation == 3){
						if(value2.availableRoom < roomNo){
							flag = 1;
							angular.forEach($scope.rateDatedetail,function(value4,key4){
								if(value3.fulldate == value.date){
									value3.flag = 1;
								}
							});
						}else{
							flag = 0;
							angular.forEach($scope.rateDatedetail,function(value3,key3){
								if(value3.fulldate == value.date){
									value3.flag = 1;
								}
							});
						}
					}*/
					angular.forEach(value2.rateDetails,function(value3,key3){
						console.log(value.currencyShort);
						if(value3.adult == adultValue){
							total = total+value3.rateValue;
							$scope.rateDatedetail.push({
								rate:value3.rateValue,
								meal:value3.mealTypeName,
								currency:value.currencyShort,
							//	flag:value.flag,
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
					//flag:value.flag,
					fulldate:value.date,
					currency:value.currencyShort,
					day:$scope.day,
				    month:$scope.month,
				    date:$scope.date
				});
			}
		     });
		
		
		
			angular.forEach($scope.ratedetail.hotelbyDate,function(value,key){
			angular.forEach(value.roomType,function(value1,key1){
				angular.forEach(value1.hotelRoomRateDetail,function(value2,key2){
					if(value2.allotmentmarket.allocation == 3){
						console.log(value2.availableRoom);
						if(value2.availableRoom < roomNo){
							flag = 1;
							angular.forEach($scope.rateDatedetail,function(value3,key3){
								if(value3.fulldate == value.date){
									value3.flag = 1;
								}
							});
							
						}else{
							flag = 0;
							angular.forEach($scope.rateDatedetail,function(value3,key3){
								if(value3.fulldate == value.date){
									value3.flag = 0;
								}
							});
						}
					}
					
				  });
			    });
			   });
		
		
		$scope.total = total;
		$scope.totalParPerson = total;
		if(roomNo != undefined){
			$scope.total = $scope.totalParPerson * roomNo;
		}
	
		$scope.flag = flag;
		console.log($scope.rateDatedetail);
		console.log($scope.total);
		console.log(flag);
	}
	
	
	$scope.dateWiseInfo = function(roomid){
				
		console.log($scope.hotel);
		
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
				
				angular.forEach(value.roomType,function(value1,key1){					
					angular.forEach(value1.hotelRoomRateDetail,function(value2,key2){
						if(value2.flag == 1){
							flag = value2.flag;
						}
						
						angular.forEach(value2.rateDetails,function(value3,key3){
							console.log(value.currencyShort);
							if(value3.adult == "1 Adult"){
								total = total+value3.rateValue;
								$scope.rateDatedetail.push({
									rate:value3.rateValue,
									meal:value3.mealTypeName,
									currency:value.currencyShort,
									//flag:value.flag,
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
						//flag:value.flag,
						fulldate:value.date,
						currency:value.currencyShort,
						day:$scope.day,
						month:$scope.month,
						date:$scope.date
						
					});
				}
			     });
			
			angular.forEach($scope.ratedetail.hotelbyDate,function(value,key){
				angular.forEach(value.roomType,function(value1,key1){
					angular.forEach(value1.hotelRoomRateDetail,function(value2,key2){
						if(value2.allotmentmarket.allocation == 3){
							console.log(value2.availableRoom);
							if(value2.availableRoom < 1){
								angular.forEach($scope.rateDatedetail,function(value3,key3){
									if(value3.fulldate == value.date){
										value3.flag = 1;
									}
								});
								
							}else{
								angular.forEach($scope.rateDatedetail,function(value3,key3){
									if(value3.fulldate == value.date){
										value3.flag = 0;
									}
								});
							}
						}
						
					  });
				    });
				   });
			
			$scope.total = total;
			$scope.totalParPerson = total;
			$scope.flag = flag;
			console.log($scope.rateDatedetail);
			console.log(total);
			console.log(flag);
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
		ngDialog.open({
			template: '/assets/resources/html/show_Date_wise_info_InHotel.html',
			scope : $scope,
			//controller:'hoteProfileController',
			className: 'ngdialog-theme-default'
		});
	}
	
	$scope.availableFlag = [];
	
	$scope.countTotal = function(roomNo){
		console.log(roomNo);
		console.log($scope.ratedetail);
		console.log($scope.totalParPerson);
		$scope.availableFlag = [];
		$scope.total = $scope.totalParPerson * roomNo;
		
		var flag = 0;
		angular.forEach($scope.ratedetail.hotelbyDate,function(value,key){
			
			angular.forEach(value.roomType,function(value1,key1){
				angular.forEach(value1.hotelRoomRateDetail,function(value2,key2){
					if(value2.flag == 1){
						flag = value2.flag;
						console.log(value2.flag);
					}
					if(value2.allotmentmarket.allocation == 3){
						console.log(value2.availableRoom);
						if(value2.availableRoom < roomNo){
							flag = 1;
							angular.forEach($scope.rateDatedetail,function(value3,key3){
								if(value3.fulldate == value.date){
									value3.flag = 1;
								}
							});
							
						}else{
							angular.forEach($scope.rateDatedetail,function(value3,key3){
								if(value3.fulldate == value.date){
									value3.flag = 0;
								}
							});
						}
					}
					
				  });
			    });
			   });
		
		$scope.flag = flag;
		
		console.log(flag);
		console.log($scope.rateDatedetail);
		
		
		/*angular.forEach(value2.rateDetails,function(value3,key3){
			console.log(value.currencyShort);
			
		});*/
		
		
	}
	
	$scope.ShowFull = function(index){
		console.log(index);
		console.log($scope.hotel);
		$scope.roomIF = $scope.hotel.hotelbyRoom[index];
		//console.log($scope.hotelIF);
		
		ngDialog.open({
			template: '/assets/resources/html/ajax/slideshow-popup.html',
			scope : $scope,
			//controller:'hoteProfileController',
			className: 'ngdialog-theme-default'
		});
	}
	
	$scope.roomWiseInfo = function(roomInfo){
		console.log(roomInfo);
		console.log($scope.hotel);
		$scope.hotelif = $scope.hotel;
		$scope.roomIF = roomInfo;
		ngDialog.open({
			template: '/assets/resources/html/room_wise_info.html',
			scope : $scope,
			//controller:'hoteProfileController',
			className: 'ngdialog-theme-default'
		});
	}
	
     $scope.showPromotion = function(roominfo){
		
		$scope.roomPromotion=roominfo;
		
		console.log($scope.roomPromotion);
		
		ngDialog.open({
			template: '/assets/resources/html/promotionInfo.html',
			scope : $scope,
			//controller:'hoteProfileController',
			className: 'ngdialog-theme-default'
		});
	}
	
	
});


travelBusiness.controller('hotelBookingController', function ($scope,$http,$filter,ngDialog,notificationService,$window) {
	
	$scope.init = function(hotel){
		$scope.hotel = hotel;
		
		angular.forEach(hotel.hotelbyRoom, function(obj, index){ 
			$scope.Img = "/hotelBookingpage/getHotelImagePath/"+hotel.supplierCode+"?d="+new Date().getTime();
			$scope.hotel.imgPaths = $scope.Img;
						
		});
		console.log($scope.hotel);
		console.log(hotel);
		
		var arr = hotel.checkIn.split("-");
		var datevalue = (arr[1]+"/"+arr[0]+"/"+arr[2])
		$scope.checkIn = $filter('date')(new Date(datevalue), "MMM dd,yyyy");
		var arr1 = hotel.checkOut.split("-");
		var datevalue1 = (arr1[1]+"/"+arr1[0]+"/"+arr1[2])
		$scope.checkOut = $filter('date')(new Date(datevalue1), "MMM dd,yyyy");
		console.log($scope.checkIn);
	}
	
	$http.get("/searchCountries").success(function(response) {
		
		$scope.searchCountries = response;
	}); 
	
	$scope.savetravellerinfo = function(){
		console.log($scope.traveller);
		$scope.hotel.hotelBookingDetails.travellerfirstname = $scope.traveller.firstname;
		$scope.hotel.hotelBookingDetails.travellerlastname = $scope.traveller.lastname;
		$scope.hotel.hotelBookingDetails.travelleraddress = $scope.traveller.address;
		$scope.hotel.hotelBookingDetails.travellercountry = $scope.traveller.country;
		$scope.hotel.hotelBookingDetails.travellerphnaumber = $scope.traveller.phnaumber;
		$scope.hotel.hotelBookingDetails.travelleremail = $scope.traveller.email;
		//$scope.hotel.hotelbyDate = null;
		console.log($scope.hotel);
		
		
		
		
		$http.post('/saveHotelBookingInfo',$scope.hotel).success(function(data){
			 notificationService.success("Room Book Successfully");
			console.log("Success");
    		
    		 $window.location.replace("http://localhost:9000/");
    		
    	}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
	}
	

});	
