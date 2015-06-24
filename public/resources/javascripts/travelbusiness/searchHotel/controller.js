

travelBusiness.controller('AgentBookingController', function ($scope,$http,$filter,ngDialog,$cookieStore,notificationService) {
	
	
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
	
	$scope.showtable = function(checkIn,checkOut,guest,status){
		console.log(guest);
		console.log(status);
		console.log(checkIn);
		console.log(checkOut);
		if(guest=="")
	{
			guest="undefined";
	}
		
		$http.get("/getagentInfobynm/"+currentPage+"/"+checkIn+"/"+checkOut+"/"+guest+"/"+status).success(function(response1){
		console.log(response1);
		totalPages = response1.totalPages;
		currentPage = response1.currentPage;
		$scope.pageNumber = response1.currentPage;
		$scope.pageSize = response1.totalPages;
		$scope.agentBook = response1.results;
		if(totalPages == 0) {
			$scope.pageNumber = 0;
		}
		
	});
	}
	
	$scope.showoAgentDataDateWise = function(selectDate){
		console.log(selectDate);
		if(selectDate.status == undefined || selectDate.status == "" || selectDate.status == "All"){
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
	$scope.bookingPayment = function(bookingId, payment){
		console.log(bookingId);
		console.log(payment);
		$http.get("/getBookingPaymentInfo/"+bookingId+"/"+payment).success(function(response){
			console.log("OK,,OK");
		});
		
	}
	
	
	$scope.showDetails = function(agentinfo){
		console.log(agentinfo);
		$scope.agentinfo = agentinfo;
		ngDialog.open({
			template: '/assets/resources/html/agent_booking_details.html',
			scope : $scope,
			//controller:'hoteProfileController',
			className: 'ngdialog-theme-default'
		});
	}
	
	$scope.cancelbooking = function(agentId,nonrefund){
		console.log(agentId);
		console.log(nonrefund);
		if(nonrefund == "true"){
			ngDialog.close();
			notificationService.error("This booking can not be cancel");
		}else{
		$http.get("/getbookingcancel/"+agentId).success(function(response){
			
			notificationService.success("Cancel Booking");
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
	
	$scope.showPDF = function(){
		console.log("Show PDF");
	
		$http.get("/generatePDF").success(function(response){
			console.log("Yessss");
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
	
	$scope.searchby = {};
	$scope.AgentId = $cookieStore.get('AgentId');
	$scope.AgentCompany = $cookieStore.get('AgentCompany');
	
	console.log($scope.AgentCompany);
	
		
	$scope.init = function(hotelAllData){
		
		angular.forEach(hotelAllData.hotellist, function(obj, index){ ///hotel_profile
					//if(index == 0){
						$scope.img = "/searchHotelInfo/getHotelImagePath/"+hotelAllData.hotellist[index].supplierCode+"/"+index;
						console.log($scope.img);
					//}
				hotelAllData.hotellist[index].imgPaths = $scope.img;
						
		});
		
		console.log(hotelAllData.hotellist);
		$scope.hotelAllData = hotelAllData;
		console.log($scope.hotelAllData);
		$scope.hotellistInfo = hotelAllData.hotellist;
		
		
		
		$scope.searchby.countryName = $scope.hotellistInfo[0].countryName;
		$scope.searchby.countryCode = $scope.hotellistInfo[0].countryCode;
		
		$scope.searchby.cityName =$scope.hotellistInfo[0].cityName;
		$scope.searchby.cityCode =$scope.hotellistInfo[0].cityCode;
		$scope.searchby.checkIn  = $scope.hotellistInfo[0].checkIn;
		$scope.searchby.checkOut = $scope.hotellistInfo[0].checkOut;
		$scope.searchby.nationalityName = $scope.hotellistInfo[0].nationalityName;
		$scope.searchby.nationalityCode = $scope.hotellistInfo[0].nationality;
		
		
		console.log($scope.searchby);
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
				$scope.img = "/searchHotelInfo/getHotelImagePath/"+data.hotellist[index].supplierCode+"/"+index;
				obj.imgPaths = $scope.img;
							
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
			$scope.findHotelData.cityCode = $scope.hotelAllData.hotellist[0].cityCode;
			$scope.findHotelData.nationalityCode = $scope.hotelAllData.hotellist[0].nationality;
			//$scope.findHotelData.id = $scope.hotelAllData.hotellist[0].startRating;
			$scope.findHotelData.amenitiesCheck = $scope.amenities_check;
			$scope.findHotelData.servicesCheck = $scope.services_check;
			$scope.findHotelData.locationCheck = $scope.location_check;
			$scope.findHotelData.starCheck = $scope.star_check;
			$scope.findHotelData.sortByRating = $scope.sortByStar;
			$scope.findHotelData.sortData = $scope.sort;
			$scope.findHotelData.hotelname = $scope.hotelName;
			$scope.findHotelData.noSort = 0;
				console.log($scope.findHotelData);
			
			$http.post('/findHotelByData', $scope.findHotelData).success(function(data){
				console.log('success');
				console.log(data);
				$scope.hotellistInfo = data.hotellist;
				angular.forEach(data.hotellist, function(obj, index){ 
					$scope.img = "/searchHotelInfo/getHotelImagePath/"+data.hotellist[index].supplierCode+"/"+index;
					$scope.hotellistInfo[index].imgPaths = $scope.img;
								
				});
				$scope.hotelAllData.totalHotel = data.totalHotel;
				console.log($scope.hotellistInfo);
				
			}).error(function(data, status, headers, config) {
				console.log('ERROR');
			});
	}
	
	   $scope.sortData = function(sortD){
		   console.log(sortD);
		 //  if($scope.sort == 0){
			   $scope.sort = sortD;
			   
		  // }else{
			   $scope.sort = sortD;
		 //  }
		   console.log($scope.sort);
		  		   
		   console.log($scope.hotellistInfo);
			$scope.findHotelData.checkIn = $scope.hotelAllData.hotellist[0].checkIn;
			$scope.findHotelData.checkOut = $scope.hotelAllData.hotellist[0].checkOut;
			$scope.findHotelData.cityCode = $scope.hotelAllData.hotellist[0].cityCode;
			$scope.findHotelData.nationalityCode = $scope.hotelAllData.hotellist[0].nationality;
			//$scope.findHotelData.id = $scope.hotelAllData.hotellist[0].startRating;
			$scope.findHotelData.amenitiesCheck = $scope.amenities_check;
			$scope.findHotelData.servicesCheck = $scope.services_check;
			$scope.findHotelData.locationCheck = $scope.location_check;
			$scope.findHotelData.starCheck = $scope.star_check;
			$scope.findHotelData.sortByRating = $scope.sortByStar;
			$scope.findHotelData.sortData = $scope.sort;
			$scope.findHotelData.hotelname = $scope.hotelName;
			$scope.findHotelData.noSort = 1;
			
				console.log($scope.findHotelData);
			
			$http.post('/findHotelByData', $scope.findHotelData).success(function(data){
				console.log('success');
				console.log(data);
				$scope.hotellistInfo = data.hotellist;
				angular.forEach(data.hotellist, function(obj, index){ 
					$scope.img = "/searchHotelInfo/getHotelImagePath/"+data.hotellist[index].supplierCode+"/"+index;
					$scope.hotellistInfo[index].imgPaths = $scope.img;
								
				});
				$scope.hotelAllData.totalHotel = data.totalHotel;
				console.log($scope.hotellistInfo);
				
			}).error(function(data, status, headers, config) {
				console.log('ERROR');
			});
		   
		   
		}
	
	   $scope.hotelwisesearch = function(hotelName){
		   console.log(hotelName);
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
			$scope.findHotelData.hotelname = hotelName;
			$scope.findHotelData.noSort = 1;
			
			console.log($scope.findHotelData);	
			
			$http.post('/findHotelByData', $scope.findHotelData).success(function(data){
				console.log('success');
				console.log(data);
				$scope.hotellistInfo = data.hotellist;
				angular.forEach(data.hotellist, function(obj, index){ 
					$scope.img = "/searchHotelInfo/getHotelImagePath/"+data.hotellist[index].supplierCode+"/"+index;
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
	
	$scope.star_check = [];
	$scope.starWiseHotel = function(starNo){
		if(jQuery("#star"+starNo).parent().attr("class") !="active") {
			$scope.star_check.push(starNo);
			$scope.star_check.push(starNo+1);
		}else{
			DeleteStar(starNo);
		}
		console.log($scope.star_check);
	}
	
	DeleteStar = function(starNo){
		console.log("huhuhuh");
		angular.forEach($scope.star_check, function(obj, index){
			if (starNo == obj) {
				$scope.star_check.splice(index, 1);
				$scope.star_check.splice(index, 2);
		       	return;
		    };
		});
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
				$scope.img = "/searchHotelInfo/getHotelImagePath/"+data.hotellist[index].supplierCode+"/"+index;
				$scope.hotellistInfo[index].imgPaths = $scope.img;
							
			});
			$scope.hotelAllData.totalHotel = data.totalHotel;
			console.log($scope.hotellistInfo);
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	}
	
	
	
	
});



travelBusiness.controller('hotelDetailsController', function ($scope,$http,$filter,ngDialog,$cookieStore) {
	
	$scope.AgentId = $cookieStore.get('AgentId');
	$scope.AgentCompany = $cookieStore.get('AgentCompany');
	$scope.imgArray = [];
	$scope.searchby = {};
	$scope.totalStayDays = 0;
	$scope.noOfRoomShow = 1;
	
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
	
	$scope.init = function(hotel){
		
		$scope.hotel = hotel;
	
		$scope.noOfRoomShow = 1;
		
		$http.get('/getAllImgs/'+$scope.hotel.supplierCode).success(function(response){
			console.log(response);
			angular.forEach(response, function(obj, index){
				$scope.imgArray[index] = "/searchHotelGenImg/getHotelGenImg/"+$scope.hotel.supplierCode+"/"+obj.indexValue;
			});
			console.log($scope.imgArray);
		});
		
		
		angular.forEach(hotel.hotelbyRoom, function(obj, index){ 
			$scope.roomImg = "/hoteldetailpage/getHotelRoomImagePath/"+hotel.hotelbyRoom[index].roomId+"?d="+new Date().getTime();
			$scope.hotel.hotelbyRoom[index].roomimgPaths = $scope.roomImg;
						
		});
		
		angular.forEach(hotel.hotelbyRoom, function(obj, index){ 
			angular.forEach(obj.hotelRoomRateDetail[0].cancellation, function(obj1, index1){ 
				if(obj1.days == "012" || obj1.days == "016" || obj1.days == "018"){
					obj1.days = hotel.cancellation_date_diff;
				}else{
					obj1.days = parseInt(obj1.days) + hotel.cancellation_date_diff;
				}
			});
		});
		
		console.log($scope.hotel);
		
		
		$scope.searchby.countryName = $scope.hotel.countryName;
		$scope.searchby.countryCode = $scope.hotel.countryCode;
		
		$scope.searchby.cityName =$scope.hotel.cityName;
		$scope.searchby.cityCode =$scope.hotel.cityCode;
		$scope.searchby.checkIn  = $scope.hotel.checkIn;
		$scope.searchby.checkOut = $scope.hotel.checkOut;
		$scope.searchby.nationalityName = $scope.hotel.nationalityName;
		$scope.searchby.nationalityCode = $scope.hotel.nationality;
		
	}
	
	$scope.showAllRooms = function(){
		console.log($scope.hotel);
		var countRoom = 0;
		console.log(countRoom);
		angular.forEach($scope.hotel.hotelbyRoom, function(obj, index){
			countRoom = countRoom + index;
		});
		console.log(countRoom);
		$scope.noOfRoomShow = countRoom+1;
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
	
	$scope.cAllow = "false";
	$scope.childnotallowMsg = "false";
	$scope.adultTotal = 0;
	$scope.childTotal = 0;
	$scope.adultString = "1 Adult";
	$scope.countNoOfRoom = 1;
	
	$scope.showRateAdultwise=function(adultValue,childallow,index){
		var roomNo = (index+1);
		if(roomNo == undefined){
			roomNo = 1;
		}
		
		//$scope.addRooms[index].total = $scope.totalParPerson;
		console.log(roomNo);
		console.log(adultValue);
		console.log(childallow);
		
		$scope.adultString = adultValue;
		
		$scope.childselected = [];
		
		$scope.childselected.push({
			age:"",
			freeChild:"",
			breakfast:"",
			childRate:""
			
		});
		
		$scope.rateDatedetail = [];
		var total = 0;
		var flag = 0;
		
		var divDays = 0;
		var remDay = 0;
		var totalStayDay = 0;
		var typeOFFreeStey = 0;
		var daysLess = 0;
		
		if($scope.ratedetail.hotelbyRoom[0].applyPromotion == 1){
			divDays = parseInt($scope.ratedetail.datediff / $scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].stayDays);
			remDay = $scope.ratedetail.datediff % $scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].stayDays;
			totalStayDay = (divDays * $scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].payDays) + remDay;
			daysLess = $scope.ratedetail.datediff - totalStayDay;
			typeOFFreeStey = $scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].typeOfStay;
		}
		
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
	
		//if(roomNo != undefined){
			$scope.total = $scope.totalParPerson * 1;// roomNo;
		//}
	
		
		var arr1 = adultValue.split(" ");
		console.log(arr1[0]);
		
		$scope.adultTotal = arr1[0];
		//$scope.childTotal = 0;
		
		$scope.commanPromotionFunction();
		$scope.batchMarkupFunction();
		
		$scope.addRooms[index].total = $scope.total;
		$scope.addRooms[index].rateDatedetail = $scope.rateDatedetail;
		$scope.addRooms[index].childselected = $scope.childselected;
		$scope.breakfastFunction(); 
		$scope.flag = flag;
		console.log($scope.rateDatedetail);
		console.log($scope.total);
		console.log(flag);
		
		
		
		if(childallow == "true"){
			
		
		console.log($scope.ratedetail);
		
		
		angular.forEach($scope.ratedetail.hotelbyRoom,function(value,key){
			if(value.maxAdultsWithchild >= arr1[0]){
				$scope.cAllow = "true";
				$scope.childnotallowMsg = "false";
			}else{
				
				$scope.cAllow = "false";
				$scope.childnotallowMsg = "true";
			}
		});
		
      }else{
    	  $scope.cAllow = "false";
    	  $scope.childnotallowMsg = "false";
      }
	}
	

	
	$scope.hidechild = function(roomCount){
		$scope.cAllow = "false";
		$scope.childnotallowMsg = "false";
		
		$scope.total = $scope.totalParPerson * roomCount;
	}
	
	$scope.rateDatedetailRoomWise = []; 
	$scope.childcount = [];
	$scope.dateWiseInfo = function(roomid){
		$scope.addRooms.push({});
		$scope.addRooms[0].adult = "1 Adult";
		console.log($scope.hotel);
		
			$scope.rateDatedetail = [];
		
		$http.get('/getDatewiseHotelRoom/'+$scope.hotel.checkIn+"/"+$scope.hotel.checkOut+"/"+$scope.hotel.nationality+"/"+$scope.hotel.supplierCode+"/"+roomid+"/"+$scope.hotel.bookingId)
		.success(function(response){
			console.log(response);
			$scope.ratedetail = response;
			console.log($scope.ratedetail);
			
			
			
			var total = 0;
			var flag =0;
			
			var stayCountar = 0;
			
			$scope.adultString = "1 Adult";
			
			if($scope.ratedetail.bookingId != null){
				$scope.addRooms = $scope.ratedetail.hotelBookingDetails.passengerInfo;
				//$scope.rateDatedetailRoomWise = $scope.ratedetail.hotelBookingDetails.passengerInfo[0].rateDatedetail;
				
				angular.forEach($scope.addRooms,function(value,key){
					$scope.rateDatedetail = [];
					$scope.fillrateDatedetai(value.adult);
					value.total = $scope.totalParPerson
					$scope.total = $scope.totalParPerson;
					$scope.commanPromotionFunction();
					$scope.batchMarkupFunction();
					console.log($scope.total);
					console.log($scope.rateDatedetail);
					var childTotalRate = 0;
					$scope.childselected = value.childselected;
					angular.forEach(value.childselected,function(value1,key1){
						childTotalRate = parseInt(childTotalRate + value1.freeChild);
					});
					 $scope.total = $scope.total + childTotalRate
					value.total = $scope.total;
					value.rateDatedetail = $scope.rateDatedetail;
				});
				$scope.breakfastFunction(); 
				//$scope.rateDatedetail = 
				console.log($scope.addRooms);
				$scope.rateDatedetailRoomWise = $scope.ratedetail.hotelBookingDetails.passengerInfo[0].rateDatedetail;
			}else{
			
			//$scope.fillrateDatedetai($scope.adultString);
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
			$scope.adultTotal = 1;
			$scope.childTotal = 0;
			
			
			/*angular.forEach($scope.rateDatedetail,function(value,key){
				
			}*/
			
			$scope.commanPromotionFunction();
			$scope.batchMarkupFunction();
			
			$scope.addRooms[0].total = $scope.total;
			$scope.addRooms[0].rateDatedetail = $scope.rateDatedetail; 
			$scope.rateDatedetailRoomWise = $scope.rateDatedetail;
			
			$scope.breakfastFunction(); 
			
			//$scope.flag = flag;
			console.log($scope.rateDatedetail);
		}
			$scope.flag = flag;
			console.log(total);
			console.log(flag);
			$scope.childcount = [];
			angular.forEach($scope.ratedetail.hotelbyRoom, function(value, index){
				for(var i=0;i<value.maxAdultsWithchild;i++){
					
					$scope.childcount[i] = i+1;
				}
			});
			$scope.cAllow = "false";
			console.log($scope.childcount);
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
		ngDialog.open({
			template: '/assets/resources/html/show_Date_wise_info_InHotel.html',
			scope : $scope,
			//controller:'hoteProfileController',
			className: 'ngdialog-theme-default'
		});
		
		$scope.selectextraBed = function(){
			console.log("------Select---------");
		}
	}
	
	$scope.batchMarkupFunction = function(){
		
		console.log($scope.totalStayDays);
		if($scope.ratedetail.batchMarkup.selected == 0){
			if($scope.totalStayDays == null || $scope.totalStayDays == "" || $scope.totalStayDays == 0){
				var markupCount = $scope.ratedetail.datediff * $scope.ratedetail.batchMarkup.flat;
			}else{
				var markupCount = $scope.totalStayDays * $scope.ratedetail.batchMarkup.flat;
			}
			
			console.log(markupCount);
			$scope.total = $scope.total + markupCount;
			
			angular.forEach($scope.rateDatedetail, function(value, index){
				value.rate = value.rate + $scope.ratedetail.batchMarkup.flat;
			});
			
		}else if($scope.ratedetail.batchMarkup.selected == 1){
		   var markupPer =	$scope.total / 100 * $scope.ratedetail.batchMarkup.percent;
		   console.log(markupPer);
		   $scope.total =  $scope.total + markupPer;
		   
		   angular.forEach($scope.rateDatedetail, function(value, index){
				value.rate = value.rate + value.rate / 100 * $scope.ratedetail.batchMarkup.percent;
			});
		   
		}
		
		console.log($scope.rateDatedetail);
	}
	
	$scope.commanPromotionFunction = function(){
		
		var divDays = 0;
		var remDay = 0;
		var totalStayDay = 0;
		$scope.totalStayDays = 0;
		var typeOFFreeStey = 0;
		var daysLess = 0;
		
		console.log($scope.total);
		
		if($scope.ratedetail.hotelbyRoom[0].applyPromotion == 1){	
		
			typeOFFreeStey = $scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].typeOfStay;
			if($scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].multiple == true){
				divDays = parseInt($scope.ratedetail.datediff / $scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].stayDays);
				remDay = $scope.ratedetail.datediff % $scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].stayDays;
				totalStayDay = (divDays * $scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].payDays) + remDay;
				daysLess = $scope.ratedetail.datediff - totalStayDay;
				$scope.totalStayDays = totalStayDay;
			
			}
			if($scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].multiple == false){
				//totalStayDay = $scope.ratedetail.datediff; //parseInt($scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].stayDays) + parseInt($scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].payDays);
				divDays = parseInt($scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].stayDays) - parseInt($scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].payDays);
				
				//daysLess = daysLess + totalStayDay;
				console.log(divDays);
			}
			
			
			var a = [];
			var cheapestTotal = 0;
			var ExpensivesTotal = 0;
			var firstNoTotal = 0;
			var standardTotal = 0;
			var i=0;
			
			for(i=0;i<$scope.rateDatedetail.length;i++){
				a[i] = $scope.rateDatedetail[i].rate;
				console.log($scope.rateDatedetail[i].rate);
			}
			
			//if($scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].multiple == false){}
			
			 if(typeOFFreeStey ==  "First Night"){
				
				 if($scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].multiple == false){
					 for(i=0; i<divDays; i++){
						 firstNoTotal = firstNoTotal + a[i];
						 if($scope.ratedetail.batchMarkup.selected == 0){
							 	firstNoTotal = firstNoTotal + $scope.ratedetail.batchMarkup.flat
						 }
						}
				 }else{
				 for(i=0; i<daysLess; i++){
					 firstNoTotal = firstNoTotal + a[i];
					if($scope.ratedetail.batchMarkup.selected == 0){
						 	firstNoTotal = firstNoTotal + $scope.ratedetail.batchMarkup.flat
					 }
					}
			     }
				// firstNoTotal = firstNoTotal * $scope.countNoOfRoom; 
				 $scope.total = $scope.total - firstNoTotal;
			  }
			  if(typeOFFreeStey ==  "Standard Policy"){
				  var stayCountar = 0;
				  if($scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].multiple == false){
						 for(i=parseInt($scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].payDays); i<parseInt($scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].payDays) + parseInt(divDays); i++){
							console.log(i);
							 standardTotal = standardTotal + a[i];
							 if($scope.ratedetail.batchMarkup.selected == 0){
								 standardTotal = standardTotal + $scope.ratedetail.batchMarkup.flat
							 }
							 console.log(standardTotal);
							}
					 }else{
						 for(i=0; i<a.length; i++){
							 if(stayCountar < $scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].payDays){
								 stayCountar = stayCountar + 1;
								 console.log(stayCountar);
							 }else if(stayCountar >= $scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].payDays){
								 standardTotal = standardTotal + a[i];	
								 if($scope.ratedetail.batchMarkup.selected == 0){
									 standardTotal = standardTotal + $scope.ratedetail.batchMarkup.flat
								 }
								 console.log(a[i]);
								 console.log(standardTotal);
								 stayCountar = stayCountar + 1;
								 if(stayCountar == $scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].stayDays){
									 stayCountar = 0; 
								 }
							 }
							 console.log(stayCountar);
						 }
					 }
				  console.log(standardTotal);
				 // standardTotal = standardTotal * $scope.countNoOfRoom;
				  console.log(standardTotal);
				  $scope.total = $scope.total - standardTotal;
				
			   }
			
			if(typeOFFreeStey ==  "Cheapest night"){
				
				a.sort(function(a, b){return a-b});

				 if($scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].multiple == false){
					 for(i=0; i<divDays; i++){
							cheapestTotal = cheapestTotal + a[i];
							if($scope.ratedetail.batchMarkup.selected == 0){
								cheapestTotal = cheapestTotal + $scope.ratedetail.batchMarkup.flat
							 }
						}
				 }else{
					 for(i=0; i<daysLess; i++){
							cheapestTotal = cheapestTotal + a[i];
							if($scope.ratedetail.batchMarkup.selected == 0){
								cheapestTotal = cheapestTotal + $scope.ratedetail.batchMarkup.flat
							 }
						}
				 }
				
				
				//cheapestTotal = cheapestTotal * $scope.countNoOfRoom;
				console.log(cheapestTotal);
				$scope.total = $scope.total - cheapestTotal;
				
			}
			if(typeOFFreeStey ==  "Most expensive night/s"){
				
							
				a.sort(function(a, b){return b-a});
				
				if($scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].multiple == false){
					 for(i=0; i<divDays; i++){
						 ExpensivesTotal = ExpensivesTotal + a[i];
						 if($scope.ratedetail.batchMarkup.selected == 0){
							 ExpensivesTotal = ExpensivesTotal + $scope.ratedetail.batchMarkup.flat
							 }
						}
				 }else{
					 for(i=0;i<daysLess;i++){
						 ExpensivesTotal = ExpensivesTotal + a[i];
						 if($scope.ratedetail.batchMarkup.selected == 0){
							 ExpensivesTotal = ExpensivesTotal + $scope.ratedetail.batchMarkup.flat
							 }
					 }
				 }
				
			//	ExpensivesTotal = ExpensivesTotal * $scope.countNoOfRoom;
				$scope.total = $scope.total - ExpensivesTotal;
			}
			console.log($scope.total);
		
		}
	}
	
	$scope.finalTotal = 0;
	$scope.finalTotalDetails = [];
	
	$scope.breakfastFunction = function(){
		$scope.finalTotal = 0;
		var adultNo = 0;
		var childNo = 0;
		
		console.log("%%%%%%%%%%%%%%");
		console.log($scope.addRooms);
		console.log("%%%%%%%%%%%%%%");
	
		
		angular.forEach($scope.addRooms,function(value,key){
			var arr =value.adult.split(" ");
			adultNo = adultNo + parseInt(arr[0]);
			if(value.noOfchild != undefined){
				angular.forEach(value.childselected,function(value1,key1){
					if(value1.age != ""){
						console.log("-----------------");
						childNo = childNo + 1;
						console.log(value.noOfchild);
						console.log(childNo);
					}
				});
			}
			console.log(value.total);
			$scope.finalTotal = $scope.finalTotal + parseInt(value.total);
			
		});
		
		console.log($scope.finalTotal);
		
		var divDays = 0;
		
		if($scope.ratedetail.hotelbyRoom[0].applyPromotion == 1){	
			
			
			typeOFFreeStey = $scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].typeOfStay;
			if($scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].multiple == true){
				divDays = parseInt($scope.ratedetail.datediff / $scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].stayDays);
			
			}
			if($scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].multiple == false){
				divDays = parseInt($scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].stayDays) - parseInt($scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].payDays);
				
			}
			
		console.log(adultNo);
		console.log(childNo);
		$scope.breakfastAddfree = 0;
		if($scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].breakfast == true){
			
			var adultbreakfastRate = 0;
			var childbreakfastRate = 0;
			var finalBreakfastCharg = 0;
			var bothAdultChildbreakfast = 0;
			adultbreakfastRate = adultNo * parseInt($scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].adultRate);
			childbreakfastRate = childNo * parseInt($scope.ratedetail.hotelbyRoom[0].specials[0].markets[0].childRate);
			
			bothAdultChildbreakfast = adultbreakfastRate + childbreakfastRate;
			finalBreakfastCharg = bothAdultChildbreakfast / 100 * parseInt($scope.ratedetail.breakfackRate);
			console.log(finalBreakfastCharg);
			
			$scope.breakfastAddfree = divDays *  finalBreakfastCharg;
		   console.log($scope.breakfastAddfree);
		   $scope.finalTotal = $scope.finalTotal+ $scope.breakfastAddfree;
	    }
		}
		console.log($scope.finalTotal);
		$scope.finalTotalDetails = [];
		angular.forEach($scope.addRooms,function(value,key){
//			$scope.finalTotalDetails.push = ({adult:"",noOfchild:"",total:""});
			$scope.finalTotalDetails.push({});
			$scope.finalTotalDetails[key].adult = value.adult;
			$scope.finalTotalDetails[key].noOfchild = value.noOfchild;
			$scope.finalTotalDetails[key].total = value.total;
		});
		
		
		console.log($scope.finalTotalDetails);
		
	}
	
	$scope.childselected = [];
	$scope.Showchild = function(countAdult){
		
		if(countAdult == undefined){
			countAdult = "1 Adult";
		}
		
		$scope.childselected = [];
			
		console.log($scope.totalParPerson);
		var arr = countAdult.split(" ");
		console.log(countAdult);
		console.log(arr[0]);
		console.log($scope.ratedetail);
		
		angular.forEach($scope.ratedetail.hotelbyRoom,function(value,key){
			if(value.maxAdultsWithchild >= arr[0]){
				$scope.cAllow = "true";
				$scope.childselected.push({
					age:"",
					freeChild:"",
					breakfast:"",
					childRate:""
					
				});
				$scope.childnotallowMsg = "false";
			}else{
				$scope.childnotallowMsg = "true";
				$scope.cAllow = "false";
			}
		});
		
				
	}
	
	$scope.parChildAge = function(age,index,pIndex){
		//freechild
		
		/*if(roomCount == undefined){
			roomCount = 1;
		}
		console.log(roomCount);*/
	//	console.log($scope.addRooms[pIndex].total);
		
		$scope.total = $scope.totalParPerson;
		//$scope.batchMarkupFunction();
		var totalcount = $scope.total;// * arr[0]; //* 1;//roomCount;
		console.log(totalcount);
		
		$scope.rateDatedetail = [];

		$scope.fillrateDatedetai($scope.adultString);
		console.log($scope.total);
		//$scope.addRooms
		//$scope.childselected[index] = $scope.addRooms[pIndex].childselected[index];
		console.log($scope.childselected);
		$scope.childselected[index].age = age;
		
		angular.forEach($scope.ratedetail.hotelbyRoom, function(value, count){
			
			if(value.breakfastInclude == "false"){
				$scope.childselected[index].breakfast = value.breakfastRate;
			}else{
				$scope.childselected[index].breakfast = "";
			}
			
			if(value.childAge > $scope.childselected[index].age){
				
				$scope.childselected[index].freeChild = "Free";
				
			}else{
				$scope.childselected[index].breakfast = "";
				angular.forEach(value.roomchildPolicies, function(value1, count1){
					if(value1.allowedChildAgeFrom <= $scope.childselected[index].age &&  value1.allowedChildAgeTo >= $scope.childselected[index].age){
						$scope.childselected[index].freeChild = value1.extraChildRate;
					}else{
						var feechildRate = $scope.childselected[index].freeChild;
						if(value.freeChild != "Free" && value.freeChild != ""){
							if(feechildRate < 0){
								feechildRate = 0; 
							}
						}
						if(value.freeChild != "Free" && value.freeChild != ""){
							totalcount = parseInt(totalcount) - feechildRate;
						}
					}
				});
			}
			
		});
		console.log($scope.childselected);
		$scope.addRooms[pIndex].childselected = $scope.childselected;
		
		var totalchileValue = 0;
		angular.forEach($scope.childselected, function(value, count){
			if(value.breakfast != ""){
				totalchileValue = parseInt(totalchileValue) + parseInt(value.breakfast);
			}
			if(value.freeChild != "Free" && value.freeChild != ""){
				totalchileValue = parseInt(totalchileValue) + parseInt(value.freeChild);
			}
		});
		console.log(totalchileValue);
		
		$scope.total = parseInt(totalcount) + parseInt(totalchileValue);
		
	
		console.log($scope.total);
		$scope.commanPromotionFunction();
		$scope.batchMarkupFunction();
	
		$scope.addRooms[pIndex].total = $scope.total;
		$scope.breakfastFunction(); 
		
	}
	
	$scope.addRooms = [];
	
	$scope.newRoom = function($event,index){
		console.log(index);
		$scope.addRooms.push( {  } );
		$scope.addRooms[index].adult = "1 Adult";
		
		$event.preventDefault();
		$scope.countTotal(index);
		
		
		//newRoom
	};
	
	$scope.lessTotal = function($event,index){
		console.log(index);
		/*$scope.addRooms[index] = [];//  .splice(index, 1);
		
		  
		
		$scope.breakfastFunction(); 
		console.log($scope.addRooms);*/
		/*angular.forEach($scope.addRooms,function(value,key){
			
			if(index == key){
				$scope.finalTotal = $scope.finalTotal - value.total;
			}
		});*/
	}
	
	
	$scope.noOfchildSelect = function(selectedChild,roomCount){
		//var roomCountAddOne = roomCount+1;
		$scope.childselected = [];
		console.log(selectedChild);
		for(var i=0;i<selectedChild;i++){
			//$scope.childselected[i] = i+1;
			$scope.childselected.push({
				age:"",
				freeChild:"",
				breakfast:"",
				childRate:""
				
			});
		}
		console.log($scope.childselected);
		console.log($scope.totalParPerson);
		$scope.addRooms[roomCount].childselected = $scope.childselected;
		console.log($scope.addRooms);
		
	//	if(roomCount == undefined){
		//	roomCount = 1;
		//}
		$scope.total = $scope.totalParPerson * (roomCount+1);
		$scope.rateDatedetail = [];

		$scope.fillrateDatedetai($scope.adultString);
		
		console.log($scope.adultTotal);
		if($scope.adultTotal == 0){
			$scope.adultTotal = 1;
		}
		$scope.childTotal = selectedChild;
		$scope.commanPromotionFunction();
		$scope.batchMarkupFunction();
	
		$scope.breakfastFunction(); 
	
	}
	
	
	$scope.fillrateDatedetai = function(adultValues){
		
		var total =0;
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
						if(value3.adult == adultValues){
			
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
		
		$scope.totalParPerson = total;
	}
	
	
	$scope.availableFlag = [];

	$scope.countTotal = function(roomNo){
		console.log(roomNo);
		$scope.countNoOfRoom = roomNo;
		console.log($scope.ratedetail);
		
		console.log($scope.childselected);
		$scope.adultString = "1 Adult";
		$scope.fillrateDatedetai($scope.adultString);
		/*$scope.dateWiseInfo($scope.ratedetail.hotelbyRoom[0].roomId)*/
		console.log($scope.totalParPerson);
		
		$scope.availableFlag = [];
		var totalchileValue = 0;
		/*angular.forEach($scope.childselected, function(value, count){
		if(value.breakfast != ""){
			totalchileValue = parseInt(totalchileValue) + parseInt(value.breakfast);
		}
		if(value.freeChild != "Free"  && value.freeChild != ""){
			totalchileValue = parseInt(totalchileValue) + parseInt(value.freeChild);
		}
	});*/
	console.log(totalchileValue);
		console.log($scope.addRooms);
	
	
	   console.log($scope.totalParPerson);
		$scope.total = $scope.totalParPerson * 1;//(roomNo+1);
		console.log(totalchileValue);
	//	$scope.total = $scope.total + totalchileValue;
		
		$scope.rateDatedetail = [];

		$scope.fillrateDatedetai($scope.adultString);
		
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
						if(value2.availableRoom < (roomNo+1)){
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
		$scope.addRooms[roomNo].rateDatedetail = $scope.rateDatedetail;
		
		console.log($scope.total);
		$scope.commanPromotionFunction();
		console.log($scope.total);
		$scope.batchMarkupFunction();

		$scope.addRooms[roomNo].total = $scope.total;
		
		$scope.breakfastFunction();
		
	}
	
	$scope.showDateWiseDetails = function(index){
		console.log("Hiii..Bye");
		console.log(index);
		$scope.rateDatedetailRoomWise = [];
		console.log($scope.addRooms[index].rateDatedetail);
		$scope.rateDatedetailRoomWise = $scope.addRooms[index].rateDatedetail;
		console.log($scope.rateDatedetailRoomWise);
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


travelBusiness.controller('hotelBookingController', function ($scope,$http,$filter,ngDialog,notificationService,$window,$cookieStore) {
	
	$scope.AgentId = $cookieStore.get('AgentId');
	$scope.AgentCompany = $cookieStore.get('AgentCompany');
	
	$scope.init = function(hotel){
		$scope.hotel = hotel;
		
		$http.get("/getSalutation").success(function(response){
			$scope.salutation=response;
			console.log($scope.salutation);
		});
		
		
		angular.forEach(hotel.hotelbyRoom, function(obj, index){ 
			$scope.Img = "/hotelBookingpage/getHotelImagePath/"+hotel.supplierCode+"/"+index;
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
		$scope.hotel.hotelBookingDetails.travellersalutation = $scope.traveller.salutation;
		$scope.hotel.hotelBookingDetails.travellerfirstname = $scope.traveller.firstname;
		$scope.hotel.hotelBookingDetails.travellermiddlename = $scope.traveller.middlename;
		$scope.hotel.hotelBookingDetails.travellerlastname = $scope.traveller.lastname;
		$scope.hotel.hotelBookingDetails.travellerpassportNo = $scope.traveller.passportNo;
		//$scope.hotel.hotelBookingDetails.travelleraddress = $scope.traveller.address;
		$scope.hotel.hotelBookingDetails.travellercountry = $scope.traveller.country;
		$scope.hotel.hotelBookingDetails.travellerphnaumber = $scope.traveller.phnaumber;
		$scope.hotel.hotelBookingDetails.travelleremail = $scope.traveller.email;
		$scope.hotel.hotelBookingDetails.nonSmokingRoom = $scope.traveller.nonSmokingRoom;
		$scope.hotel.hotelBookingDetails.twinBeds = $scope.traveller.twinBeds;
		$scope.hotel.hotelBookingDetails.lateCheckin = $scope.traveller.lateCheckin;
		$scope.hotel.hotelBookingDetails.largeBed = $scope.traveller.largeBed;
		$scope.hotel.hotelBookingDetails.highFloor = $scope.traveller.highFloor;
		$scope.hotel.hotelBookingDetails.earlyCheckin = $scope.traveller.earlyCheckin;
		$scope.hotel.hotelBookingDetails.airportTransfer = $scope.traveller.airportTransfer;
		$scope.hotel.hotelBookingDetails.airportTransferInfo = $scope.traveller.airportTransferInfo;
		$scope.hotel.hotelBookingDetails.enterComments = $scope.traveller.enterComments;
		//$scope.hotel.hotelbyDate = null;
		console.log($scope.hotel);
		
		
		
		$scope.flagA = 0;
		$http.post('/saveHotelBookingInfo',$scope.hotel).success(function(data){
			
			console.log("Success");
			console.log(data);
			console.log("-----------");
    		if(data == 0){
    			$scope.flagA = 0;
    			 notificationService.success("Room Book Successfully");
    		 $window.location.replace("http://li664-78.members.linode.com:9989/");
    		}else{
    			$scope.flagA = 1;
    		}
    		
    	}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
	}
	

});	
