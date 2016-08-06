

travelBusiness.controller('AgentBookingController', function ($scope,$http,$filter,ngDialog,$cookieStore,notificationService,$cookies) {
	
	
	var session = $cookies['PLAY_SESSION'];
	
	if(session != undefined){
		$scope.sessionValue = 1
	}else{
		$scope.sessionValue = 0;
	}
	
	
	$scope.pageNumber;
	$scope.pageSize;
	$scope.fromData = "1";
	$scope.toDate="1";
	$scope.status = "1";
	$scope.creditUsed;
	$scope.agentEdit;
	$scope.agentProfile = false;
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
		
		angular.forEach($scope.agentBook,function(value,key){
			value.latestCancellationDate = $filter('date')(value.latestCancellationDate, "dd-MM-yyyy")
		});
		$scope.agentEdit = hotelAllData.results[0].agent[0];
		$scope.creditUsed = parseInt($scope.agentBook[0].agent[0].creditLimit)-parseInt($scope.agentBook[0].agent[0].availableLimit);
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
			angular.forEach($scope.agentBook,function(value,key){
				value.latestCancellationDate = $filter('date')(value.latestCancellationDate, "dd-MM-yyyy")
			});
			if(totalPages == 0) {
				$scope.pageNumber = 0;
			}
			
		});
	};
	
	$scope.openVoucherPopUp = function(booking){
		$scope.voucherPdg = "/hotel_profile/getVoucherPdfPath/"+booking.bookingId;
		
		ngDialog.open({
			template: '/assets/resources/html/htmltemplet/showVoucherPdf.html',
			scope : $scope,
			//controller:'hoteProfileController',
			className: 'ngdialog-theme-default'
		});
	}
	
	$scope.showtable = function(checkIn,checkOut,guest,status, bookingId){
		console.log(guest);
		console.log(status);
		console.log(checkIn);
		console.log(checkOut);
		console.log(bookingId);
		if(guest=="")
		{
			guest="undefined";
		}
		if(bookingId == "" || bookingId == undefined){
			bookingId = "0";
		}
		
		$http.get("/getagentInfobynm/"+currentPage+"/"+checkIn+"/"+checkOut+"/"+guest+"/"+status+"/"+bookingId).success(function(response1){
		console.log(response1);
		totalPages = response1.totalPages;
		currentPage = response1.currentPage;
		$scope.pageNumber = response1.currentPage;
		$scope.pageSize = response1.totalPages;
		$scope.agentBook = response1.results;
		angular.forEach($scope.agentBook,function(value,key){
			value.latestCancellationDate = $filter('date')(value.latestCancellationDate, "dd-MM-yyyy")
		});
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
			angular.forEach($scope.agentBook,function(value,key){
				value.latestCancellationDate = $filter('date')(value.latestCancellationDate, "dd-MM-yyyy")
			});
			if(totalPages == 0) {
				$scope.pageNumber = 0;
			}
		});
	}
	$scope.selectDate = {};
	$scope.selectStatus = function(status){
		$scope.selectDate.status = status;
		$scope.showoAgentDataDateWise($scope.selectDate);
	}
	
	$scope.showAgentProfile = function(){
		$scope.agentProfile = true;
	}
	$scope.passengerInfo;
	$scope.rateDatewise = [];
	$scope.showdateWiseView = function(agentinfo){
		console.log(agentinfo.id);
		$scope.roomIndex = 1;
		$scope.passengerInfo = agentinfo.passengerInfo;
		$scope.rateDatewise = $scope.passengerInfo[0].rateDatedetail
		console.log($scope.rateDatewise);
		
		//$scope.rateDatewise = [];
		/*$http.get("/getbookDateWise/"+agentinfo.id).success(function(response){
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
		});*/
	}
	
	$scope.roomWiseRate = function(index){
		$scope.roomIndex = index + 1;
		console.log(index);
		console.log($scope.passengerInfo);
		$scope.rateDatewise = $scope.passengerInfo[index].rateDatedetail;
	}
	
	$scope.bookingPayment = function(bookingId, payment){
		console.log(bookingId);
		console.log(payment);
		$http.get("/getBookingPaymentInfo/"+bookingId+"/"+payment).success(function(response){
			console.log("OK,,OK");
		});
		
	}
	
	
	$scope.natureOfBusiness;
	$scope.editProfile = "show";
	$scope.showEditProfile = function(){
		
		$scope.editProfile = "edit";
		
		$http.get("/getNatureOfBusiness").success(function(response){
			$scope.natureOfBusiness = response;
		});
		
	}
	$scope.cancelEdit = function(){
		$scope.editProfile = "show";
	}
	
	$scope.saveAgentData = function(){
		console.log($scope.agentEdit);
		
			$http.post('/editAgent', $scope.agentEdit).success(function(data){
					console.log(data);
					$scope.editProfile = "show";
					notificationService.success("Agent Update Successfully");
			});
		
	}
	
	
	$scope.showDetails = function(agentinfo){
		console.log(agentinfo);
		$scope.agentinfo = agentinfo;
		ngDialog.open({
			template: '/assets/resources/html/htmltemplet/agent_booking_details.html',
			scope : $scope,
			//controller:'hoteProfileController',
			className: 'ngdialog-theme-default'
		});
	}
	
	$scope.cancelbooking = function(bookingId, nonrefund, agentInfo){
		
		if(nonrefund == "true"){
			ngDialog.close();
			notificationService.error("This booking can not be cancel");
		}else{
			console.log(agentInfo);
			var pandingAmount = 0;
			 var date = new Date();
			 var night = agentInfo.cancellationNightsCharge.split("-");
			 if(Date.parse($filter('date')(agentInfo.latestCancellationDate, "MM-dd-yyyy")) <= Date.parse($filter('date')(date, "MM-dd-yyyy"))){
				
				 if (confirm("On cancelation of this booking apply "+night[1]+" night charges!\n sure to cancel Booking ?")) {
					    
					 	var nightRate = 0;
					 angular.forEach(agentInfo.passengerInfo,function(value,key){
						 angular.forEach(value.rateDatedetail,function(value1,key1){
							 	var i=0;
							 		if(night[1] > key1){
							 			console.log(value1.rate);
							 			nightRate = parseInt(nightRate) + parseInt(value1.rate);
							 		}
						 }); 	
					 });
					 console.log("nightRate");
					 console.log(nightRate);
					 pandingAmount = parseInt(agentInfo.total) - parseInt(nightRate);
					 console.log(pandingAmount);
					 $http.get("/bookingCancelAndCharge/"+bookingId+"/"+nightRate+"/"+pandingAmount).success(function(response){
						 console.log("success");
					 });
					 
					 
				 }
			 }else{
				 console.log("Not");
				 $http.get("/getbookingcancel/"+bookingId).success(function(response){
						
						notificationService.success("Cancel Booking");
						ngDialog.close();
						$http.get("/getagentInfo/"+currentPage+"/"+$scope.fromData+"/"+$scope.toDate+"/"+$scope.status).success(function(response){
							
							console.log(response);
							totalPages = response.totalPages;
							currentPage = response.currentPage;
							$scope.pageNumber = response.currentPage;
							$scope.pageSize = response.totalPages;
							$scope.agentBook = response.results;
							angular.forEach($scope.agentBook,function(value,key){
								value.latestCancellationDate = $filter('date')(value.latestCancellationDate, "dd-MM-yyyy")
							});
							if(totalPages == 0) {
								$scope.pageNumber = 0;
							}
							
						});
					});
			 }
			
		}
	}
	
	$scope.voucher = function(bookingId, roomStatus){
		
		console.log(roomStatus);
		if(roomStatus == "Confirm"){
			$http.get("/getVoucherConfirm/"+bookingId).success(function(response){
				console.log("send Vouchar");
			});
		}else if(roomStatus == "Cancelled"){
			$http.get("/getVoucherCancel/"+bookingId).success(function(response){
				console.log("send Vouchar");
			});
		}else if(roomStatus == "on request"){
			$http.get("/getVoucherOnRequest/"+bookingId).success(function(response){
				console.log("send Vouchar");
			});
		}
		
	}
	
	$scope.roomType;
	$scope.openAmendPopup = function(agentinfo){
		
		$scope.agentinfo = agentinfo;
		
		var pandingAmount = 0;
		 var date = new Date();
		 if(Date.parse($filter('date')(agentinfo.latestCancellationDate, "MM-dd-yyyy")) <= Date.parse($filter('date')(date, "MM-dd-yyyy"))){
					 notificationService.error("you can not amend your booking");
		 }else{
			 $scope.agentinfo.startDate = $filter('date')(date, "dd-MM-yyyy")
			 $http.get("/getRoomType/"+$scope.agentinfo.supplierCode).success(function(response){
					$scope.roomType = response;
				});
				ngDialog.open({
					template: '/assets/resources/html/htmltemplet/agent_amend_booking.html',
					scope : $scope,
					//controller:'hoteProfileController',
					className: 'ngdialog-theme-default'
				});
		 }
		
	}
	
	
	
	$scope.amendButton = 0;
	$scope.dateAmendFunction = function(value1,value2,value3,agentinfo){
		
		if($scope.agentinfo.date != undefined){
			$scope.agentinfo.date = value1;
		}
		if($scope.agentinfo.room != undefined){
			$scope.agentinfo.room = value2;
		}
		if($scope.agentinfo.Passenger != undefined){
			$scope.agentinfo.Passenger = value3;
		}
		
		$scope.agentinfo.checkInAmend = agentinfo.checkIn;
		$scope.agentinfo.checkOutAmend = agentinfo.checkOut;
		
		if($scope.agentinfo.date == "true"){
			$scope.amendButton = 0;
		}
		
		if($scope.agentinfo.room != "true"){
			$scope.agentinfo.roomIdAmend =  agentinfo.roomId;
		}else{
			$scope.amendButton = 0;
		}
		
		if($scope.agentinfo.Passenger == "true"){
			$scope.amendBookingInfo(agentinfo, 1);
			$scope.amendButton = 1;
		}
		
		console.log($scope.agentinfo.date);
		console.log($scope.agentinfo.room);
		console.log($scope.agentinfo.Passenger);
	}
	
	$scope.addRooms = [];
	$scope.roomNotavailableMsg = 0;
	$scope.amendBookingInfo = function(agentinfo, openPopup){
		console.log(openPopup);
		console.log(agentinfo);
		
		
			$scope.addRooms = [];	
			$scope.rateDatedetail = [];
		
		$http.get('/getDatewiseHotelRoom/'+agentinfo.checkInAmend+"/"+agentinfo.checkOutAmend+"/"+agentinfo.nationality+"/"+agentinfo.supplierCode+"/"+agentinfo.roomIdAmend+"/"+agentinfo.id)
		.success(function(response){
			console.log(response);
			$scope.ratedetail = response;
			$scope.ratedetail.agentCurrency = agentinfo.agent[0].currencyShort;
			$scope.ratedetail.currencyExchangeRate = agentinfo.currencyExchangeRate;
			console.log($scope.ratedetail);
			
			if($scope.ratedetail.hotelbyRoom.length !=0){
				$scope.roomNotavailableMsg = 0;
			var total = 0;
			var flag =0;
			
			var stayCountar = 0;
			
			$scope.adultString = "1 Adult";
			
			if($scope.ratedetail.bookingId != null){
				$scope.addRooms = $scope.ratedetail.hotelBookingDetails.passengerInfo;
				$scope.indexCount = $scope.addRooms.length - 1;
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
						if(value1.freeChild != "Free" && value1.freeChild != ""){
							childTotalRate = parseInt(childTotalRate) + parseInt(value1.freeChild);
							console.log("value1.freeChild");
							console.log(childTotalRate);
						}
					});
					console.log("childTotalRate");
					console.log(childTotalRate);
					 $scope.total = $scope.total + childTotalRate;
					 console.log($scope.total);
					value.total = $scope.total;
					value.rateDatedetail = $scope.rateDatedetail;
				});
				$scope.breakfastFunction(); 
				//$scope.rateDatedetail = 
				console.log($scope.addRooms);
				$scope.rateDatedetailRoomWise = $scope.ratedetail.hotelBookingDetails.passengerInfo[0].rateDatedetail;
				$scope.currentRoom = 1;
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
			if(openPopup == 0){
				ngDialog.open({
					template: '/assets/resources/html/htmltemplet/amend_show_Date_wise_info_InHotel.html',
					scope : $scope,
					closeByDocument:false,
					//controller:'hoteProfileController',
					className: 'ngdialog-theme-default'
				});
			}
		}else{
			$scope.roomNotavailableMsg = 1;
		}
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			 notificationService.error("Select Any one for Amend");
		});
		
	}
	
	$scope.indexCount = 0;
	$scope.newRoom = function($event,index){
		
		if($scope.addRooms.length < 5){
			$scope.addRooms.push( {  } );
			$scope.indexCount = $scope.indexCount + 1;
			console.log($scope.indexCount);
			$scope.addRooms[$scope.indexCount].adult = "1 Adult";
			$scope.addRooms[$scope.indexCount].cAllow = "true";
			$scope.addRooms[$scope.indexCount].id = index;
			$scope.countTotal($scope.indexCount);
		}
		
	};
	
	$scope.lessTotal = function(event,index){
		console.log(index);
		
		 $scope.addRooms.splice(index,1);
		 $scope.indexCount = $scope.indexCount - 1;	 
		console.log($scope.addRooms);
		$scope.breakfastFunction(); 
		
	}
	
	$scope.countTotal = function(roomNo){
		console.log(roomNo);
		$scope.countNoOfRoom = roomNo;
		console.log($scope.ratedetail);
		
		console.log($scope.childselected);
		$scope.adultString = "1 Adult";
	//	$scope.fillrateDatedetai($scope.adultString);
		console.log($scope.totalParPerson);
		
		$scope.availableFlag = [];
		var totalchileValue = 0;
		
	console.log(totalchileValue);
		console.log($scope.addRooms);
	
	
	   console.log($scope.totalParPerson);
		$scope.total = $scope.totalParPerson * 1;//(roomNo+1);
		console.log(totalchileValue);
		
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
						
						var arr = $scope.ratedetail.checkIn.split("-");
						var datevalue = (arr[1]+"/"+arr[0]+"/"+arr[2])
						  var d = new Date();
						  var dateOut = new Date(datevalue);
						  
						  var periodDay = value2.allotmentmarket.period.split(" ");
						  dateOut.setDate(dateOut.getDate() -  parseInt(periodDay[0]));
						  
						  console.log(dateOut);
						  console.log(d);
						  if(dateOut <= d){
							  	flag = 1;
							  	angular.forEach($scope.rateDatedetail,function(value3,key3){
									if(value3.fulldate == value.date){
										value3.flag = 1;
									}
								});
						  }else{
						
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
					}
					
				  });
			    });
			   });
		
		$scope.flag = flag;
		
		console.log(flag);
		console.log($scope.rateDatedetail);
		$scope.addRooms[roomNo].rateDatedetail = $scope.rateDatedetail;
		
		
		
		$scope.commanPromotionFunction();
		$scope.batchMarkupFunction();
		

		$scope.addRooms[roomNo].total = $scope.total;
		
		$scope.breakfastFunction();
		
	}
	
	$scope.parChildAge = function(age,index,pIndex){
				
		$scope.total = $scope.totalParPerson;
		var totalcount = $scope.total;
		console.log(totalcount);
		
		
		$scope.rateDatedetail = [];
		console.log($scope.addRooms[pIndex]);
		$scope.fillrateDatedetai($scope.addRooms[pIndex].adult);
		
		$scope.total = $scope.totalParPerson;
		var totalcount = $scope.total;
		console.log(totalcount);
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
		
		//$scope.total = parseInt(totalcount) + parseInt(totalchileValue);
		
	
		console.log($scope.total);
		$scope.commanPromotionFunction();
		$scope.batchMarkupFunction();
		
		
		$scope.total = $scope.total + parseInt(totalchileValue);
		
	
		$scope.addRooms[pIndex].total = $scope.total;
		$scope.breakfastFunction(); 
		
	}
	
	$scope.noOfchildSelect = function(selectedChild,roomCount){

		$scope.childselected = [];
		console.log(selectedChild);
		for(var i=0;i<selectedChild;i++){
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
		
		
		$scope.fillrateDatedetai(adultValue);
/*		angular.forEach($scope.ratedetail.hotelbyDate,function(value,key){
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
		*/
		
		
			angular.forEach($scope.ratedetail.hotelbyDate,function(value,key){
			angular.forEach(value.roomType,function(value1,key1){
				angular.forEach(value1.hotelRoomRateDetail,function(value2,key2){
					if(value2.allotmentmarket.allocation == 3){
						
						var arr = $scope.ratedetail.checkIn.split("-");
						var datevalue = (arr[1]+"/"+arr[0]+"/"+arr[2])
						  var d = new Date();
						  var dateOut = new Date(datevalue);
						  
						  var periodDay = value2.allotmentmarket.period.split(" ");
						  dateOut.setDate(dateOut.getDate() -  parseInt(periodDay[0]));
						  
						  console.log(dateOut);
						  console.log(d);
						  if(dateOut <= d){
							  	flag = 1;
							  	angular.forEach($scope.rateDatedetail,function(value3,key3){
									if(value3.fulldate == value.date){
										value3.flag = 1;
									}
								});
						  }else{
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
					}
					
				  });
			    });
			   });
		
		
		//$scope.total = total;
		//$scope.totalParPerson = total;
			
			$scope.total = $scope.totalParPerson;
	
		//	$scope.total = $scope.totalParPerson * 1;// roomNo;
		
		var arr1 = adultValue.split(" ");
		console.log(arr1[0]);
		
		$scope.adultTotal = arr1[0];
		
		$scope.commanPromotionFunction();
		$scope.batchMarkupFunction();
		$scope.totalValueRate = 0;
		angular.forEach($scope.rateDatedetail,function(value,key){
			$scope.totalValueRate = $scope.totalValueRate + value.rate;
		});
		
		$scope.addRooms[index].totalValueRate = $scope.totalValueRate;
		$scope.addRooms[index].total = $scope.total;
		$scope.addRooms[index].rateDatedetail = $scope.rateDatedetail;
		$scope.addRooms[index].childselected = $scope.childselected;
		$scope.breakfastFunction(); 
		$scope.flag = flag;
		console.log($scope.rateDatedetail);
		console.log($scope.total);
		console.log(flag);
		
		console.log($scope.ratedetail);
		
		
		angular.forEach($scope.ratedetail.hotelbyRoom,function(value,key){
			if(value.maxAdultsWithchild > arr1[0]){
				$scope.addRooms[index].cAllow = "true";
			}else{
				$scope.addRooms[index].cAllow = "false";
				delete $scope.addRooms[index].childselected;
				$scope.addRooms[index].noOfchild = "0";
			}
		});
		
   
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
	

$scope.commanPromotionFunction = function(){
	
	var divDays = 0;
	var remDay = 0;
	var totalStayDay = 0;
	$scope.totalStayDays = 0;
	var typeOFFreeStey = 0;
	var daysLess = 0;
	$scope.total = 0;
	$scope.totalNightCal = 0;
	$scope.totalBirdCal = 0;
	$scope.totalFlatCal = 0;
	$scope.applyPromo = 0;
	
    for(var j=0;j<$scope.rateDatedetail.length;j++){
    	$scope.total = $scope.total  + $scope.rateDatedetail[j].rate;
    }
   
			
	
	if($scope.ratedetail.hotelbyRoom[0].applyPromotion == 1){	
		
		angular.forEach($scope.ratedetail.hotelbyRoom[0].specials, function(value, index){
			  if(value.promotionType == "nightPromotion"){
				  $scope.totalNightCal = $scope.total;
		typeOFFreeStey = value.markets[0].typeOfStay;
		if(value.markets[0].multiple == true){
			divDays = parseInt($scope.ratedetail.datediff / value.markets[0].stayDays);
			remDay = $scope.ratedetail.datediff % value.markets[0].stayDays;
			totalStayDay = (divDays * value.markets[0].payDays) + remDay;
			daysLess = $scope.ratedetail.datediff - totalStayDay;
			$scope.totalStayDays = totalStayDay;
		
		}
		if(value.markets[0].multiple == false){
			divDays = parseInt(value.markets[0].stayDays) - parseInt(value.markets[0].payDays);
			
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
		
		
		 if(typeOFFreeStey ==  "First Night"){
			
			 if(value.markets[0].multiple == false){
				 for(i=0; i<divDays; i++){
					 firstNoTotal = firstNoTotal + a[i];
				
					}
			 }else{
			 for(i=0; i<daysLess; i++){
				 firstNoTotal = firstNoTotal + a[i];
				
				}
		     }
			 $scope.totalNightCal = $scope.totalNightCal - firstNoTotal;
		  }
		  if(typeOFFreeStey ==  "Standard Policy"){
			  var stayCountar = 0;
			  if(value.markets[0].multiple == false){
					 for(i=parseInt(value.markets[0].payDays); i<parseInt(value.markets[0].payDays) + parseInt(divDays); i++){
						console.log(i);
						 standardTotal = standardTotal + a[i];
						
						 console.log(standardTotal);
						}
				 }else{
					 for(i=0; i<a.length; i++){
						 if(stayCountar < value.markets[0].payDays){
							 stayCountar = stayCountar + 1;
							 console.log(stayCountar);
						 }else if(stayCountar >= value.markets[0].payDays){
							 standardTotal = standardTotal + a[i];	
							
							 console.log(a[i]);
							 console.log(standardTotal);
							 stayCountar = stayCountar + 1;
							 if(stayCountar == value.markets[0].stayDays){
								 stayCountar = 0; 
							 }
						 }
						 console.log(stayCountar);
					 }
				 }
			  console.log(standardTotal);
			  console.log($scope.totalNightCal);
			  $scope.totalNightCal = $scope.totalNightCal - standardTotal;
			
		   }
		
		if(typeOFFreeStey ==  "Cheapest night"){
			
			a.sort(function(a, b){return a-b});

			 if(value.markets[0].multiple == false){
				 for(i=0; i<divDays; i++){
						cheapestTotal = cheapestTotal + a[i];
						
					}
			 }else{
				 for(i=0; i<daysLess; i++){
						cheapestTotal = cheapestTotal + a[i];
						
					}
			 }
			
			
			console.log(cheapestTotal);
			$scope.totalNightCal = $scope.totalNightCal - cheapestTotal;
			
		}
		if(typeOFFreeStey ==  "Most expensive night/s"){
			
						
			a.sort(function(a, b){return b-a});
			
			if(value.markets[0].multiple == false){
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
			
			$scope.totalNightCal = $scope.totalNightCal - ExpensivesTotal;
		}
		}
      });
		
	}
	
	if($scope.ratedetail.hotelbyRoom[0].applyFlatPromotion == 1){
		
		angular.forEach($scope.ratedetail.hotelbyRoom[0].specials, function(value, index){
		  if(value.promotionType == "flatPromotion"){
			   for(i=0;i<$scope.rateDatedetail.length;i++){
					 $scope.totalFlatCal = $scope.totalFlatCal + value.markets[0].flatRate;
				}
		  }
		});
		
	}
	
	if($scope.ratedetail.hotelbyRoom[0].applybirdPromotion == 1){
		
		angular.forEach($scope.ratedetail.hotelbyRoom[0].specials, function(value, index){
		  if(value.promotionType == "birdPromotion"){
			  $scope.totalBirdCal = $scope.total;
			  if(value.markets[0].earlyBirdRateCalculat == "flatRate"){
				  $scope.totalBirdCal = 0;
				  if($scope.ratedetail.hotelbyRoom[0].applyFlatPromotion == 1){
					  angular.forEach($scope.ratedetail.hotelbyRoom[0].specials, function(value1, index1){
						  if(value1.promotionType == "flatPromotion"){
							  for(i=0;i<$scope.rateDatedetail.length;i++){
								  $scope.totalBirdCal = $scope.totalBirdCal + value1.markets[0].flatRate;
								}
							  var birdDis =	$scope.totalBirdCal / 100 * value.markets[0].earlyBirdDisount;
							  $scope.totalBirdCal = $scope.totalBirdCal - birdDis;
						  }
					  });  
					 
				  }
			  }else  if(value.markets[0].earlyBirdRateCalculat == "contractRate"){
				  var birdDis =	$scope.totalBirdCal / 100 * value.markets[0].earlyBirdDisount;
				  $scope.totalBirdCal = $scope.totalBirdCal - birdDis;
			  }
			  
		  }
		});
		
	}
	
	console.log("*****Rate ************");
	console.log($scope.totalFlatCal);
	console.log($scope.totalNightCal);
	console.log($scope.totalBirdCal);
	if($scope.totalFlatCal == 0 && $scope.totalNightCal == 0 && $scope.totalBirdCal == 0){
		
	}else{
		if($scope.totalFlatCal == 0){
			$scope.totalFlatCal = 100000000;
		}
		if($scope.totalNightCal == 0){
			$scope.totalNightCal = 100000000;
		}
		if($scope.totalBirdCal == 0){
			$scope.totalBirdCal = 100000000;
		}
		
		if($scope.totalFlatCal > $scope.totalNightCal &&  $scope.totalBirdCal > $scope.totalNightCal){
			$scope.applyPromo = "nightPromotion";
			$scope.total = $scope.totalNightCal;
		}else if($scope.totalNightCal > $scope.totalFlatCal && $scope.totalBirdCal > $scope.totalFlatCal){
			$scope.applyPromo = "flatPromotion";
			angular.forEach($scope.ratedetail.hotelbyRoom[0].specials, function(value, index){
			  if(value.promotionType == "flatPromotion"){
				   for(i=0;i<$scope.rateDatedetail.length;i++){
						 $scope.rateDatedetail[i].rate = value.markets[0].flatRate;
					}
			  }
			});
			$scope.total = $scope.totalFlatCal;
			
		}else if($scope.totalFlatCal > $scope.totalBirdCal && $scope.totalNightCal > $scope.totalBirdCal){
			console.log("*****Rate 123");
			$scope.applyPromo = "birdPromotion";
			angular.forEach($scope.ratedetail.hotelbyRoom[0].specials, function(value, index){
				  if(value.promotionType == "birdPromotion"){
					  if(value.markets[0].earlyBirdRateCalculat == "flatRate"){
						   if($scope.ratedetail.hotelbyRoom[0].applyFlatPromotion == 1){
							   angular.forEach($scope.ratedetail.hotelbyRoom[0].specials, function(value1, index1){
									  if(value1.promotionType == "flatPromotion"){
										  for(i=0;i<$scope.rateDatedetail.length;i++){
											  console.log($scope.rateDatedetail[i].rate);
									 			$scope.rateDatedetail[i].rate = value1.markets[0].flatRate - value1.markets[0].flatRate / 100 * value.markets[0].earlyBirdDisount;
											}
									  }
								  });
						  }
					  }else  if(value.markets[0].earlyBirdRateCalculat == "contractRate"){
						   angular.forEach($scope.rateDatedetail, function(value1, index1){
							   value1.rate = value1.rate - value1.rate / 100 * value.markets[0].earlyBirdDisount;
						   });
					  }					 
				  }
				});
			$scope.total =  $scope.totalBirdCal;
		
		}
	}
	
	console.log($scope.total);
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


$scope.breakfastFunction = function(){
	$scope.finalTotal = 0;
	$scope.finalTotalOther = 0;
	var adultNo = 0;
	var childNo = 0;
		
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
		$scope.finalTotalOther = $scope.finalTotalOther + parseInt(value.totalValueRate);
		
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
//		$scope.finalTotalDetails.push = ({adult:"",noOfchild:"",total:""});
		$scope.finalTotalDetails.push({});
		$scope.finalTotalDetails[key].adult = value.adult;
		$scope.finalTotalDetails[key].noOfchild = value.noOfchild;
		$scope.finalTotalDetails[key].total = value.total;
	});
	
	
	console.log($scope.finalTotalDetails);
	
}

$scope.showDateWiseDetails = function(index){
	console.log("Hiii..Bye");
	console.log(index);
	$scope.rateDatedetailRoomWise = [];
	console.log($scope.addRooms[index]);
	$scope.currentRoom = index + 1;
	$scope.rateDatedetailRoomWise = $scope.addRooms[index].rateDatedetail;
	console.log($scope.rateDatedetailRoomWise);
}
	
});

travelBusiness.controller('HomePageController', function ($scope,$http,$filter,ngDialog,$cookieStore,$cookies) {
	
	
	var session = $cookies['PLAY_SESSION'];
	
	if(session != undefined){
		$scope.sessionValue = 1
	}else{
		$scope.sessionValue = 0;
	}
	
	
	$scope.searchby = {
			checkIn:"dd/mm/yyyy"
	}
	$scope.$watch('searchby.checkIn',function(newValue){
		console.log(newValue);
		var arr = newValue.split("-");
		var datevalue = (arr[1]+"/"+arr[0]+"/"+arr[2])
		  var dateOut = new Date(datevalue);
		  dateOut.setDate(dateOut.getDate() + 1);
		  console.log($filter('date')(dateOut, "dd-MM-yyyy"));
		
		var arr1 = $filter('date')(dateOut, "dd-MM-yyyy").split("-");
		jQuery("#checkOutDatePicker").datepicker( "option", "minDate", new Date(arr1[2], arr1[1] -1, arr1[0]) );
	});
	
	
	$scope.loginSuccess = 0;
	$scope.errorMsg = false;
	$scope.loginAgent = function(){
		
		ngDialog.open({
			template: '/assets/resources/html/htmltemplet/loginAgent.html',
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
			template: '/assets/resources/html/htmltemplet/forgotPassword.html',
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
				$scope.sessionValue = 1;
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

	$scope.onCountryChange = function(countryCode) {
				console.log("for city");
		$http.get('/searchCities/'+countryCode)
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



travelBusiness.controller('PageController', function ($scope,$http,$filter,ngDialog,$cookieStore,$cookies) {
	
	
	var session = $cookies['PLAY_SESSION'];
	
	if(session != undefined){
		$scope.sessionValue = 1
	}else{
		$scope.sessionValue = 0;
	}
	
	$scope.searchby = {};
	$scope.AgentId = $cookieStore.get('AgentId');
	$scope.AgentCompany = $cookieStore.get('AgentCompany');
	
	console.log($scope.AgentCompany);
	
	$scope.searchby = {
			checkIn:"dd/mm/yyyy"
	}
	$scope.$watch('searchby.checkIn',function(newValue){
		console.log(newValue);
		var arr = newValue.split("-");
		var datevalue = (arr[1]+"/"+arr[0]+"/"+arr[2])
		  var dateOut = new Date(datevalue);
		  dateOut.setDate(dateOut.getDate() + 1);
		  console.log($filter('date')(dateOut, "dd-MM-yyyy"));
		
		var arr1 = $filter('date')(dateOut, "dd-MM-yyyy").split("-");
		jQuery("#checkOutDatePicker").datepicker( "option", "minDate", new Date(arr1[2], arr1[1] -1, arr1[0]) );
	});	
	
	$scope.init = function(hotelAllData){
		
		var currArr = hotelAllData.agentInfo.currency.split(" - ");
		
		angular.forEach(hotelAllData.hotellist, function(obj, index){ ///hotel_profile
						$scope.img = "/searchHotelInfo/getHotelImagePath/"+hotelAllData.hotellist[index].supplierCode+"/"+index;
						console.log($scope.img);
				hotelAllData.hotellist[index].imgPaths = $scope.img;
				hotelAllData.hotellist[index].agentCurrency = currArr[0];
				
				if(obj.currencyShort == "SGD"){
					hotelAllData.hotellist[index].currencyExchangeRate = hotelAllData.currencyExchangeRate.curr_SGD;
				}else if(obj.currencyShort == "INR"){
					hotelAllData.hotellist[index].currencyExchangeRate = hotelAllData.currencyExchangeRate.curr_INR;
				}else if(obj.currencyShort == "THB"){
					hotelAllData.hotellist[index].currencyExchangeRate = hotelAllData.currencyExchangeRate.curr_THB;
				}else if(obj.currencyShort == "MYR"){
					hotelAllData.hotellist[index].currencyExchangeRate = hotelAllData.currencyExchangeRate.curr_MYR;
				}
						
		});
		
		angular.forEach(hotelAllData.hotellist, function(value, key){
		angular.forEach(value.hotelbyRoom, function(obj, index){ 
			angular.forEach(obj.hotelRoomRateDetail[0].rateDetailsNormal, function(obj1, index1){ 
				if(value.batchMarkup.selected == 0){
						obj1.rateAvg = obj1.rateAvg + value.batchMarkup.flat;
				}else if(value.batchMarkup.selected == 1){
					   var markupPer =	obj1.rateAvg / 100 * value.batchMarkup.percent;
					   obj1.rateAvg = obj1.rateAvg + markupPer;
				}
			});
		});
			if(value.batchMarkup.selected == 0){
				value.minRate = value.minRate + value.batchMarkup.flat;
			}else if(value.batchMarkup.selected == 1){
				var markupPer =	value.minRate / 100 * value.batchMarkup.percent;
				value.minRate = value.minRate + markupPer;
			}
		
		});
		
		console.log(hotelAllData.hotellist);
		$scope.hotelAllData = hotelAllData;
		console.log($scope.hotelAllData);
		
		$scope.hotellistInfo = hotelAllData.hotellist;
		
		
		if($scope.hotellistInfo.length > 0){
			$scope.searchby.countryName = $scope.hotellistInfo[0].countryName;
			$scope.searchby.countryCode = $scope.hotellistInfo[0].countryCode;
			
			$scope.searchby.cityName =$scope.hotellistInfo[0].cityName;
			$scope.searchby.cityCode =$scope.hotellistInfo[0].cityCode;
			$scope.searchby.checkIn  = $scope.hotellistInfo[0].checkIn;
			$scope.searchby.checkOut = $scope.hotellistInfo[0].checkOut;
			$scope.searchby.nationalityName = $scope.hotellistInfo[0].nationalityName;
			$scope.searchby.nationalityCode = $scope.hotellistInfo[0].nationality;
		}
		
		
		$scope.onCountryChange($scope.searchby.countryCode);
		
		console.log($scope.searchby);
	}
	
$http.get("/searchCountries").success(function(response) {
		
		$scope.searchCountries = response;
	}); 

	$scope.onCountryChange = function(countryCode) {
				console.log("for city");
		$http.get('/searchCities/'+countryCode)
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
			$scope.findHotelData.currencyExchangeRate = $scope.hotelAllData.hotellist[0].currencyExchangeRate;
			$scope.findHotelData.agentCurrency = $scope.hotelAllData.hotellist[0].agentCurrency;
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
				
				angular.forEach(data.hotellist, function(obj, index){ 
					$scope.img = "/searchHotelInfo/getHotelImagePath/"+data.hotellist[index].supplierCode+"/"+index;
					obj.imgPaths = $scope.img;
								
				});
				
				angular.forEach(data.hotellist, function(value, key){
					angular.forEach(value.hotelbyRoom, function(obj, index){ 
						angular.forEach(obj.hotelRoomRateDetail[0].rateDetailsNormal, function(obj1, index1){ 
							if(value.batchMarkup.selected == 0){
									obj1.rateAvg = obj1.rateAvg + value.batchMarkup.flat;
							}else if(value.batchMarkup.selected == 1){
								   var markupPer =	obj1.rateAvg / 100 * value.batchMarkup.percent;
								   obj1.rateAvg = obj1.rateAvg + markupPer;
							}
						});
					});
						if(value.batchMarkup.selected == 0){
							value.minRate = value.minRate + value.batchMarkup.flat;
						}else if(value.batchMarkup.selected == 1){
							var markupPer =	value.minRate / 100 * value.batchMarkup.percent;
							value.minRate = value.minRate + markupPer;
						}
					
					});
				$scope.hotellistInfo = data.hotellist;
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
		//	$scope.findHotelData.currencyExchangeRate = $scope.hotelAllData.hotellist[0].currencyExchangeRate;
			$scope.findHotelData.agentCurrency = $scope.hotelAllData.hotellist[0].agentCurrency;
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
			
				angular.forEach(data.hotellist, function(obj, index){ 
					$scope.img = "/searchHotelInfo/getHotelImagePath/"+data.hotellist[index].supplierCode+"/"+index;
					obj.imgPaths = $scope.img;
								
				});
				
				angular.forEach(data.hotellist, function(value, key){
					angular.forEach(value.hotelbyRoom, function(obj, index){ 
						angular.forEach(obj.hotelRoomRateDetail[0].rateDetailsNormal, function(obj1, index1){ 
							if(value.batchMarkup.selected == 0){
									obj1.rateAvg = obj1.rateAvg + value.batchMarkup.flat;
							}else if(value.batchMarkup.selected == 1){
								   var markupPer =	obj1.rateAvg / 100 * value.batchMarkup.percent;
								   obj1.rateAvg = obj1.rateAvg + markupPer;
							}
						});
					});
						if(value.batchMarkup.selected == 0){
							value.minRate = value.minRate + value.batchMarkup.flat;
						}else if(value.batchMarkup.selected == 1){
							var markupPer =	value.minRate / 100 * value.batchMarkup.percent;
							value.minRate = value.minRate + markupPer;
						}
					
					});
				$scope.hotellistInfo = data.hotellist;
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



travelBusiness.controller('hotelDetailsController', function ($scope,$http,$filter,ngDialog,$cookieStore,$cookies) {
	
	
	var session = $cookies['PLAY_SESSION'];
	
	if(session != undefined){
		$scope.sessionValue = 1
	}else{
		$scope.sessionValue = 0;
	}
	
	$scope.AgentId = $cookieStore.get('AgentId');
	$scope.AgentCompany = $cookieStore.get('AgentCompany');
	$scope.imgArray = [];
	$scope.searchby = {};
	$scope.totalStayDays = 0;
	$scope.noOfRoomShow = 1;
	
	$scope.searchby = {
			checkIn:"dd/mm/yyyy"
	}
	$scope.$watch('searchby.checkIn',function(newValue){
		console.log(newValue);
		var arr = newValue.split("-");
		var datevalue = (arr[1]+"/"+arr[0]+"/"+arr[2])
		 var dateOut = new Date(datevalue);
		  dateOut.setDate(dateOut.getDate() + 1);
		  console.log($filter('date')(dateOut, "dd-MM-yyyy"));
		
		var arr1 = $filter('date')(dateOut, "dd-MM-yyyy").split("-");
		jQuery("#checkOutDatePicker").datepicker( "option", "minDate", new Date(arr1[2], arr1[1] -1, arr1[0]) );
	});	
	
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
				obj1.days;
				
				console.log(hotel.checkIn);
				var arr = hotel.checkIn.split("-");
				var datevalue = (arr[1]+"/"+arr[0]+"/"+arr[2])
				  var d = new Date();
				  var dateOut = new Date(datevalue);
				  dateOut.setDate(dateOut.getDate() - 3);
				  if(dateOut < d){
					  console.log($filter('date')(d, "dd-MMM-yyyy"));
					  obj1.cancellationDate = $filter('date')(d, "dd-MMM-yyyy");
				  }else{
					  console.log($filter('date')(dateOut, "dd-MMM-yyyy"));
					  obj1.cancellationDate = $filter('date')(dateOut, "dd-MMM-yyyy");
				  }
				  
			});
		});
		
		angular.forEach(hotel.hotelbyRoom, function(obj, index){ 
			angular.forEach(obj.hotelRoomRateDetail[0].rateDetailsNormal, function(obj1, index1){ 
				if($scope.hotel.batchMarkup.selected == 0){
						obj1.rateAvg = obj1.rateAvg + $scope.hotel.batchMarkup.flat;
				}else if($scope.hotel.batchMarkup.selected == 1){
					   var markupPer =	obj1.rateAvg / 100 * $scope.hotel.batchMarkup.percent;
					   obj1.rateAvg = obj1.rateAvg + markupPer;
				}
			});
		});
		
		if($scope.hotel.batchMarkup.selected == 0){
			$scope.hotel.minRate = hotel.minRate + $scope.hotel.batchMarkup.flat;
		}else if($scope.hotel.batchMarkup.selected == 1){
			var markupPer =	$scope.hotel.minRate / 100 * $scope.hotel.batchMarkup.percent;
			$scope.hotel.minRate = $scope.hotel.minRate + markupPer;
		}
		
				
		console.log($scope.hotel);
		
		
		$scope.searchby.countryName = $scope.hotel.countryName;
		$scope.searchby.countryCode = $scope.hotel.countryCode;
		
		$scope.searchby.cityName =$scope.hotel.cityName;
		$scope.searchby.cityCode =$scope.hotel.cityCode;
		$scope.searchby.checkIn  = $scope.hotel.checkIn;
		$scope.searchby.checkOut = $scope.hotel.checkOut;
		$scope.searchby.nationalityName = $scope.hotel.nationalityName;
		$scope.searchby.nationalityCode = $scope.hotel.nationality;
		
		$scope.onCountryChange($scope.searchby.countryCode);
		
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
	
	$scope.closePopup = function(){
		$scope.addRooms = [];
		/*$scope.addRooms.push({});
		$scope.addRooms[0].adult = "1 Adult";
		$scope.addRooms[0].cAllow = "true";
		$scope.addRooms[0].id = 0;*/
		console.log("HiiiOOooooo");
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
						var arr = $scope.ratedetail.checkIn.split("-");
						var datevalue = (arr[1]+"/"+arr[0]+"/"+arr[2])
						  var d = new Date();
						  var dateOut = new Date(datevalue);
						  
						  var periodDay = value2.allotmentmarket.period.split(" ");
						  dateOut.setDate(dateOut.getDate() -  parseInt(periodDay[0]));
						  
						  console.log(dateOut);
						  console.log(d);
						  if(dateOut <= d){
							  	flag = 1;
							  	angular.forEach($scope.rateDatedetail,function(value3,key3){
									if(value3.fulldate == value.date){
										value3.flag = 1;
									}
								});
						  }else{
						
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
					}
					
				  });
			    });
			   });
		
		
		$scope.total = total;
		$scope.totalParPerson = total;
	
		//if(roomNo != undefined){
			//$scope.total = $scope.totalParPerson * 1;// roomNo;
		//}
	
		
		var arr1 = adultValue.split(" ");
		console.log(arr1[0]);
		
		$scope.adultTotal = arr1[0];
		//$scope.childTotal = 0;
		
		
		$scope.commanPromotionFunction();
		$scope.batchMarkupFunction();
		$scope.totalValueRate = 0;
		angular.forEach($scope.rateDatedetail,function(value,key){
			$scope.totalValueRate = $scope.totalValueRate + value.rate;
		});
		
		$scope.addRooms[index].totalValueRate = $scope.totalValueRate;
		$scope.addRooms[index].total = $scope.total;
		$scope.addRooms[index].rateDatedetail = $scope.rateDatedetail;
		$scope.addRooms[index].childselected = $scope.childselected;
		$scope.breakfastFunction(); 
		$scope.flag = flag;
		console.log($scope.rateDatedetail);
		console.log($scope.total);
		console.log(flag);
		
				
		console.log($scope.ratedetail);
		
		
		angular.forEach($scope.ratedetail.hotelbyRoom,function(value,key){
			if(value.maxAdultsWithchild > arr1[0]){
				$scope.addRooms[index].cAllow = "true";
				//$scope.cAllow = "true";
				//$scope.childnotallowMsg = "false";
			}else{
				$scope.addRooms[index].cAllow = "false";
				delete $scope.addRooms[index].childselected;
				$scope.addRooms[index].noOfchild = "0";
				//$scope.cAllow = "false";
				//$scope.childnotallowMsg = "true";
			}
		});
		
     /* }else{
    		$scope.addRooms[index].cAllow = "false";
    	  //$scope.cAllow = "false";
    	  //$scope.childnotallowMsg = "false";
      }*/
	}
	

	
	$scope.hidechild = function(roomCount){
		$scope.cAllow = "false";
		$scope.childnotallowMsg = "false";
		
		$scope.total = $scope.totalParPerson * roomCount;
	}
	
	$scope.rateDatedetailRoomWise = []; 
	$scope.childcount = [];
	
	$scope.showRatePromotionwise = function(roomInfo,typePromo){
		$scope.typePromo = typePromo;
		console.log($scope.typePromo);
		console.log(roomInfo);
		ngDialog.close();
		$scope.dateWiseInfo(roomInfo.roomId,$scope.typePromo);
	}
	
	$scope.dateWiseInfo = function(roomid,typePromo){
		$scope.typePromo = typePromo;
		$scope.addRooms.push({});
		$scope.addRooms[0].adult = "1 Adult";
		$scope.addRooms[0].cAllow = "true";
		$scope.addRooms[0].id = 0;
		console.log($scope.hotel);
		
			$scope.rateDatedetail = [];
		
		$http.get('/getDatewiseHotelRoom/'+$scope.hotel.checkIn+"/"+$scope.hotel.checkOut+"/"+$scope.hotel.nationality+"/"+$scope.hotel.supplierCode+"/"+roomid+"/"+$scope.hotel.bookingId)
		.success(function(response){
			console.log(response);
			response.agentCurrency = $scope.hotel.agentCurrency;
			response.currencyExchangeRate = $scope.hotel.currencyExchangeRate;
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
					$scope.totalValueRate = 0;
					angular.forEach(value.rateDatedetail,function(value1,key1){
						$scope.totalValueRate = $scope.totalValueRate + value1.rate;
					});
					value.totalValueRate = $scope.totalValueRate;
					
					$scope.addRooms[index].totalValueRate = $scope.totalValueRate;
				});
				$scope.breakfastFunction(); 
				//$scope.rateDatedetail = 
				console.log($scope.addRooms);
				$scope.rateDatedetailRoomWise = $scope.ratedetail.hotelBookingDetails.passengerInfo[0].rateDatedetail;
				$scope.currentRoom = 1;
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
							//	console.log("------------value3.rateValue--------------");
								//console.log(value3.rateValue);
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
							
									var arr = $scope.ratedetail.checkIn.split("-");
							var datevalue = (arr[1]+"/"+arr[0]+"/"+arr[2])
							  var d = new Date();
							  var dateOut = new Date(datevalue);
							  
							  var periodDay = value2.allotmentmarket.period.split(" ");
							  dateOut.setDate(dateOut.getDate() -  parseInt(periodDay[0]));
							  
							  console.log(dateOut);
							  console.log(d);
							  if(dateOut <= d){
								  	flag = 1;
								  	angular.forEach($scope.rateDatedetail,function(value3,key3){
										if(value3.fulldate == value.date){
											value3.flag = 1;
										}
									});
							  }else{
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
													
						}
						
					  });
				    });
				   });
			
			$scope.total = total;
			$scope.totalParPerson = total;
			$scope.adultTotal = 1;
			$scope.childTotal = 0;
			
			
		
			console.log($scope.rateDatedetail);
			$scope.commanPromotionFunction();
			$scope.batchMarkupFunction();
			$scope.totalValueRate = 0;
			angular.forEach($scope.rateDatedetail,function(value,key){
				$scope.totalValueRate = $scope.totalValueRate + value.rate;
			});
			
			$scope.addRooms[0].totalValueRate = $scope.totalValueRate;
			$scope.addRooms[0].total = $scope.total;
			$scope.addRooms[0].rateDatedetail = $scope.rateDatedetail; 
			$scope.rateDatedetailRoomWise = $scope.rateDatedetail;
			$scope.currentRoom = 1;
			
			$scope.breakfastFunction(); 
			
			//$scope.flag = flag;
			
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
			ngDialog.open({
				template: '/assets/resources/html/show_Date_wise_info_InHotel.html',
				scope : $scope,
				closeByDocument:false,
				//controller:'hoteProfileController',
				className: 'ngdialog-theme-default'
			});
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
		$scope.selectextraBed = function(){
			console.log("------Select---------");
		}
	}
	
	$scope.caluAllotRoom = function(){
		
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
			console.log("909090");
		  console.log($scope.total);
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
		$scope.total = 0;
		$scope.totalNightCal = 0;
		$scope.totalBirdCal = 0;
		$scope.totalFlatCal = 0;
		$scope.applyPromo = 0;
	    for(var j=0;j<$scope.rateDatedetail.length;j++){
	    	$scope.total = $scope.total  + $scope.rateDatedetail[j].rate;
	    }
	   
		
	    console.log("88888888888888console.log($scope.ratedetail);8888888888888888888");
	    console.log($scope.ratedetail);
	    console.log($scope.typePromo);
		
		if($scope.ratedetail.hotelbyRoom[0].applyPromotion == 1){	
			
			angular.forEach($scope.ratedetail.hotelbyRoom[0].specials, function(value, index){
				  if(value.promotionType == "nightPromotion"){
					  $scope.totalNightCal = $scope.total;
			typeOFFreeStey = value.markets[0].typeOfStay;
			if(value.markets[0].multiple == true){
				divDays = parseInt($scope.ratedetail.datediff / value.markets[0].stayDays);
				remDay = $scope.ratedetail.datediff % value.markets[0].stayDays;
				totalStayDay = (divDays * value.markets[0].payDays) + remDay;
				daysLess = $scope.ratedetail.datediff - totalStayDay;
				$scope.totalStayDays = totalStayDay;
			
			}
			if(value.markets[0].multiple == false){
				divDays = parseInt(value.markets[0].stayDays) - parseInt(value.markets[0].payDays);
				
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
			
			
			 if(typeOFFreeStey ==  "First Night"){
				
				 if(value.markets[0].multiple == false){
					 for(i=0; i<divDays; i++){
						 firstNoTotal = firstNoTotal + a[i];
					
						}
				 }else{
				 for(i=0; i<daysLess; i++){
					 firstNoTotal = firstNoTotal + a[i];
					
					}
			     }
				 $scope.totalNightCal = $scope.totalNightCal - firstNoTotal;
			  }
			  if(typeOFFreeStey ==  "Standard Policy"){
				  var stayCountar = 0;
				  if(value.markets[0].multiple == false){
						 for(i=parseInt(value.markets[0].payDays); i<parseInt(value.markets[0].payDays) + parseInt(divDays); i++){
							console.log(i);
							 standardTotal = standardTotal + a[i];
							
							 console.log(standardTotal);
							}
					 }else{
						 for(i=0; i<a.length; i++){
							 if(stayCountar < value.markets[0].payDays){
								 stayCountar = stayCountar + 1;
								 console.log(stayCountar);
							 }else if(stayCountar >= value.markets[0].payDays){
								 standardTotal = standardTotal + a[i];	
								
								 console.log(a[i]);
								 console.log(standardTotal);
								 stayCountar = stayCountar + 1;
								 if(stayCountar == value.markets[0].stayDays){
									 stayCountar = 0; 
								 }
							 }
							 console.log(stayCountar);
						 }
					 }
				  console.log(standardTotal);
				  console.log($scope.totalNightCal);
				  $scope.totalNightCal = $scope.totalNightCal - standardTotal;
				
			   }
			
			if(typeOFFreeStey ==  "Cheapest night"){
				
				a.sort(function(a, b){return a-b});

				 if(value.markets[0].multiple == false){
					 for(i=0; i<divDays; i++){
							cheapestTotal = cheapestTotal + a[i];
							
						}
				 }else{
					 for(i=0; i<daysLess; i++){
							cheapestTotal = cheapestTotal + a[i];
							
						}
				 }
				
				
				console.log(cheapestTotal);
				$scope.totalNightCal = $scope.totalNightCal - cheapestTotal;
				
			}
			if(typeOFFreeStey ==  "Most expensive night/s"){
				
							
				a.sort(function(a, b){return b-a});
				
				if(value.markets[0].multiple == false){
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
				
				$scope.totalNightCal = $scope.totalNightCal - ExpensivesTotal;
			}
			}
	      });
			
		}
		
		if($scope.ratedetail.hotelbyRoom[0].applyFlatPromotion == 1){
			
			angular.forEach($scope.ratedetail.hotelbyRoom[0].specials, function(value, index){
			  if(value.promotionType == "flatPromotion"){
				   for(i=0;i<$scope.rateDatedetail.length;i++){
						 $scope.totalFlatCal = $scope.totalFlatCal + value.markets[0].flatRate;
					}
			  }
			});
			
		}
		
		if($scope.ratedetail.hotelbyRoom[0].applybirdPromotion == 1){
			
			angular.forEach($scope.ratedetail.hotelbyRoom[0].specials, function(value, index){
			  if(value.promotionType == "birdPromotion"){
				  $scope.totalBirdCal = $scope.total;
				  if(value.markets[0].earlyBirdRateCalculat == "flatRate"){
					  $scope.totalBirdCal = 0;
					  if($scope.ratedetail.hotelbyRoom[0].applyFlatPromotion == 1){
						  angular.forEach($scope.ratedetail.hotelbyRoom[0].specials, function(value1, index1){
							  if(value1.promotionType == "flatPromotion"){
								  for(i=0;i<$scope.rateDatedetail.length;i++){
									  $scope.totalBirdCal = $scope.totalBirdCal + value1.markets[0].flatRate;
									}
								  var birdDis =	$scope.totalBirdCal / 100 * value.markets[0].earlyBirdDisount;
								  $scope.totalBirdCal = $scope.totalBirdCal - birdDis;
							  }
						  });  
						 
					  }
				  }else  if(value.markets[0].earlyBirdRateCalculat == "contractRate"){
					  var birdDis =	$scope.totalBirdCal / 100 * value.markets[0].earlyBirdDisount;
					  $scope.totalBirdCal = $scope.totalBirdCal - birdDis;
				  }
				  
			  }
			});
			
		}
		
		console.log("*****Rate ************");
		console.log($scope.totalFlatCal);
		console.log($scope.totalNightCal);
		console.log($scope.totalBirdCal);
		
		if($scope.typePromo == "maxRate"){
			if($scope.totalFlatCal == 0 && $scope.totalNightCal == 0 && $scope.totalBirdCal == 0){
				
			}else{
				if($scope.totalFlatCal == 0){
					$scope.totalFlatCal = 100000000;
				}
				if($scope.totalNightCal == 0){
					$scope.totalNightCal = 100000000;
				}
				if($scope.totalBirdCal == 0){
					$scope.totalBirdCal = 100000000;
				}
				
				if($scope.totalFlatCal > $scope.totalNightCal &&  $scope.totalBirdCal > $scope.totalNightCal){
					$scope.applyPromo = "nightPromotion";
					$scope.total = $scope.totalNightCal;
				}else if($scope.totalNightCal > $scope.totalFlatCal && $scope.totalBirdCal > $scope.totalFlatCal){
					$scope.applyPromo = "flatPromotion";
					angular.forEach($scope.ratedetail.hotelbyRoom[0].specials, function(value, index){
					  if(value.promotionType == "flatPromotion"){
						   for(i=0;i<$scope.rateDatedetail.length;i++){
								 $scope.rateDatedetail[i].rate = value.markets[0].flatRate;
							}
					  }
					});
					$scope.total = $scope.totalFlatCal;
					
				}else if($scope.totalFlatCal > $scope.totalBirdCal && $scope.totalNightCal > $scope.totalBirdCal){
					console.log("*****Rate 123");
					$scope.applyPromo = "birdPromotion";
					angular.forEach($scope.ratedetail.hotelbyRoom[0].specials, function(value, index){
						  if(value.promotionType == "birdPromotion"){
							  if(value.markets[0].earlyBirdRateCalculat == "flatRate"){
								   if($scope.ratedetail.hotelbyRoom[0].applyFlatPromotion == 1){
									   angular.forEach($scope.ratedetail.hotelbyRoom[0].specials, function(value1, index1){
											  if(value1.promotionType == "flatPromotion"){
												  for(i=0;i<$scope.rateDatedetail.length;i++){
													  console.log($scope.rateDatedetail[i].rate);
											 			$scope.rateDatedetail[i].rate = value1.markets[0].flatRate - value1.markets[0].flatRate / 100 * value.markets[0].earlyBirdDisount;
													}
											  }
										  });
								  }
							  }else  if(value.markets[0].earlyBirdRateCalculat == "contractRate"){
								   angular.forEach($scope.rateDatedetail, function(value1, index1){
									   value1.rate = value1.rate - value1.rate / 100 * value.markets[0].earlyBirdDisount;
								   });
							  }					 
						  }
						});
					$scope.total =  $scope.totalBirdCal;
				
				}
			}
		}else if($scope.typePromo == "nightPromotion"){
			$scope.applyPromo = "nightPromotion";
			$scope.total = $scope.totalNightCal;
		}else if($scope.typePromo == "flatPromotion"){
			$scope.applyPromo = "flatPromotion";
			angular.forEach($scope.ratedetail.hotelbyRoom[0].specials, function(value, index){
			  if(value.promotionType == "flatPromotion"){
				   for(i=0;i<$scope.rateDatedetail.length;i++){
						 $scope.rateDatedetail[i].rate = value.markets[0].flatRate;
					}
			  }
			});
			$scope.total = $scope.totalFlatCal;
		}else if($scope.typePromo == "birdPromotion"){
			$scope.applyPromo = "birdPromotion";
			angular.forEach($scope.ratedetail.hotelbyRoom[0].specials, function(value, index){
				  if(value.promotionType == "birdPromotion"){
					  if(value.markets[0].earlyBirdRateCalculat == "flatRate"){
						   if($scope.ratedetail.hotelbyRoom[0].applyFlatPromotion == 1){
							   angular.forEach($scope.ratedetail.hotelbyRoom[0].specials, function(value1, index1){
									  if(value1.promotionType == "flatPromotion"){
										  for(i=0;i<$scope.rateDatedetail.length;i++){
											  console.log($scope.rateDatedetail[i].rate);
									 			$scope.rateDatedetail[i].rate = value1.markets[0].flatRate - value1.markets[0].flatRate / 100 * value.markets[0].earlyBirdDisount;
											}
									  }
								  });
						  }
					  }else  if(value.markets[0].earlyBirdRateCalculat == "contractRate"){
						   angular.forEach($scope.rateDatedetail, function(value1, index1){
							   value1.rate = value1.rate - value1.rate / 100 * value.markets[0].earlyBirdDisount;
						   });
					  }					 
				  }
				});
			$scope.total =  $scope.totalBirdCal;
		}
		
		
		console.log($scope.total);
		
	}
	
	
	/*----------------------------------------------*/
	
	$scope.finalTotal = 0;
	$scope.finalTotalDetails = [];
	
	$scope.breakfastFunction = function(){
		$scope.finalTotal = 0;
		var adultNo = 0;
		var childNo = 0;
		
			
		angular.forEach($scope.addRooms,function(value,key){
			var arr =value.adult.split(" ");
			adultNo = adultNo + parseInt(arr[0]);
			if(value.noOfchild != undefined){
				angular.forEach(value.childselected,function(value1,key1){
					if(value1.age != ""){
						childNo = childNo + 1;
					}
				});
			}
			console.log(value.total);
			$scope.finalTotal = $scope.finalTotal + parseInt(value.total);
			//$scope.finalTotalOther = $scope.finalTotalOther + parseInt(value.totalValueRate);
			
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
		var totalcount = $scope.total;
		console.log(totalcount);
		
		$scope.rateDatedetail = [];
		console.log($scope.addRooms[pIndex]);
		$scope.fillrateDatedetai($scope.addRooms[pIndex].adult);
		
		$scope.total = $scope.totalParPerson;
		var totalcount = $scope.total;
		console.log(totalcount);
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
				console.log(value.breakfast);
			}
			if(value.freeChild != "Free" && value.freeChild != ""){
				totalchileValue = parseInt(totalchileValue) + parseInt(value.freeChild);
			}
		});
	
		
		//$scope.total = parseInt(totalcount) + parseInt(totalchileValue);
		
	
		$scope.commanPromotionFunction();
		$scope.batchMarkupFunction();
		
		console.log(totalchileValue);
		console.log($scope.total);
		$scope.total = $scope.total + parseInt(totalchileValue);
	
		console.log(pIndex);
		$scope.totalValueRate = 0;
		angular.forEach($scope.rateDatedetail,function(value1,key1){
			$scope.totalValueRate = $scope.totalValueRate + value1.rate;
		});
		$scope.addRooms[pIndex].totalValueRate = $scope.totalValueRate;
		$scope.addRooms[pIndex].total = $scope.total;
		$scope.breakfastFunction(); 
		
	}
	
	$scope.addRooms = [];
	$scope.indexCount = 0;
	$scope.newRoom = function($event,index){
		
		if($scope.addRooms.length < 5){
			$scope.addRooms.push( {  } );
			console.log(index);
			$scope.indexCount = $scope.indexCount + 1;
			console.log($scope.indexCount);
			$scope.addRooms[$scope.indexCount].adult = "1 Adult";
			$scope.addRooms[$scope.indexCount].cAllow = "true";
			$scope.addRooms[$scope.indexCount].id = $scope.indexCount;
			//$event.preventDefault();
			$scope.countTotal($scope.indexCount);
		}
		
	};
	
	$scope.lessTotal = function(event,index){
		console.log(index);
		
		 $scope.addRooms.splice(index,1);
		 $scope.indexCount = $scope.indexCount - 1;	 
		console.log($scope.addRooms);
		$scope.breakfastFunction(); 
		
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
		console.log($scope.rateDatedetail);
	}
	
	
	$scope.availableFlag = [];

	$scope.countTotal = function(roomNo){
		console.log(roomNo);
		$scope.countNoOfRoom = roomNo;
		console.log($scope.ratedetail);
		
		console.log($scope.childselected);
		$scope.adultString = "1 Adult";
		//$scope.fillrateDatedetai($scope.adultString);
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
						var arr = $scope.ratedetail.checkIn.split("-");
						var datevalue = (arr[1]+"/"+arr[0]+"/"+arr[2])
						  var d = new Date();
						  var dateOut = new Date(datevalue);
						  
						  var periodDay = value2.allotmentmarket.period.split(" ");
						  dateOut.setDate(dateOut.getDate() -  parseInt(periodDay[0]));
						  
						  console.log(dateOut);
						  console.log(d);
						  if(dateOut <= d){
							  	flag = 1;
							  	angular.forEach($scope.rateDatedetail,function(value3,key3){
									if(value3.fulldate == value.date){
										value3.flag = 1;
									}
								});
						  }else{
						
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
					}
					
				  });
			    });
			   });
		
		$scope.flag = flag;
		
		console.log(roomNo);
		console.log($scope.rateDatedetail);
		$scope.addRooms[roomNo].rateDatedetail = $scope.rateDatedetail;
		
		console.log($scope.total);
		$scope.commanPromotionFunction();
		$scope.batchMarkupFunction();
		console.log($scope.total);
		$scope.totalValueRate = 0;
		angular.forEach($scope.rateDatedetail,function(value1,key1){
			$scope.totalValueRate = $scope.totalValueRate + value1.rate;
		});
		$scope.addRooms[roomNo].totalValueRate = $scope.totalValueRate;
		$scope.addRooms[roomNo].total = $scope.total;
		
		$scope.breakfastFunction();
		
	}
	
	$scope.showDateWiseDetails = function(index){
		console.log("Hiii..Bye");
		console.log(index);
		$scope.rateDatedetailRoomWise = [];
		console.log($scope.addRooms[index].rateDatedetail);
		$scope.currentRoom = index + 1;
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
			template: '/assets/resources/html/htmltemplet/room_wise_info.html',
			scope : $scope,
			//controller:'hoteProfileController',
			className: 'ngdialog-theme-default'
		});
	}
	
     $scope.showPromotion = function(roominfo){
		
		$scope.roomPromotion=roominfo;
		
		console.log($scope.roomPromotion);
		
		ngDialog.open({
			template: '/assets/resources/html/htmltemplet/promotionInfo.html',
			scope : $scope,
			//controller:'hoteProfileController',
			className: 'ngdialog-theme-default'
		});
	}
	
	
});


travelBusiness.controller('hotelBookingController', function ($scope,$http,$filter,ngDialog,notificationService,$window,$cookieStore,$timeout,$cookies) {
	
	
	var session = $cookies['PLAY_SESSION'];
	
	if(session != undefined){
		$scope.sessionValue = 1
	}else{
		$scope.sessionValue = 0;
	}
	
	$scope.AgentId = $cookieStore.get('AgentId');
	$scope.AgentCompany = $cookieStore.get('AgentCompany');
	$scope.checkBoxShow = 0;
	
	$scope.totalAddGala = 0;
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
		
		if($scope.hotel.mealCompulsory != null){
			$scope.totalAddGala = parseInt($scope.hotel.hotelBookingDetails.total) + parseInt($scope.hotel.mealCompulsory); 
		}
		console.log($scope.hotel.datediff);
		console.log($scope.hotel.hotelbyRoom[0].hotelRoomRateDetail[0].minNight);
		if($scope.hotel.datediff < $scope.hotel.hotelbyRoom[0].hotelRoomRateDetail[0].minNight){
			$scope.checkBoxShow = 1;
		}
		
		
		angular.forEach($scope.hotel.hotelbyRoom, function(obj, index){ 
			angular.forEach(obj.hotelRoomRateDetail[0].cancellation, function(obj1, index1){ 
				if(obj1.days == "012" || obj1.days == "016" || obj1.days == "018"){
					obj1.days = $scope.hotel.cancellation_date_diff;
				}else{
					obj1.days = parseInt(obj1.days) + $scope.hotel.cancellation_date_diff;
				}
				
				console.log($scope.hotel.checkIn);
				var arr = $scope.hotel.checkIn.split("-");
				var datevalue = (arr[1]+"/"+arr[0]+"/"+arr[2])
				 var d = new Date();
				  var dateOut = new Date(datevalue);
				  dateOut.setDate(dateOut.getDate() - 3);
				  console.log($filter('date')(dateOut, "dd-MMM-yyyy"));
				  if(dateOut < d){
					  console.log($filter('date')(d, "dd-MMM-yyyy"));
					  obj1.cancellationDate = $filter('date')(d, "dd-MMM-yyyy");
				  }else{
					  console.log($filter('date')(dateOut, "dd-MMM-yyyy"));
					  obj1.cancellationDate = $filter('date')(dateOut, "dd-MMM-yyyy");
				  }
			});
		});
		
		$scope.traveller = $scope.hotel.hotelBookingDetails;
		if($scope.hotel.bookingId == ""){
			$scope.traveller.travellersalutation = 2;
		}
		console.log($scope.hotel);
		console.log($scope.traveller);
		$scope.termsAndConditions = false;
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
	$scope.confirmMsg = 0;
	$scope.confirmBooking = function(){
		
		$scope.confirmMsg = 1;
		console.log($scope.confirmMsg);
		$scope.savetravellerinfo();
	}
	$scope.flagA = "0";
	$scope.savetravellerinfo = function(){
		
		var totalValue = parseInt($scope.hotel.hotelBookingDetails.total) + parseInt($scope.hotel.mealCompulsory);
		if($scope.hotel.paymentType == "Credit" || $scope.hotel.paymentType == "Pre-Payment"){
			if($scope.hotel.availableLimit != null){
				if(parseInt($scope.hotel.availableLimit) < parseInt(totalValue)){
					$scope.flagA = "NocreditLimit";
					notificationService.error("Credit Limit Is Less");
				}else{
					$scope.flagA = "0";
				}
			}else{
				$scope.flagA = "NocreditLimit";
				notificationService.error("Credit limit not define");
			}			
		}
		
		if($scope.flagA == "0"){
			
			if($scope.confirmMsg == 0){
			jQuery('#myModal').modal('show');
		}
		
		if($scope.confirmMsg == 1){
			
			$scope.hotel.hotelBookingDetails.travellersalutation = $scope.traveller.travellersalutation;
			$scope.hotel.hotelBookingDetails.travellerfirstname = $scope.traveller.travellerfirstname;
			$scope.hotel.hotelBookingDetails.travellermiddlename = $scope.traveller.travellermiddlename;
			$scope.hotel.hotelBookingDetails.travellerlastname = $scope.traveller.travellerlastname;
			$scope.hotel.hotelBookingDetails.travellerpassportNo = $scope.traveller.travellerpassportNo;
			$scope.hotel.hotelBookingDetails.travellercountry = $scope.hotel.nationality;
			$scope.hotel.hotelBookingDetails.travellerphnaumber = $scope.traveller.travellerphnaumber;
			$scope.hotel.hotelBookingDetails.travelleremail = $scope.traveller.travelleremail;
			$scope.hotel.hotelBookingDetails.nonSmokingRoom = $scope.traveller.nonSmokingRoom;
			$scope.hotel.hotelBookingDetails.twinBeds = $scope.traveller.twinBeds;
			$scope.hotel.hotelBookingDetails.lateCheckout = $scope.traveller.lateCheckout;
			$scope.hotel.hotelBookingDetails.largeBed = $scope.traveller.largeBed;
			$scope.hotel.hotelBookingDetails.highFloor = $scope.traveller.highFloor;
			$scope.hotel.hotelBookingDetails.earlyCheckin = $scope.traveller.earlyCheckin;
			$scope.hotel.hotelBookingDetails.airportTransfer = $scope.traveller.airportTransfer;
			$scope.hotel.hotelBookingDetails.airportTransferInfo = $scope.traveller.airportTransferInfo;
			$scope.hotel.hotelBookingDetails.enterComments = $scope.traveller.enterComments;
			$scope.hotel.hotelBookingDetails.smokingRoom = $scope.traveller.smokingRoom;
			$scope.hotel.hotelBookingDetails.wheelchair = $scope.traveller.wheelchair;
			$scope.hotel.hotelBookingDetails.handicappedRoom = $scope.traveller.handicappedRoom;
			$scope.hotel.hotelBookingDetails.total = parseInt($scope.hotel.hotelBookingDetails.total) + parseInt($scope.hotel.mealCompulsory);
			
			
			console.log($scope.hotel);
			
			
			
			$scope.flagA = 0;
			$http.post('/saveHotelBookingInfo',$scope.hotel).success(function(data){
				
				console.log("Success");
				console.log(data);
				console.log("-----------");
	    		if(data == "NocreditLimit"){
	    			
	    			$scope.flagA = "NocreditLimit";
	    			notificationService.error("Credit Limit Is Less");
	    		}else if(data == "NullcreditLimit"){
	    			notificationService.error("Credit limit not define");
	    		}else{
	    			notificationService.success("Room Book Successfully");
	    			window.location = "/AgentBookingInfo";
	    		}
	    		
	    	}).error(function(data, status, headers, config) {
				console.log('ERROR');
				notificationService.error("Please Enter Required Fields");
			});
		}
		}
		
	}
	
	
	
	
	});	




travelBusiness.controller('BookingConfirmRejectController', function ($scope,$http,$filter,ngDialog,$cookieStore,notificationService,$cookies) {
	
	
	var session = $cookies['PLAY_SESSION'];
	
	if(session != undefined){
		$scope.sessionValue = 1
	}else{
		$scope.sessionValue = 0;
	}
	
	$scope.bookingData;

	$scope.doThis = 0;
	$scope.confirmationBookingId = function(bookingId, confirmationId, nameConfirm,status){
		
		console.log(bookingId);
		
		if(confirmationId == undefined){
			confirmationId = "";
		}
		
		if(status == "Rejected"){
			if(nameConfirm == null || nameConfirm == "" || nameConfirm == undefined){
				$scope.doThis = 1;
			}else{
				$scope.doThis = 0;
			}
		}else{
			
			$scope.doThis = 0;
		}
		if($scope.doThis == 0){
		$http.get("/getConfirmationInfo/"+bookingId+"/"+confirmationId+"/"+nameConfirm+"/"+status).success(function(response){
			if(status == "Confirm"){
				notificationService.success("Booking Confirm");
				$scope.doThis = 0;
			}
			if(status == "Rejected"){
				notificationService.success("Booking Reject");
				$scope.doThis = 0;
			}
		});
	 }
	}
	
	$scope.getAllBookingData = function(uuId){
		console.log(uuId);
		$http.get("/getBookingInfo/"+uuId).success(function(response){
			console.log("Ok..hi");
			console.log(response);
			$scope.bookingData = response;
		});
	}
	
	

});
