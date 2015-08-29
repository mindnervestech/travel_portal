
angular.module('travel_portal').
controller("LoginController",['$scope', '$http','notificationService','$rootScope','$filter','$upload','ngDialog',
	                                         function($scope, $http,notificationService, $rootScope,  $filter, $upload, ngDialog) {
	
	$scope.forgotpass = function() {
		console.log("HIII bye");
		//$scope.loactionAddr = find.locationAddr;
		ngDialog.open({
			template: '/assets/html/hotel_profile/forgot_password.html',
			scope : $scope,
			className: 'ngdialog-theme-default'				
		});
	
	};
	
	
	$scope.getpassword = function(email){
		console.log(email);
		
		$http.get('/getpassword/'+email).success(function(response){
			if(response == 0){
				$scope.flag = 0;
				ngDialog.close();
			}else{
				$scope.flag = 1;
				
			}
			
			console.log("ok");
		});
				
	} 

}]
);


angular.module('travel_portal').
controller("ApplicationController",function($scope,$http) {
	
	 if (angular.isUndefined(supplierCode) || supplierCode == null){
		 $scope.isSupplier = false;
	 }else{
		 $scope.isSupplier = true;
	 }
	
	console.log($scope.isSupplier);
	
	$scope.permission;
	$scope.init = function(data) {
		
		$scope.permission = data;
		console.log($scope.permission);
	};
	
	$(".form-validate").validate({
        errorPlacement: function(error, element){
            error.insertAfter(element);
        }
    });
	
}); 
angular.module('travel_portal').
controller("supplierAgreementController",['$scope','$rootScope','notificationService','$http','$filter','$upload',
                                         function($scope,$rootScope,notificationService, $http, $filter, $upload) {
	
	var navJs = 0;
	
	console.log(supplierCode);
	
	$scope.pdfFile = "/hotel_profile/getPdfPath/"+supplierCode;
	console.log($scope.pdfFile);
	console.log($rootScope.checkUser);
	if($rootScope.checkUser == "Admin"){
		$scope.uploadpdf = "true";
		$scope.showpdf = "false";
	}else{
		$scope.showpdf = "true";
		$scope.uploadpdf = "false";
	}
	
	
	
	
	$http.get('/getPdfPath1/'+supplierCode).success(function(response){
		console.log("***************");
		console.log(response.found);
		if(response.found == 1){
			$scope.imageShow = "true";
			$scope.labelShow = "false";
		}else{
			$scope.labelShow = "true";
			$scope.imageShow = "false";
		}
	});
	
	
	/*$http.get("/roomtypes/"+supplierCode).success(function(response){
		console.log('success');
		$scope.hotelRoomTypes = response;
	});*/
		
		
	 var Pdffile = null;
	   $scope.selectProfilePdf = function($files)
	   {		
		   Pdffile = $files[0]; 		
	   }
	   
	   $scope.savepdfData = {};
	   $scope.PdfUpload = function(){
		   
		   $scope.savepdfData.supplierCode = supplierCode;
		   $scope.upload = $upload.upload({
	           url: '/savepdf', 
	           method:'post',
	           data:$scope.savepdfData,
	           fileFormDataName: 'file1',
	           file:Pdffile,
	          
	   }).progress(function(evt) {
	           console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
	   }).success(function(data, status, headers, config) {
	          console.log(data);
	          notificationService.success("Upload Successfully");
	          $scope.showtext = false;
	          
	   }); 
	   }
	
}]
);


angular.module('travel_portal').
	controller("ManageHotelImageController",['$scope', '$http','notificationService','$rootScope','$filter','$upload','ngDialog',
	                                         function($scope, $http,notificationService, $rootScope,  $filter, $upload, ngDialog) {
		
		
		
		$scope.AddImgAndInfo= {
				addImg:[],
				supplierCode:''
			};
		
		
		$http.get('/getAllImgs/'+supplierCode).success(function(response){
			console.log(response);
			if(response.length == 0){
				$scope.AddImgAndInfo.addImg.push({});
			}else{
			angular.forEach(response, function(obj, index){
				$scope.AddImgAndInfo.addImg.push({
					pictureName:obj.pictureName,
					img:"/hotel_profile/getImagePath/"+obj.supplierCode+"/"+obj.indexValue,
					description:obj.pictureDescription
				})
				
			});
		}
			console.log($scope.AddImgAndInfo);
		});
		
			
			$scope.newImage = function($event){
				console.log("newLocation");
				$scope.AddImgAndInfo.addImg.push( {  } );
				$event.preventDefault();
			};
		
		
		var generalPic = [];
		 $scope.opengeneralPic = false;
		 $scope.opengeneralPic1 = true;
		 
		 console.log(supplierCode);
		 
	     $scope.selectGeneralPicImage = function($generalPic,index)
	     {
	    	
	    	 generalPic[index] = $generalPic[0]; 
	    	    	 
	     }
	    // $scope.img = "/hotel_profile/getImagePath/"+supplierCode+"?d="+new Date().getTime();
	     $scope.savegeneralPic = function(imgInfo,index){
	    	 console.log(generalPic);
	    	 imgInfo.newImg = generalPic[index];
	    	 imgInfo.supplierCode = supplierCode;
	    	 imgInfo.index = index;
	    	 imgInfo.imgPath = null;
	    	// imgInfo.pictureName = "lobby";
	    	 console.log(imgInfo);

	    	 $scope.upload = $upload.upload({
	             url: '/savegeneralImg', 
	             method:'post',
	             data:imgInfo,
	             fileFormDataName: 'generalImg',
	             file:generalPic[index],
	            
	     }).progress(function(evt) {
	             console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
	     }).success(function(data, status, headers, config) {
	    	 console.log(data);   
	    	 console.log(data.generalPicture);
	    	 $scope.AddImgAndInfo.addImg[index].img = "/hotel_profile/getImagePath/"+data.supplierCode+"/"+index+"?d="+new Date().getTime();
	            notificationService.success("Replace Successfully");
	            $scope.opengeneralPic = false;	
		    	 $scope.opengeneralPic1 = true;
		    	 console.log( $scope.AddImgAndInfo);
	     }); 
	  	 
	     }
		     
	   
		 
		 
		 /*------------------------View Web Page------------------------------*/
		 

			$http.get("/amenities").success(function(response){
				$scope.amenities=response;
			});

			$http.get("/business").success(function(response){
				$scope.business=response;
			});

			$http.get("/leisureSport").success(function(response){
				$scope.leisureSport=response;
			});
		 
		 
		 $scope.amenities_check = [];
		 $scope.leisure_sport_check = [];
		 $scope.business_check = [];
		 
		 $http.get('/finddescrip/'+supplierCode).success(function(response) {
			
			 console.log(response);
			 $scope.gDescription = response.generalDescription;
			 $scope.lDescription = response.lobbyDescription;
		 });
		
		 $http.get('/findAreaData/'+supplierCode).success(function(response) {
				console.log("!!!!!!!!!");
				console.log(response);
				$scope.areaAtt = response;					
		});	
		 
		 $http.get('/findAmenitiesData/'+supplierCode).success(function(response) {
				angular.forEach($scope.amenities, function(obj, index){
					angular.forEach(response, function(obj1, index){
						if ((obj.amenitiesCode == obj1.amenitiesCode)) {
							$scope.amenities_check.push(obj);
						};
					});
				});
			});
		 console.log($scope.amenities);
			console.log($scope.amenities_check);
		 
				$http.get('/findAmenitiesData/'+supplierCode).success(function(response) {
				angular.forEach($scope.leisureSport, function(obj, index){
					angular.forEach(response, function(obj1, index){
						if ((obj.amenitiesCode == obj1.amenitiesCode)) {
							$scope.leisure_sport_check.push(obj);
						};
					});
				});
				});
			console.log($scope.leisure_sport_check);
			
				$http.get('/findAmenitiesData/'+supplierCode).success(function(response) {
				angular.forEach($scope.business, function(obj, index){
					angular.forEach(response, function(obj1, index){

						if ((obj.amenitiesCode == obj1.amenitiesCode)) {
							$scope.business_check.push(obj);
						};
					});
				});
			});
		
		 			
			
	     
		}]
);

angular.module('travel_portal').
	controller("hoteRoomController",['$scope','notificationService','$rootScope','$upload','$http',function($scope,notificationService,$rootScope,$upload,$http){
	    $scope.roomTypeIns = ( {chargesForChildren:"false"} );
	    
		$(".form-validate").validate({
	        errorPlacement: function(error, element){
	            error.insertAfter(element);
	        }
	    });
	    
			$scope.counterArray = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20];
			
			$scope.setOccupancy = function(occupancy){
				$scope.adultcount = []
				console.log(occupancy);	
				for(var i = 1; i <= occupancy; i++){
					$scope.adultcount[i] = i;
				}
				console.log("-----");
				console.log($scope.adultcount);
			}
		
			console.log("hoteRoomController successfully initialized."+supplierCode);

			$scope.$watch("sel_room_type", function(selRoomType) {
				console.log("room type changed...  " + selRoomType);
				//call service to get the details of the selected room type..
			});
			
			$http.get("/roomtypes/"+supplierCode).success(function(response){
				console.log('success');
				$scope.hotelRoomTypes = response;
			});
			$scope.update = 0;
			$scope.updateRoomId = 0;
			$scope.selectType = function(){
				
				$http.get('/roomtypesInfo/'+$scope.roomTypeIns.roomId).success(function(response){
					console.log(response);
					$scope.imgRooms = "/hotel_profile/getRoomImagePathInroom/"+$scope.roomTypeIns.roomId+"?d="+new Date().getTime();
					$scope.roomTypeIns.childAllowedFreeWithAdults = response.childAllowedFreeWithAdults; 
					$scope.roomTypeIns.roomname = response.roomType;
					$scope.roomTypeIns.extraBedAllowed = response.extraBedAllowed; 
					$scope.roomTypeIns.maxAdultOccSharingWithChildren = response.maxAdultOccSharingWithChildren;
					$scope.roomTypeIns.maxAdultOccupancy = response.maxAdultOccupancy; 
					$scope.roomTypeIns.roomType = response.roomType;
					$scope.roomTypeIns.maxOccupancy = response.maxOccupancy; 
					$scope.roomTypeIns.breakfastInclude = response.breakfastInclude; 
					$scope.roomTypeIns.breakfastRate = response.breakfastRate;
					$scope.roomTypeIns.childAge = response.childAge;
					$scope.roomTypeIns.roomSize = response.roomSize;
					$scope.roomTypeIns.freeWifi = response.freeWifi;
					$scope.adultcount = [];
					for(var i = 1; i <= response.maxOccupancy; i++){
						$scope.adultcount[i] = i;
					}
					$scope.roomTypeIns.description = response.description;
					$scope.roomTypeIns.roomSuiteType = response.roomSuiteType;
					$scope.roomTypeIns.chargesForChildren = response.chargesForChildren;
					 $scope.childpolicy = response.roomchildPolicies;
					 
					 $scope.roomamenities =[];
					 
					 angular.forEach($scope.roomAmenities, function(obj, index){
						 obj.isSelected=false;
							angular.forEach(response.amenities, function(obj1, index){
								if ((obj.amenityId == obj1.amenityId)) {				 
									
									 obj.isSelected=true;
							    };
							});
							
						});
					 
					 for(var i=0;i<response.amenities.length;i++)
						 {
					 $scope.roomamenities.push(response.amenities[i].amenityId);
						 }
					 console.log($scope.roomamenities);
					 $scope.updateRoomId = response.roomId;
					 
					 
				});
				$scope.update = 1;
			}
			
			
			
			$scope.initRoomTypePage = function() {
				
				$http.get("/room/amenities").success(function(response) {
					$scope.roomAmenities = response;
				});
			}
			
			$scope.roomamenities = [];
			 $scope.childpolicy = [];
			  $scope.childpolicy.push( {  } );
			 
			 $scope.newchildpolicy = function($event){
			        $scope.childpolicy.push( {  } );
			        $event.preventDefault();
			       // console.log($scope.childpolicy);
			       
			    };
			    
			 			    
			    var roomImg =null;
				 $scope.selectRoomImage = function($roomImg){
					 roomImg = $roomImg[0];
				 }
			    
			    $scope.createroomtypeMsg = false;
			    $scope.roomImginfo = {};
			    $scope.CreateRoomType =function(){
			    	
			    	console.log($scope.update);
			    	
			    	$scope.roomTypeIns.supplierCode = supplierCode; //$scope.roomTypeIns;
			    	$scope.roomTypeIns.roomchildPolicies = $scope.childpolicy;
			    	$scope.roomTypeIns.roomamenities = $scope.roomamenities;
			    	if($scope.roomTypeIns.chargesForChildren == "false")
			    		{
			    		$scope.roomTypeIns.roomchildPolicies = [];
			    		}
			    	
			    	console.log($scope.roomTypeIns);
			    	var flag = 0;
			    /*	angular.forEach($scope.roomTypeIns.roomchildPolicies, function(obj, index){
			    		if(obj.years == undefined && obj.years == null){
			    			flag = 1;
			    		}
			    	});*/
			    	
			    	angular.forEach($scope.roomTypeIns.roomchildPolicies, function(obj, index){
			    		if(obj.extraChildRate == undefined && obj.extraChildRate == null){
			    			flag = 1;
			    		}
			    	});
			    	
			    	
			    	if(flag == 0){
			    	$http.post('/hotel/saveUpdateRoomType',$scope.roomTypeIns).success(function(data){
						console.log(data);
						
						$http.get("/roomtypes/"+supplierCode).success(function(response){
							console.log(response);
							notificationService.success("Save Successfully");
							$scope.hotelRoomTypes = response;
							
							$scope.roomT = true;
							var roomNo = 0;
							
							angular.forEach($scope.hotelRoomTypes, function(obj, index){
								
								 if(index == 0){
									 roomNo = obj.roomId; 
								 }
								 if(roomNo < obj.roomId){
									 roomNo = obj.roomId;
								 }
							 });
							$scope.roomNo = roomNo;
							
							//console.log($scope.roomNo);
							if($scope.update == 0){
							$scope.roomImginfo.roomId = $scope.roomNo;
							}else{
								$scope.roomImginfo.roomId = $scope.updateRoomId;
							}
							$scope.roomImginfo.supplierCode = supplierCode;
							console.log($scope.roomImginfo);
							$scope.upload = $upload.upload({
					             url: '/saveRoomImgs', 
					             method:'post',
					             data:$scope.roomImginfo,
					             fileFormDataName: 'roomPic',
					             file:roomImg,
					            
					     }).progress(function(evt) {
					             console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
					     }).success(function(data, status, headers, config) {
					    	 console.log(data);   
					    	
					     }); 
							
						});					
						
											
					}).error(function(data, status, headers, config) {
						console.log('ERROR');
						notificationService.error("Please Enter Required Fields");
					});
			    	}else{
			    		notificationService.error("Please Enter Required Fields");
			    	}
			    	
			    }
			  
			    $scope.roomChildDeleteId = function(index){
					console.log($scope.childpolicy);
					console.log($scope.childpolicy[$scope.childpolicy.length-1].roomchildPolicyId);
					console.log($scope.roomTypeIns.roomchildPolicies);
					console.log("@#$%$%^&*(*&&^^#%%#%");
								
					if($scope.childpolicy[$scope.childpolicy.length-1].roomchildPolicyId != undefined){
					var r = confirm("Do You Want To Delete!");
				    if (r == true) {
					$http.get('/deleteRoomchild/'+$scope.childpolicy[$scope.childpolicy.length-1].roomchildPolicyId)
					.success(function(){
					//	$scope.roomTypeIns.roomchildPolicies = [];
					//	$scope.childpolicy = [];//$scope.roomTypeIns.roomchildPolicies = [];
						console.log('success');
						$scope.childpolicy.splice($scope.childpolicy.length-1,1);
						//$scope.childpolicy.push( {  } );
					});
				    }
					}else{
						$scope.childpolicy.splice($scope.childpolicy.length-1,1);
					}


				};
			   $scope.roomT = true;
				$scope.clearData = function(){
					$scope.roomT = false;
					$scope.roomTypeIns = null;
					$scope.childpolicy = [];
					angular.forEach($scope.roomAmenities, function(obj, index){
						 obj.isSelected=false;
					 });
					console.log($scope.roomTypeIns);
					$scope.update = 0;
				}
			    
			    $scope.serviceClicked = function(e, roomTypeIns) {
					
					if($(e.target).is(":checked")) {
						$scope.roomamenities.push(roomTypeIns.amenityId);
					} else {
						DeleteItem(roomTypeIns);
					}
					
				}
			    /*|| (generalInfo.serviceId === obj.serviceId))*/
				
			    DeleteItem = function(roomTypeIns){
			    	
					angular.forEach($scope.roomamenities, function(obj, index){
						 if ((roomTypeIns.amenityId === obj)) {
					    	$scope.roomamenities.splice(index, 1);
					    
					       	return;
					    };
					  });
						  
					}
				
			
		}]
);


angular.module('travel_portal').
	controller("hoteProfileController",function($scope, $http,$routeParams,$location,notificationService,$rootScope,$filter, $upload,$cookieStore, ngDialog) {

		
		
		/*$(".form-validate").validate({
	        errorPlacement: function(error, element){
	            error.insertAfter(element);
	        }
	    });
		
		$(".form-validates").validate({
	        errorPlacement: function(error, element){
	            error.insertAfter(element);
	        }
	    });*/
		
		console.log("$$$$$$&$$$$$");
		console.log(permissions);
	$scope.generalInfo = {};
	$scope.descrip = {};
	$scope.countries = [];
	$scope.cities = [];

	$scope.service_check = [];
	$scope.amenities_check = [];
	$scope.business_check = [];
	$scope.leisure_sport_check = [];

	$scope.name = '';

	$scope.contacts = [];
	$scope.mealdata = [];

	$scope.area= {
		areaInfo:[],
		supplierCode:''
	};

	$scope.formArr = [];
	for(var i=0;i<10;i++) {
		var j = {name:'',distance:'',km:'',minutes:''};
		$scope.formArr[i] = j;
	}

	$scope.area1={
		name:'',
		distance:'',
		km:'',
		minutes:''
	};

	for(var i=0;i<10;i++) {
		$scope.area.areaInfo.push($scope.area1);
	}

	$scope.newContact = function($event) {
		$scope.contacts.push( {  } );
		$scope.mealdata.child.push( { } );
		$event.preventDefault();
	};

	$scope.locationsearch= {
		findLocation:[],
		supplierCode:''
	};
	

	$scope.locationsearch.findLocation.push({});
	$scope.newLocation = function($event){
		console.log("newLocation");
		$scope.locationsearch.findLocation.push( {  } );
		$event.preventDefault();
	};

	$scope.submitSearch1 = function (set) {
		$scope.setText = set;
	};

	$scope.init = function () {
		$http.get("/hotelchains").success(function(response) {
			$scope.hotelchain = response;
		}); 

		$http.get("/currency").success(function(response) {
			$scope.currency = response;
			
		});

		$http.get("/hotelbrands").success(function(response) {
			$scope.hotelbrand = response;
		});

		$http.get("/marketrate").success(function(response) {
			$scope.marketrate = response;
		});

		$http.get("/starrating").success(function(response) {
			$scope.starrating = response;
		});

		$http.get("/countries").success(function(response) {
			$scope.countries = response;
		}); 
		


		$scope.onCountryChange = function() {
			$http.get('/cities/'+$scope.generalInfo.countryCode)
			.success(function(data){
				if(data) {
					console.log("----");
					console.log(data);
					$scope.cities = [];
					for(var i = 0; i<data.length; i++){
						$scope.cities.push({
							cityCode:data[i].id,
							cityName:data[i].name
						});
					}
				} else {
					$scope.cities.splice(0);
				}
			});
		}
		
		$scope.onCityChange = function(){
			console.log($scope.generalInfo.cityCode);
		}
		
		
		  $http.get('/findAllData/'+supplierCode).success(function(response) {
				$scope.getallData=response;
				console.log(response);
				$rootScope.hotelName = response.hotelgeneralinfo.hotelNm;
				$rootScope.hotelAddress =  response.hotelgeneralinfo.hotelAddr;
				$cookieStore.put('hotelName',$rootScope.hotelName);
				$cookieStore.put('hotelAddress',$rootScope.hotelAddress);
				 $cookieStore.put('cityCode',response.hotelgeneralinfo.cityCode);
				
				angular.forEach($scope.currency, function(obj, index){

					if ((response.hotelgeneralinfo.currencyCode == $scope.currency[index].currencyCode)) {
						$rootScope.currencyname = $scope.currency[index].currencyName ;	
						 $cookieStore.put('currency',$rootScope.currencyname);
							$rootScope.currencyname = $cookieStore.get('currency');
							var arr = $rootScope.currencyname.split(" - ");
							$rootScope.currencyShotcut = arr[0];
					};
				});
				
					 $cookieStore.put('currencyShotcut',$rootScope.currencyShotcut);
					 
					 $rootScope.currencyname = $cookieStore.get('currency');
					 $rootScope.currencyShotcut = $cookieStore.get('currencyShotcut');
				
				console.log(response.hotelgeneralinfo.isAdmin)
				$http.get('/cities/'+response.hotelgeneralinfo.countryCode)
				.success(function(data){
					if(data) {
						console.log("----");
						console.log(data);
						$scope.cities = [];
						for(var i = 0; i<data.length; i++){
							$scope.cities.push({
								cityCode:data[i].id,
								cityName:data[i].name
							});
						}
					} else {
						$scope.cities.splice(0);
					}
				});
				
				$scope.generalInfo=response.hotelgeneralinfo;
				   $rootScope.checkUser = response.hotelgeneralinfo.isAdmin; 
				 
			});
	}
	
	$rootScope.hotelName= $cookieStore.get('hotelName');
	$rootScope.hotelAddress= $cookieStore.get('hotelAddress');
	
	
	
	$scope.getgeneralinfo = function(){
	
		$http.get('/findAllData/'+$rootScope.supplierCode).success(function(response) {
			$scope.getallData=response;
			$rootScope.hotelName = response.hotelgeneralinfo.hotelNm;
		
			
			$http.get('/cities/'+response.hotelgeneralinfo.countryCode)
			.success(function(data){
				if(data) {
				
					$scope.cities = [];
					for(var i = 0; i<data.length; i++){
						$scope.cities.push({
							cityCode:data[i].id,
							cityName:data[i].name
						});
					}
				} else {
					$scope.cities.splice(0);
				}
			});
			
			$scope.generalInfo=response.hotelgeneralinfo;
			  $rootScope.checkUser = response.hotelgeneralinfo.isAdmin; 
			 
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
		
		}
	
	$scope.getdescription = function()
	{
		$http.get("/services").success(function(response) {
			$scope.services = response;
		});
		
		$http.get('/finddescripData/'+$rootScope.supplierCode).success(function(response) {
			$scope.descrip = response;
			console.log("***********");
			console.log($scope.descrip);
			angular.forEach($scope.services, function(obj, index){
				angular.forEach(response.services, function(obj1, index){

					if ((obj.serviceId == obj1)) {
						$scope.service_check.push(obj.serviceId);
						obj.isSelected=true;
					};
				});
			});
		});
		
		console.log( $cookieStore.get('cityCode'));
		$scope.location = [];
		$http.get('/location/'+$cookieStore.get('cityCode')).success(function(response) {
			for(var i =0 ; i<response.length; i++) {
				$scope.location.push({
					locationId:response[i].id,
					locationNm:response[i].name
				});
			}
			
		}); 

		
		$http.get("/shoppingfacility").success(function(response) {
			$scope.shoppingfacility = response;
		}); 

		$http.get("/nightlife").success(function(response) {
			$scope.nightlife = response;
		}); 
		
		
	}
	$scope.getInternalInfo = function()
	{
		$http.get('/findInternalData/'+$rootScope.supplierCode).success(function(response) {
			$scope.internalInfo = response;
		});
	}
	$scope.getContactInfo = function()
	{
		$http.get("/salutation").success(function(response){
			$scope.salutation=response;
		});
		
		$http.get('/findContactData/'+$rootScope.supplierCode).success(function(response) {
			$scope.contactInfo =  response;
		});
	}
	$scope.getcommunicationInfo = function()
	{
		$http.get('/findCommunicationData/'+$rootScope.supplierCode).success(function(response) {
			$scope.comunicationhotel = response;
			$scope.comunicationhotel.Remail =response.primaryEmailAddr;
			$scope.comunicationhotel.Rccmail =response.ccEmailAddr;
		});
	}
	$scope.getBillInfo = function()
	{
		$http.get("/salutation").success(function(response){
			$scope.salutation=response;
		});
		
		$http.get('/findBillData/'+$rootScope.supplierCode).success(function(response) {
			$scope.bill = response;
			console.log("<><><<><><><><><");
			console.log(response);
		});
	}
	
	$scope.getMealInfo = function(){
		
		$http.get("/MealType").success(function(response){
			$scope.MealTypes=response;			
			
		});
		
		
		$http.get("/MealTypeplan/"+$rootScope.supplierCode).success(function(response){
			$scope.MealType=response;
			console.log("-----------------");
			console.log($scope.MealType);
			if($scope.MealType != null )
				{
				$scope.mealRate = true;
				$scope.haveMeal = "true";
				
				}else
					{
					$scope.haveMeal = "false";
					}
			
			angular.forEach($scope.MealType, function(obj, index){
				$scope.MealType[index].fromPeriod = $filter('date')($scope.MealType[index].fromPeriod, "dd-MM-yyyy");
				$scope.MealType[index].toPeriod = $filter('date')($scope.MealType[index].toPeriod, "dd-MM-yyyy");
								
					
				return;
			});
		});	
		$scope.mealpolicy= {};
		$scope.addnew = function(){
			$scope.mealpolicy= {};
			$scope.contacts = [];
			$scope.mealpolicy= ({taxIncluded : "true"});
			//$scope.cont= ({chargeType:"true"});
			ngDialog.open({
				template: '/assets/html/hotel_profile/create_meal_plan.html',
				scope : $scope,
				//controller:'hoteProfileController',
				className: 'ngdialog-theme-default'
			});
			
			
		};
		
	}
	
	$scope.getAmenitiesInfo = function()
	{
		$http.get("/amenities").success(function(response){
			$scope.amenities=response;
			console.log($scope.amenities);
		});
		
		$http.get('/findAmenitiesData/'+$rootScope.supplierCode).success(function(response) {
			console.log(response);
			angular.forEach($scope.amenities, function(obj, index){
				angular.forEach(response, function(obj1, index){
					if ((obj.amenitiesCode == obj1.amenitiesCode)) {
						$scope.amenities_check.push(obj.amenitiesCode);
						obj.isSelected=true;
					};
				});
			});
		});
		}
		$scope.getLeisureInfo = function()
		{
			$http.get('/findAmenitiesData/'+$rootScope.supplierCode).success(function(response) {
			angular.forEach($scope.leisureSport, function(obj, index){
				angular.forEach(response, function(obj1, index){
					if ((obj.amenitiesCode == obj1.amenitiesCode)) {
						$scope.leisure_sport_check.push(obj.amenitiesCode);
						obj.isSelected=true;
					};
				});
			});
			});
		}
		$scope.getBusinessInfo = function()
		{
			$http.get("/business").success(function(response){
				$scope.business=response;
			});

			$http.get("/leisureSport").success(function(response){
				$scope.leisureSport=response;
			});
			
			$http.get('/findAmenitiesData/'+$rootScope.supplierCode).success(function(response) {
			angular.forEach($scope.business, function(obj, index){
				angular.forEach(response, function(obj1, index){

					if ((obj.amenitiesCode == obj1.amenitiesCode)) {
						$scope.business_check.push(obj.amenitiesCode);
						obj.isSelected=true;
					};
				});
			});
		});
			
			$http.get('/findAmenitiesData/'+$rootScope.supplierCode).success(function(response) {
				angular.forEach($scope.leisureSport, function(obj, index){
					angular.forEach(response, function(obj1, index){
						if ((obj.amenitiesCode == obj1.amenitiesCode)) {
							$scope.leisure_sport_check.push(obj.amenitiesCode);
							obj.isSelected=true;
						};
					});
				});
				});
	}
		
		$scope.getAreaInfo = function(){
			$http.get('/findAreaData/'+$rootScope.supplierCode).success(function(response) {
				
				if (response != undefined) {
					for(var i=0;i<response.length;i++) {
						$scope.formArr[i] = response[i];	
					}
				}
			});
			
		}
		$scope.getTranAndDirInfo = function(){
			$http.get('/findTranDirData/'+$rootScope.supplierCode).success(function(response) {
				console.log("getTranAndDirInfo :: "+response);
				if(response != '')
			 $scope.locationsearch.findLocation = response;
			});
		}
		$scope.gethealthAndSafetyInfo = function(){
			$http.get('/findHealthAndSafData/'+$rootScope.supplierCode).success(function(response) {
				
				  $scope.HealthSafety = response.healthAndSafetyVM;
			       $scope.HealthSafety.expiryDate = $filter('date')(response.healthAndSafetyVM.expiryDate, "dd-MM-yyyy");
			       $scope.HealthSafety.expiryDate1 = $filter('date')(response.healthAndSafetyVM.expiryDate1, "dd-MM-yyyy");
			       $scope.FirePrecaution = response.healthAndSafetyVM;
			       $scope.ExitsAndCorridor = response.healthAndSafetyVM;
			       $scope.AirCondition = response.healthAndSafetyVM;
			       $scope.Lifts = response.healthAndSafetyVM;
			       $scope.Bedrooms = response.healthAndSafetyVM;
			       $scope.KitchenAndHygiene = response.healthAndSafetyVM;
			       $scope.additionalInfo = response.healthAndSafetyVM;
			       $scope.GaswaterHeaters = response.healthAndSafetyVM;
			       $scope.ChildrenFaciliti = response.healthAndSafetyVM;
			       $scope.SwimmingPool = response.healthAndSafetyVM;    
			      
			       
			      
			       
			       angular.forEach(response.docInfo[0].imgpath, function(obj, index){
						response.docInfo[0].imgpath[index].datetime = $filter('date')(response.docInfo[0].imgpath[index].datetime, "dd-MM-yyyy");
						return;
					});
			       
			       $scope.document = response.docInfo[0].imgpath;
			       $scope.docId = response.docInfo[0].id;
			       console.log(response.docInfo);
			       
			       angular.forEach($scope.kidClub, function(obj, index){
						angular.forEach(response.healthAndSafetyVM.monthkid, function(obj1, index){

							if ((obj.name == obj1)) {
								$scope.ChildrenkidClub.push(obj.name);
								obj.isSelected=true;
							};
						});
					});
			       
			       angular.forEach($scope.operational, function(obj, index){
						angular.forEach(response.healthAndSafetyVM.monthOperational, function(obj1, index){

							if ((obj.name == obj1)) {
								$scope.swimmingpoolOperation.push(obj.name);
								obj.isSelected=true;
							};
						});
					});
			       
			       angular.forEach($scope.cctv, function(obj, index){
						angular.forEach(response.healthAndSafetyVM.cctvArea, function(obj1, index){

							if ((obj.name == obj1)) {
								$scope.CCTVStatusArray.push(obj.name);
								obj.isSelected=true;
							};
						});
					});
			});
		}
	
		if($routeParams.id == undefined || $routeParams.id == null )
			{
			$rootScope.supplierCode = supplierCode;
			}
		else{
			$rootScope.supplierCode =$routeParams.id;
			}
		
	console.log("location :"+$location.path());
	if($location.path() == "/profile0")
	{
		$scope.getgeneralinfo();
	}
	if($location.path() == "/profile1")
	{
		$scope.getdescription();
	}
	if($location.path() == "/profile2")
	{
		$scope.getInternalInfo();
	}
	if($location.path() == "/profile3")
	{
		$scope.getContactInfo();
	}
	if($location.path() == "/profile4")
	{
		$scope.getcommunicationInfo();
	}
	if($location.path() == "/profile5")
	{
		$scope.getBillInfo();
	}
	if($location.path() == "/profile7")
	{
		$scope.getMealInfo();
	}
	
	if($location.path() == "/profile8")
	{
		$scope.getAmenitiesInfo();
	}
	if($location.path() == "/profile9")
	{
		$scope.getBusinessInfo();
	}
	/*if($location.path() == "/profile10")
	{
		$scope.getLeisureInfo();
	}*/
	if($location.path() == "/profile10")
	{
		$scope.getAreaInfo();
	}
	if($location.path() == "/profile11")
	{
		$scope.getTranAndDirInfo();
	}
	if($location.path() == "/profile12")
	{
		$scope.gethealthAndSafetyInfo();
	}
	
	
	

	
	
	$scope.findmap = function(find,scope){
		console.log(find);
		$scope.loactionAddr = find.locationAddr;
		ngDialog.open({
			template: '/assets/html/hotel_profile/showMap.html',
			scope : $scope,
			className: 'ngdialog-theme-default'				
		});
		$rootScope.$on('ngDialog.opened', function (e, $dialog) {
			$('#testDiv3').slimScroll({
		          color: '#00f'
		      });
		});
		
		
	}

	$scope.ShowTax = function(tax){
		$scope.taxQTY=false;
		if(tax==true) {
			$scope.taxQTY= false;
		}
		else {
			$scope.taxQTY= true;
		}
	}

	$scope.showMeal = function(mealRate) {
		$scope.mealRate=mealRate;
	
	}

	$scope.closeNewmeal = function (){

		
	}


	$scope.editdata = function(meal){

		$rootScope.mealIdData=meal;
		$scope.mealdata = meal;
		console.log($scope.mealdata);

		ngDialog.open({
			template: '/assets/html/hotel_profile/edit_meal_plan.html',
			scope : $scope,
			className: 'ngdialog-theme-default'
		});
	};
	
	
	$scope.DeleteDocId = function(doc){
		console.log(doc);

		var r = confirm("Do You Want To Delete!");
	    if (r == true) {
		$http.get('/deleteDocument/'+$scope.docId+'/'+doc.imgpathId)
		.success(function(){
			
			console.log('success');
			$http.get('/findHealthAndSafData/'+$rootScope.supplierCode).success(function(response) {
				console.log(response);
				
				 angular.forEach(response.docInfo[0].imgpath, function(obj, index){
						response.docInfo[0].imgpath[index].datetime = $filter('date')(response.docInfo[0].imgpath[index].datetime, "dd-MM-yyyy");
						return;
					});
				 $scope.document = response.docInfo[0].imgpath;
				  $scope.docId = response.docInfo[0].id;
			});
		});
	    }
		};

	$scope.setDeleteId = function(meal){
		console.log(meal.id);

		var r = confirm("Do You Want To Delete!");
	    if (r == true) {
		$http.get('/deletemealpolicy/'+meal.id)
		.success(function(){
			angular.forEach($scope.MealType, function(obj, index){
				if(obj.id == meal.id){
					$scope.MealType.splice(index,1);
				}

			});
			console.log('success');
		});
	    }
	};

	$scope.childDeleteId = function(){
		
		var r = confirm("Do You Want To Delete!");
	    if (r == true) {
		$http.get('/deletechile/'+$rootScope.mealIdData.id)
		.success(function(){
			$scope.MealType.child = [];
			$scope.mealdata.child = $scope.MealType.child = [];

			console.log('success');
		});
	    }

	};
	
	$scope.cctv =[{
		name:'Entrance',
		isSelected:false
	},
	{name:'Lobby',isSelected:false},
	{name:'Corridors',isSelected:false},
	{name:'Fitness Centre',isSelected:false},
	{name:'Indoor Pools',isSelected:false},
	{name:'Indoor Pools',isSelected:false}];
	
	$scope.CCTVStatusArray=[];
	//cctvClicked($event,TVstatus)
	 $scope.cctvClicked = function(e, TVstatus){
			
			if($(e.target).is(":checked")) {
				$scope.CCTVStatusArray.push(TVstatus.name);
			} else {
				DeleteCCTVStatus(TVstatus);
			}
			console.log($scope.CCTVStatusArray);
		}
		
	 DeleteCCTVStatus = function(TVstatus){

			angular.forEach($scope.CCTVStatusArray, function(obj, index){
				if ((TVstatus.name === obj)) {
					$scope.CCTVStatusArray.splice(index, 1);

					return;
				};
			});

		}
	
	
	
	
	$scope.operational = [{
		name:'January',
		isSelected:false
	},
	{name:'February',isSelected:false},
	{name:'March',isSelected:false},
	{name:'April',isSelected:false},
	{name:'May',isSelected:false},
	{name:'June',isSelected:false},
	{name:'July',isSelected:false},
	{name:'August',isSelected:false},
	{name:'September',isSelected:false},
	{name:'October',isSelected:false},
	{name:'November',isSelected:false},
	{name:'December',isSelected:false}];
	
		
	$scope.swimmingpoolOperation=[];
    $scope.operationalClicked = function(e, SwimmingP){
		
		if($(e.target).is(":checked")) {
			$scope.swimmingpoolOperation.push(SwimmingP.name);
		} else {
			DeleteswimmingpoolOperation(SwimmingP);
		}
		console.log($scope.swimmingpoolOperation);
	}
	
    DeleteswimmingpoolOperation = function(SwimmingP){

		angular.forEach($scope.swimmingpoolOperation, function(obj, index){
			if ((SwimmingP.name === obj)) {
				$scope.swimmingpoolOperation.splice(index, 1);

				return;
			};
		});

	}
    
    
    $scope.kidClub = [{
		name:'January',
		isSelected:false
	},
	{name:'February',isSelected:false},
	{name:'March',isSelected:false},
	{name:'April',isSelected:false},
	{name:'May',isSelected:false},
	{name:'June',isSelected:false},
	{name:'July',isSelected:false},
	{name:'August',isSelected:false},
	{name:'September',isSelected:false},
	{name:'October',isSelected:false},
	{name:'November',isSelected:false},
	{name:'December',isSelected:false}];
    
	$scope.ChildrenkidClub=[];
		
	$scope.kidClubClicked = function(e, ChildrenF){
		
		if($(e.target).is(":checked")) {
			$scope.ChildrenkidClub.push(ChildrenF.name);
		} else {
			DeleteChildrenkidClub(ChildrenF);
		}
		console.log($scope.ChildrenkidClub);
	}
	
	DeleteChildrenkidClub = function(ChildrenF){

		angular.forEach($scope.ChildrenkidClub, function(obj, index){
			if ((ChildrenF.name === obj)) {
				$scope.ChildrenkidClub.splice(index, 1);

				return;
			};
		});

	}
	
	
	$scope.serviceClicked = function(e, generalInfo) {

		if($(e.target).is(":checked")) {
			$scope.service_check.push(generalInfo.serviceId);
		} else {
			DeleteItem(generalInfo);
		}
		console.log($scope.service_check);
	}

	DeleteItem = function(generalInfo){

		angular.forEach($scope.service_check, function(obj, index){
			if ((generalInfo.serviceId === obj)) {
				$scope.service_check.splice(index, 1);

				return;
			};
		});

	}

		
	
	$scope.saveUpdateFirePrecaution = false;
	$scope.saveFirePrecaution = function(){
		
		$scope.FirePrecaution.supplierCode = $rootScope.supplierCode;
		console.log($scope.FirePrecaution);
		$http.post('/saveUpdateFirePrecaution', $scope.FirePrecaution).success(function(data){
			console.log('success');
			//$scope.saveUpdateFirePrecaution = true;
			 notificationService.success("Save Successfully");
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
	}
	
	$scope.saveUpdateDocumentation = false;
	$scope.saveDocumentation = function(){
		
		$scope.HealthSafety.supplierCode = $rootScope.supplierCode; 
		console.log($scope.HealthSafety);
		$http.post('/saveUpdateHealthSafety', $scope.HealthSafety).success(function(data){
			console.log('success');
			//$scope.saveUpdateDocumentation = true;
			 notificationService.success("Save Successfully");
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
		
	}
	
	$scope.saveUpExitsAndCorridors = false;
	$scope.saveExitsAndCorridors = function(){
		
		$scope.ExitsAndCorridor.supplierCode = $rootScope.supplierCode; 
		console.log($scope.ExitsAndCorridor);
		$http.post('/saveUpdateExitsAndCorridor', $scope.ExitsAndCorridor).success(function(data){
			console.log('success');
			//$scope.saveUpExitsAndCorridors = true;
			 notificationService.success("Save Successfully");
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
		
	}
	
	$scope.saveUpAirCondition = false;
	$scope.saveAirCondition = function(){
		
		$scope.AirCondition.supplierCode = $rootScope.supplierCode; 
		console.log($scope.AirCondition);
		$http.post('/saveUpdateAirCondition', $scope.AirCondition).success(function(data){
			console.log('success');
			//$scope.saveUpAirCondition = true;
			 notificationService.success("Save Successfully");
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
		
	}
	
	$scope.saveUpLifts = false;
	$scope.saveLifts = function(){
		
		$scope.Lifts.supplierCode = $rootScope.supplierCode; 
		console.log($scope.Lifts);
		$http.post('/saveUpdateLifts', $scope.Lifts).success(function(data){
			console.log('success');
			//$scope.saveUpLifts = true;
			 notificationService.success("Save Successfully");
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
		
	}
	
	$scope.saveUpBedrooms = false;
	$scope.saveBedroomsAsndBalconies = function(){
		
		$scope.Bedrooms.supplierCode = $rootScope.supplierCode; 
		console.log($scope.Bedrooms);
		$http.post('/saveUpdateBedroomsAsndBalconies', $scope.Bedrooms).success(function(data){
			console.log('success');
			//$scope.saveUpBedrooms = true;
			 notificationService.success("Save Successfully");
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
		
	}
	
	$scope.saveUpKitchen = false;
	$scope.saveKitchenAndHygiene = function(){
		
		$scope.KitchenAndHygiene.supplierCode = $rootScope.supplierCode; 
		console.log($scope.KitchenAndHygiene);
		$http.post('/saveUpdateKitchenAndHygiene', $scope.KitchenAndHygiene).success(function(data){
			console.log('success');
			//$scope.saveUpKitchen = true;
			 notificationService.success("Save Successfully");
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
		
	}
	
	$scope.saveUPChildren = false;
     $scope.saveChildrenFaciliti = function(){
		
		$scope.ChildrenFaciliti.supplierCode = $rootScope.supplierCode; 
		$scope.ChildrenFaciliti.monthkid = $scope.ChildrenkidClub;
		console.log($scope.ChildrenFaciliti);
		$http.post('/saveUpdateChildrenFaciliti', $scope.ChildrenFaciliti).success(function(data){
			console.log('success');
			//$scope.saveUPChildren = true;
			 notificationService.success("Save Successfully");
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
		
	}
     
     $scope.saveUpSwiming = false;
     $scope.saveSwimmingPool = function(){
 		
 		$scope.SwimmingPool.supplierCode = $rootScope.supplierCode; 
 		$scope.SwimmingPool.monthOperational = $scope.swimmingpoolOperation;
 		console.log($scope.SwimmingPool);
 		$http.post('/saveUpdateSwimmingPool', $scope.SwimmingPool).success(function(data){
 			console.log('success');
 			//$scope.saveUpSwiming = true;
 			 notificationService.success("Save Successfully");
 			
 		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
 		
 	}
     $scope.cctvSt = false;
     $scope.CCTVStatus={};
     $scope.saveCCTVstatus = function(){
  		
  		$scope.CCTVStatus.supplierCode = $rootScope.supplierCode; 
  		$scope.CCTVStatus.cctvArea = $scope.CCTVStatusArray;
  		console.log($scope.CCTVStatus);
  		$http.post('/saveUpdateCCTVstatus', $scope.CCTVStatus).success(function(data){
  			console.log('success');
  			//$scope.cctvSt = true;
  			 notificationService.success("Save Successfully");
  			
  		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
  		
  	}
     $scope.saveAddition = false;
     $scope.saveAdditionalInfo = function(){
 		
 		$scope.additionalInfo.supplierCode = $rootScope.supplierCode; 
 		console.log($scope.additionalInfo);
 		$http.post('/saveUpdateAdditionalInfo', $scope.additionalInfo).success(function(data){
 			console.log('success');
 			//$scope.saveAddition = true;
 			 notificationService.success("Save Successfully");
 			
 		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
 		
 	}
     $scope.saveGasHeaters = false;
     $scope.saveGaswaterHeaters = function(){
  		
  		$scope.GaswaterHeaters.supplierCode = $rootScope.supplierCode; 
  		console.log($scope.GaswaterHeaters);
  		$http.post('/saveUpdateGaswaterHeaters', $scope.GaswaterHeaters).success(function(data){
  			console.log('success');
  			//$scope.saveGasHeaters = true;
  			 notificationService.success("Save Successfully");
  			
  		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
  		
  	}
    
     
   
     
    var files = null;
   $scope.selectProfileImage = function($files)
   {		
		files = $files[0]; 		/**/
   }
   
   $scope.savedoc = {};
   $scope.saveDocumens = function(){
	   $scope.savedoc.supplierCode = $rootScope.supplierCode;
	   $scope.upload = $upload.upload({
           url: '/savefiles', 
           method:'post',
           data:$scope.savedoc,
           fileFormDataName: 'file1',
           file:files,
          
   }).progress(function(evt) {
           console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
   }).success(function(data, status, headers, config) {
	   
	   angular.forEach(data[0].imgpath, function(obj, index){
		   obj.datetime = $filter('date')(obj.datetime, "dd-MM-yyyy");
	   });
	   
	   //data[0].imgpath.datetime = $filter('date')($scope.MealType[index].fromPeriod, "dd-MM-yyyy");
          console.log(data[0].imgpath);
          /*$scope.HealthSafety.expiryDate = $filter('date')(response.healthAndSafetyVM.expiryDate, "dd-MM-yyyy");*/
          $scope.document = data[0].imgpath;
   }); 
   }
  
	
	$scope.radioValue;
	$scope.generalInfoMsg = false;
	$scope.savegeneralinfo = function() {
		//$scope.generalInfo.supplierCode = "1234";
		console.log($scope.generalInfo.supplierCode);
		console.log($scope.generalInfo);

		$http.post('/saveGeneralInfo', $scope.generalInfo).success(function(data){
			console.log('success');
			console.log(data);
			$scope.generalInfoMsg = true;
			//$scope.MealType = data;		
			$scope.generalInfo.supplierCode=data.ID;
			$rootScope.supplierCode=data.ID;
			$rootScope.supplierName=data.NAME;
			$rootScope.supplierAddr=data.ADDR;
			$rootScope.supplierCurrency=data.Currency;

			//$scope.saveDescription($scope.supp);
			notificationService.success("Save Successfully");
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
	};
	$scope.mealpolicy={};

	$scope.mealPlanSuccessMsg = false;
	$scope.savemealplan = function(){

		$scope.mealpolicy.supplierCode = $rootScope.supplierCode ;

		$scope.mealpolicy.child=$scope.contacts;
		 if($scope.mealpolicy.taxIncluded == "true"){
			 delete $scope.mealpolicy.taxtype;
			 delete $scope.mealpolicy.taxvalue;
		 }
		console.log($scope.mealpolicy);
		$http.post('/savemealpolicy',$scope.mealpolicy).success(function(data){
			console.log('success');
			
			$http.get("/MealTypeplan/"+$rootScope.supplierCode).success(function(response){
				$scope.MealType=response;
				console.log("+++++++");
				console.log($scope.MealType);
				angular.forEach($scope.MealType, function(obj, index){
					$scope.MealType[index].fromPeriod = $filter('date')($scope.MealType[index].fromPeriod, "dd-MM-yyyy");
					$scope.MealType[index].toPeriod = $filter('date')($scope.MealType[index].toPeriod, "dd-MM-yyyy");
					return;
				});
			});	
			
			notificationService.success("Save Successfully");
			ngDialog.close();
			//$scope.mealPlanSuccessMsg = true;
			//$scope.mealpolicy = [];
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});


	};
	
	
	

	$scope.mealPlanUpdateSuccessMsg = false;
	$scope.updatemealplan = function(){
		
		 if($scope.mealdata.taxIncluded == "true"){
			 delete $scope.mealdata.taxtype;
			 delete $scope.mealdata.taxvalue;
		 }
		 angular.forEach($scope.mealdata.child, function(obj, index){
			 if($scope.mealdata.child[index].chargeType == "true"){
				delete $scope.mealdata.child[index].childtaxtype;
				delete $scope.mealdata.child[index].childtaxvalue;
			 }
				 
		 });
		 console.log($scope.mealdata);
		 console.log($scope.mealdata.mealType.mealTypeId);
		 var mealId = $scope.mealdata.mealType.mealTypeId;
		
		 $scope.mealdata.mealType = null;
		 console.log(mealId);
		 $scope.mealdata.mealType = mealId;
		console.log($scope.mealdata);
		
		
		$http.post('/updatemealpolicy',$scope.mealdata).success(function(data){
			console.log('success');
			//$scope.mealPlanUpdateSuccessMsg = true;
			 notificationService.success("Update Successfully");
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});

	};


	$scope.saveAreainfo = function() {


		$scope.area.supplierCode =$rootScope.supplierCode ;
		console.log($scope.formArr);
		console.log("//////////");
		$scope.area.areaInfo = $scope.formArr;
		console.log($scope.area);
		
		/*angular.forEach(, function(obj, index){
			
		});	*/
		var flag = 0;
		console.log($scope.area.areaInfo[0]);
		if($scope.area.areaInfo[0].name == "" || $scope.area.areaInfo[0].name == null){
			flag = 1;
		}
		
		
		if(flag == 0){
		$http.post('/saveattraction',$scope.area).success(function(data){
			console.log('success');
			if(data == "yes")
			{
				$scope.attractionfind = "true";
				$scope.attractionnotfind = "false"
			}
			else
			{
				$scope.attractionfind = "false";
				$scope.attractionnotfind = "true";
			}
			 notificationService.success("Save Successfully");
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
	 }else{
		 notificationService.error("Please Enter Required Fields");
	 }
	}


	$scope.savetranspotDire = function() {

		$scope.locationfind=false;	
		$scope.locationnotfind = false;

		$scope.locationsearch.supplierCode = $rootScope.supplierCode ;
		console.log($scope.locationsearch);

		$http.post('/savetransportDir',$scope.locationsearch).success(function(data){
			console.log('success');
			if(data == "yes")
			{
				$scope.locationfind = "true";
				$scope.locationnotfind = "false";
			}
			else
			{
				$scope.locationnotfind = "true";
				$scope.locationfind = "false";
			}
			 notificationService.success("Save Successfully");
			//$scope.locationfind; 
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
	}

	$scope.InternalInfoSuccess = false;

	$scope.saveInternalInfo = function() {
		$scope.internalInfo.supplierCode = $rootScope.supplierCode ;
		console.log($scope.internalInfo);
		$http.post('/updateInternalInfo',$scope.internalInfo).success(function(data){
			console.log('success');
			notificationService.success("Internal Info Save Successfully");
			$scope.InternalInfoSuccess = true;
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
	}

	//$scope.value1=false;
	$scope.sameadd=function()
	{
     
		if($scope.contactInfo.reservationDetailSame == true)
		{
			$scope.contactInfo.rContactName=$scope.contactInfo.cPersonName;
			$scope.contactInfo.rEmailAddr=$scope.contactInfo.dEmailAddr;
			$scope.contactInfo.rTitle=$scope.contactInfo.cTitle;
			$scope.contactInfo.rDirectTelCode=$scope.contactInfo.dTelCode;
			$scope.contactInfo.rDirectTelNo=$scope.contactInfo.dTelNo;
			$scope.contactInfo.rExtNo=$scope.contactInfo.dExtNo;
		}
		else
		{
			$scope.contactInfo.rContactName="";
			$scope.contactInfo.rEmailAddr="";
			$scope.contactInfo.rTitle="";
			$scope.contactInfo.rDirectTelCode="";
			$scope.contactInfo.rDirectTelNo="";
			$scope.contactInfo.rExtNo="";


		}
	}

	$scope.Contactinfosuccess = false;
	$scope.SaveContactinfo = function() {

		$scope.contactInfo.supplierCode = $rootScope.supplierCode;
		console.log($scope.contactInfo);
		$http.post('/updateContactInfo',$scope.contactInfo).success(function(data){
			console.log('success');
			notificationService.success("Save Successfully");
			//$scope.Contactinfosuccess = true;

		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});

	}

	$scope.BillinfoSucccess = false;
	$scope.Savebillinginfo=function()
	{
		$scope.bill.supplierCode=$rootScope.supplierCode;

		if($scope.bill.bankToBankTransfer == "false"){
			delete $scope.bill.bankName;
			delete $scope.bill.branchName;
			delete $scope.bill.swifiCode;
			delete $scope.bill.accountNo;
			delete $scope.bill.accountType;
			
		}
		console.log($scope.bill);
		$http.post('/updatebillingInfo',$scope.bill).success(function(data){
			console.log('success');
			notificationService.success("Save Successfully");
		//	$scope.BillinfoSucccess = true;

		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});

	}


	$scope.communciatsuccess = false;
	$scope.savecommunciat=function()
	{
		$scope.comunicationhotel.supplierCode=$rootScope.supplierCode;
		console.log($scope.comunicationhotel);
		$http.post('/updateComunication',$scope.comunicationhotel).success(function(data){
			console.log('success');
			notificationService.success("Save Successfully");
			//$scope.communciatsuccess = true;

		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});

	}
	$scope.Descriptinsuccess = false;
	$scope.saveDescription=function(){

		$scope.descrip.supplierCode = $rootScope.supplierCode;

		$scope.descrip.services = $scope.service_check;

		console.log($scope.descrip);
		console.log($scope.descrip.services);
		console.log("//////////////////");
		$http.post('/updateDescription',$scope.descrip).success(function(data){
			console.log('success');
			notificationService.success("Save Successfully");
			//$scope.Descriptinsuccess = true;
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
	}

	$scope.leisuresucess = false;
	$scope.saveleisure_sport = function(){
		$scope.leisure.supplierCode= $rootScope.supplierCode;
		$scope.leisure.amenities=$scope.leisure_sport_check;
		console.log($scope.leisure);
		$http.post('/saveamenities',$scope.leisure).success(function(data){
			console.log('success');
			notificationService.success("Save Successfully");
			//$scope.leisuresucess = true;
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
	}
	
	$scope.leisureClicked = function(e, leisure) {
		if($(e.target).is(":checked")) {
			$scope.leisure_sport_check.push(leisure.amenitiesCode);
		} else {
			DeleteleisureItem(leisure);
		}
		console.log($scope.leisure_sport_check);
	}


	DeleteleisureItem = function(leisure){
		angular.forEach($scope.leisure_sport_check, function(obj, index){
			if ((leisure.amenitiesCode == obj)) {
				$scope.leisure_sport_check.splice(index, 1);
				return;
			};
		});
	}

	$scope.businessSucess = false;
	$scope.savebusiness = function(){
		$scope.businessInfo.supplierCode=$rootScope.supplierCode;
		$scope.businessInfo.amenities=$scope.business_check;
		console.log($scope.businessInfo);
		$http.post('/saveamenities',$scope.businessInfo).success(function(data){
			console.log('success');
			notificationService.success(' Successfully');
			$scope.businessSucess = true;
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
	}
	
	$scope.businessClicked = function(e, businessInfo) {

		if($(e.target).is(":checked")) {
			$scope.business_check.push(businessInfo.amenitiesCode);
		} else {
			DeletebusinessItem(businessInfo);
		}

	}

	DeletebusinessItem = function(businessInfo){

		angular.forEach($scope.business_check, function(obj, index){

			if ((businessInfo.amenitiesCode == obj)) {
				$scope.business_check.splice(index, 1);

				return;
			};
		});

	}

	$scope.amenitiesSuccess = false;
	$scope.saveAmenities = function(){
		//console.log($scope.amenitiesInfo.supplierCode);
		//$scope.amenitiesInfo.supplierCode=$scope.amenitiesInfo.supplierCode;
		$scope.amenitiesInfo.supplierCode= $rootScope.supplierCode;

		$scope.amenitiesInfo.amenities =$scope.amenities_check;
		console.log($scope.amenitiesInfo);
		$http.post('/saveamenities',$scope.amenitiesInfo).success(function(data){
			console.log('success');
			notificationService.success("Save Successfully");
			//$scope.amenitiesSuccess = true;
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
	}
	
	$scope.amenitiesClicked = function(e, amenitiesInfo) {

		if($(e.target).is(":checked")) {
			$scope.amenities_check.push(amenitiesInfo.amenitiesCode);
		} else {
			DeleteamenitiesItem(amenitiesInfo);
		}
		console.log("//////////");
		console.log($scope.amenities_check);
	}

	DeleteamenitiesItem = function(amenitiesInfo){
		angular.forEach($scope.amenities_check, function(obj, index){
			if ((amenitiesInfo.amenitiesCode == obj)) {
				$scope.amenities_check.splice(index, 1);
				return;
			};
		});

	}

});

angular.module('travel_portal').
controller("manageContractsController",['$scope','notificationService','$rootScope','$cookieStore','$filter','$http',function($scope,notificationService,$rootScope,$cookieStore,$filter,$http){
	
	 $scope.currencyname = $cookieStore.get('currency');
	
	 var nowTemp = new Date();
	 var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
	 
	 console.log(now);
	 
	
	/* $scope.textclick = function(){
		 rateData.rateValue = "";
		
	 }*/
	
	$(".form-validate").validate({
        errorPlacement: function(error, element){
            error.insertAfter(element);
        }
    });
	$(".form-validate6").validate({
        errorPlacement: function(error, element){
            error.insertAfter(element);
        }
    });
	
	$(".form-validate7").validate({
        errorPlacement: function(error, element){
            error.insertAfter(element);
        }
    });
	
	
	$scope.showMeals = false;
	$scope.addMeal1 = 'no';
	$scope.showContract = true;
	$scope.showPeriod = false;
	$scope.diffRate = 'no';
	$scope.showRemoveRate = false;
	$scope.showSavedRates = false;
	$scope.rateObject = [];
	$scope.rateMeta = [];
	var showRateCount = 0;
	$scope.isUpdated = false;
	
	console.log($rootScope.hotelName);
	
	$http.get('/getRoomTypes').success(function(response){
		console.log(response);
		$scope.roomTypes = response;
	
	});
	
	$http.get('/getCurrency').success(function(response){
		console.log(response);
		$scope.currencies = response;
	});
	
	$http.get('/getMealTypes').success(function(response){
		console.log(response);
		$scope.mealTypes = response;
	});
	
//	   $scope.roomTypeIns = ( {chargesForChildren:"false"} );
	$scope.rateMeta = [];
	$scope.showExtraBad = 0;
	$scope.showData = function() {
		$scope.rateMeta = [];
		$scope.addNewButton = "false";
		$scope.showRateUpdate = false;
		console.log($scope.formData.room);
		console.log($scope.currencyname);
		console.log($scope.formData.fromDate);
		console.log($scope.formData.toDate);
		
		var arr = $scope.formData.toDate.split("-");
		var tDate = (arr[1]+"/"+arr[0]+"/"+arr[2])
		var arr1 = $scope.formData.fromDate.split("-");
		var fDate = (arr1[1]+"/"+arr1[0]+"/"+arr1[2])
	
		 var toDate = Date.parse(tDate);
         var fromDate = Date.parse(fDate);
		
		if(fromDate < toDate){
		
		$http.get('/getRateData/'+$scope.formData.room+'/'+$scope.formData.fromDate+'/'+$scope.formData.toDate+'/'+$scope.currencyname).success(function(response){
			console.log(response);
			$scope.rateMeta1 = response;
			console.log($scope.rateMeta1);
			$scope.fdate = $scope.formData.fromDate;
			$scope.tdate = $scope.formData.toDate;
			
			for(var i=0;i<$scope.rateMeta1.length;i++) {
				
				//if($scope.rateMeta[i].applyToMarket == false){
				//	console.log($scope.rateMeta[i].id);
					$scope.showMarketTable($scope.rateMeta1[i]);
				//}else{
					
			//	}
				
				}
			/*$scope.rateData = [{meals:'Lunch'}];*/
			console.log($scope.rateMeta1);
			if(angular.isUndefined($scope.rateMeta1) || $scope.rateMeta1 == "") {
				$scope.messageShow = "No Rate Found For This Period please";
				$scope.link = "Add New Rate";
				$scope.addNewButton = "false";
				$scope.showRateUpdate = false;
				$scope.tableshow = false;
				$scope.showPeriod = false;
			} else {
			//	 $scope.showMarketTable($scope.rateMeta);
				//$scope.addNewButton = "true";
				$scope.messageShow = " ";
				$scope.link = " ";
				$scope.tableshow = true;
				//$scope.showRateUpdate = true;
				$scope.showPeriod = false;
			}
			 
		});
		$scope.isUpdated = false;
		$scope.showSavedRates = true;
		}else{
			$scope.messageShow = "Please Select 'To Date' Greater Then 'From Date'";
			$scope.link="";
			alert("Data max");
		}
	};
	
	$scope.EditThisRate = function(rate){
		
		console.log(rate);
		$scope.fdate = rate.fromDate;
		$scope.tdate = rate.toDate;
		
		console.log($scope.fdate);
		console.log($scope.tdate);
		console.log(rate);
		$scope.showExtraBad = rate.normalRate.rateDetails.length -1;
		
		if(rate.special.rateDetails.length == 0){
			$http.get('/getRateObject/'+$scope.formData.room).success(function(response){
				$scope.rateObject.push(response);
				console.log(response);
				angular.forEach($scope.rateObject, function(obj, index){
					rate.special.rateDetails = $scope.rateObject[index].special.rateDetails;
					rate.special.cancellation  = $scope.rateObject[index].special.cancellation;
					
				});
				
			    
			});
		}
		
		if(rate.specialDaysRate.length == 0){
			$http.get('/getRateObject/'+$scope.formData.room).success(function(response){
				$scope.rateObject.push(response);
				console.log(response);
				angular.forEach($scope.rateObject, function(obj, index){
					angular.forEach(obj.specialDaysRate, function(obj1, index1){
						console.log(obj1);
						rate.specialDaysRate[index1] = obj1;
						rate.specialDaysRate[index1].cancellation = obj1.cancellation;
						rate.specialDaysRate[index1].rateDetails = obj1.rateDetails;
					});
				});
				
			    
			});
		}
		console.log(rate);
		/*$http.get('/getRateObject/'+$scope.formData.room).success(function(response){
			$scope.rateObject.push(response);
			console.log($scope.rateObject);
			$scope.showMarketTable($scope.rateObject[0]);
			$scope.messageShow = " ";
			$scope.link = " ";
		});*/
		
		$scope.tableshow = false;
		$scope.addNewButton = "true";
		$scope.showRateUpdate = true;
		console.log($scope.rateMeta);
		$scope.rateMeta[0] = rate;
		console.log($scope.rateMeta);
	}
	
	$scope.createNewRate = function() {
		
		
		for(var i=0;i<$scope.rateObject.length;i++) {
			$scope.rateObject.splice(i,1);
			console.log($scope.rateObject);
			// $scope.showMarketTable($scope.rateObject[i]);
			//$scope.rateObject.normalRate.rateDetails[i] = ( {meals:"Lunch"} );
			//$scope.rateObject.normalRate.rateDetails[i].meals = "Lunch";
			//$scope.rateObject.normalRate.rateDetails[i].onlineMeals = "Lunch";
			console.log(i);
		}
		
		$http.get('/getRateObject/'+$scope.formData.room).success(function(response){
			
			
           angular.forEach(response.normalRate.rateDetails, function(value, key){
				value.onlineRateValue = null;
				value.rateValue = null;
				$scope.showExtraBad = key;
			});
           
           angular.forEach(response.special.rateDetails, function(value, key){
				value.onlineRateValue = null;
				value.rateValue = null;
				
			});
           
           angular.forEach(response.specialDaysRate, function(value, key){
        	   angular.forEach(value.rateDetails, function(value1, key1){
				value1.onlineRateValue = null;
				value1.rateValue = null;
        	   });
			});
           
         console.log(response);
			$scope.rateObject.push(response);
			console.log($scope.rateObject);
			$scope.showMarketTable($scope.rateObject[0]);
			$scope.messageShow = " ";
			$scope.link = " ";
		});
		$scope.tableshow = false;
		$scope.showPeriod = true;
	};
	
	$scope.showContractPage = function() {
		$scope.showContract = true;
		$scope.showPeriod = false;
	};
	
	$scope.isChecked = function(value,index) {
		if(value == 'yes') {
			$scope.rateObject.normalRate.rateDetails[index].includeMeals = true;
			/*$scope.rateObject.normalRate.rateDetails[index].meals = "Lunch";*/
		/*	$scope.rateObject.normalRate.rateDetails[index].onlineMeals = "Lunch";*/
		} else {
			$scope.rateObject.normalRate.rateDetails[index].includeMeals = false;
			$scope.rateObject.normalRate.rateDetails[index].meals = null;
		}
		
	};
	
	$scope.setWeekDay = function(value,flag,index) {
		if(flag == true) {
			$scope.rateObject[index].special.weekDays.push(value);
		} else {
			$scope.rateObject[index].special.weekDays.splice($scope.rateObject[index].special.weekDays.indexOf(value),1);
		}
		console.log($scope.rateObject[index].special.weekDays);
	};
	
	$scope.setRateMetaWeekDay = function(value,flag,index) {
		if(flag == true) {
			$scope.rateMeta[index].special.weekDays.push(value);
		} else {
			$scope.rateMeta[index].special.weekDays.splice($scope.rateMeta[index].special.weekDays.indexOf(value),1);
		}
		console.log($scope.rateMeta[index].special.weekDays);
	};
	
	$scope.addNewRuleforRate = function(index) {

		$scope.rateObject[index].cancellation.push({});
	
	};
	
	$scope.addNewRuleforSavedRate = function(index) {
		$scope.rateMeta[index].cancellation.push({});
		
	};
	
	$scope.removeRuleOfRate = function(index,parentIndex) {
		$scope.rateObject[parentIndex].cancellation.splice(index,1);
	};
	
	$scope.removeRuleOfSavedRate = function(index,parentIndex) {
		
		console.log($scope.rateMeta);
		console.log($scope.rateMeta[parentIndex].cancellation[index].id);
		$scope.id = $scope.rateMeta[parentIndex].cancellation[index].id;
		$scope.normal = false; 
		var r = confirm("Do You Want To Delete!");
	    if (r == true) {
		$http.get("/deletecansell/"+ $scope.id+'/'+$scope.normal).success(function(response){
			console.log('success');
			
			
			$scope.rateMeta[parentIndex].cancellation.splice(index,1);
			
		});
	    }
		//$scope.rateMeta[parentIndex].cancellation.splice(index,1);
	};
	
	$scope.removeRuleOfSpecialRate = function(index,parentIndex) {
		$scope.rateObject[parentIndex].special.cancellation.splice(index,1);
	};
	
	$scope.removeRuleOfSpecialRateMeta = function(index,parentIndex) {
		
		$scope.id = $scope.rateMeta[parentIndex].special.cancellation[index].id;
		$scope.spe = true;
		var r = confirm("Do You Want To Delete!");
	    if (r == true) {
		$http.get("/deleteSpecialcansell/"+ $scope.id+'/'+$scope.spe).success(function(response){
			console.log('success');
			
			
			$scope.rateMeta[parentIndex].special.cancellation.splice(index,1);
			
		});
	    }
		
	};
	
	$scope.addNewRuleforSpecialRate = function(index) {
		$scope.rateObject[index].special.cancellation.push({});
	};
	
	/*$scope.addNewRuleSpecialDaysRate = function(index) {
		console.log(index);
		$scope.rateObject[index].specialDaysRate.push({});
	};*/
	
	$scope.addNewRuleforSpecialRatecancel = function(index,pindex) {
		$scope.rateMeta[pindex].specialDaysRate[index].cancellation.push({});
	};
	$scope.addNewRuleforSpecialRateMeta = function(index) {
		$scope.rateMeta[index].special.cancellation.push({});
	};
	
	$scope.addNewSpecial = function(index) {
		console.log(index);
		$http.get('/getSpecialObject/'+$scope.formData.room).success(function(response){
			 angular.forEach(response.rateDetails, function(value, key){
				 value.rateDetails = null;
				 value.rateValue = null;
			 });
			$scope.rateObject[index].specialDaysRate.push(response);

			console.log($scope.rateObject[index].specialDaysRate);
			console.log(response);
		
		});
//		
//			if($scope.rateObject.length > 0) {
//				$scope.showRemoveRate = true;
//			}
			
	};
	
	$scope.addNewSpecialInUpdate = function(index) {
		console.log(index);
		$http.get('/getSpecialObject/'+$scope.formData.room).success(function(response){
			$scope.rateMeta[0].specialDaysRate.push(response);

			console.log(response);
		
		});
//		
//			if($scope.rateObject.length > 0) {
//				$scope.showRemoveRate = true;
//			}
			
	};
	
	$scope.addNewRate = function() {
		$http.get('/getRateObject/'+$scope.formData.room).success(function(response){
			console.log(response);
			
			 angular.forEach(response.normalRate.rateDetails, function(value, key){
				 value.onlineRateValue = null;
				 value.rateValue = null;
			 });
			 angular.forEach(response.special.rateDetails, function(value, key){
				 value.onlineRateValue = null;
				 value.rateValue = null;
			 });
			 
			 angular.forEach(response.specialDaysRate, function(value, key){
			 angular.forEach(value.rateDetails, function(value1, key1){
				 value1.onlineRateValue = null;
				 value1.rateValue = null;
			 });
			 });
			
			$scope.rateObject.push(response);
			for(i=0;i<$scope.rateObject.length;i++){
				$scope.showMarketTable($scope.rateObject[i]);
			}
			console.log($scope.rateObject);
			
		});
		
			if($scope.rateObject.length > 0) {
				$scope.showRemoveRate = true;
			}
			
	};
	
	$scope.removeRate = function(index) {
		$scope.rateObject.splice(index,1);
		
		if($scope.rateObject.length == 0) {
			$scope.showRemoveRate = false;
		}
	};
	$scope.selectstopsell = function(allot)
	{
		console.log(allot);
		allot.period = null;
		allot.choose = null;
		
	}
	$scope.selectRoomAll = function(allot){
		console.log(allot);
		allot.fromDate = null;
		allot.toDate = null;
		allot.stopPeriod = null;
		allot.stopChoose = null;
	}
	$scope.selectFreesell = function(allot)
	{
		console.log(allot);
		allot.choose = null;
		allot.fromDate = null;
		allot.toDate = null;
		allot.stopPeriod = null;
		allot.stopChoose = null;
		
	}
	$scope.selectstopfreeSale = function(allot){
		console.log(allot);
		allot.stopPeriod = null;
		allot.stopChoose = null;
	}
	$scope.saveRate = function() {
		
		console.log($scope.rateObject);
		console.log($scope.rateMeta1);
		
		for(var i=0;i<$scope.rateObject.length;i++) {
			console.log(i);
			
			$scope.rateObject[i].roomId = $scope.formData.room;
			$scope.rateObject[i].fromDate = $scope.formData.fromDate;
			$scope.rateObject[i].toDate = $scope.formData.toDate;
			$scope.rateObject[i].currency = $scope.currencyname;
			$scope.rateObject[i].supplierCode = supplierCode;
		}
		
		$scope.showDate = false;
		
		var flag = 0;
		console.log($scope.rateObject);
		 angular.forEach($scope.rateObject, function(value, key){
			 angular.forEach(value.allocatedCities, function(value1, key1){
				 angular.forEach($scope.rateMeta1,function(value2, key2){
					 angular.forEach(value2.allocatedCountry,function(value3, key3){
					 
				 if(value1.ticked == true){
					 console.log(value1.name);
					 console.log(value3);
					 if(value1.name == value3){
						flag = 1; 
						
					 }
				 	}
				   });
				 });
			  });
			});
		 
		 angular.forEach($scope.rateObject, function(value, key){
			 angular.forEach(value.normalRate.rateDetails, function(value1, key1){
				 if(value1.onlineRateValue == ""){
					 value1.onlineRateValue = null;
				 }
			 });
		 });
		 console.log(flag);
		if(flag == 0){
		$http.post('/saveRate', {"rateObject":$scope.rateObject}).success(function(data){
			console.log('success');
			if(data == "rateNameSame"){
				notificationService.error("Rate name already exists");
				 $("#focus").focus();
				
			}else{
				notificationService.success("Save Successfully");
			}
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			 notificationService.error("Please enter required fields");
		});
		}else{
			notificationService.error("Rate already Exist For This Market");
		}
	};
	
	$scope.updateRateMeta = function() {
		console.log($scope.rateMeta);
		console.log($scope.rateMeta1);
		var flag = 0;
		$http.get('/getRateRang/'+$scope.rateMeta[0].fromDate+"/"+$scope.rateMeta[0].toDate+"/"+$scope.rateMeta[0].roomId+"/"+supplierCode)
		.success(function(response){
			console.log(response);
			$scope.rateMeta1 = response;
			
			 angular.forEach($scope.rateMeta, function(value, key){
				 angular.forEach(value.allocatedCities, function(value1, key1){
					 angular.forEach($scope.rateMeta1,function(value2, key2){
						 if(value.id != value2.id){
						 angular.forEach(value2.allocatedCountry,function(value3, key3){
						if(value1.name == value3){
							if(value1.ticked == true){
								
								 if(value1.name == value3){
									flag = 1; 
								 }
							 	}
						  }
			 		 
					   });
					}	 
					 });
				  });
				});
		});
		
		
		 console.log(flag);
			if(flag == 0){
		$http.post('/updateRateMeta', {"rateObject":$scope.rateMeta}).success(function(data){
			console.log('success');
			 notificationService.success("Update Successfully");
			//$scope.isUpdated = true;
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
	  }else{
			notificationService.error("Rate already Exist For This Market");
		}
	};
	
	$scope.deleteRate = function(id,index) {
		var r = confirm("Do You Want To Delete!");
	    if (r == true) {
	    	$http.get('/deleteRate/'+id).success(function(response){			
				$scope.rateMeta.splice(index,1);
				$scope.addNewButton = "false";
				$scope.showRateUpdate = false;
			});
	        alert("Delete Successfully !");
	    }	
		
	}
	
	$scope.checkCountry = function(data){
		console.log(data);
		
		var flag = 0;
		
		 angular.forEach($scope.rateMeta1,function(value, key){
			 angular.forEach(value.allocatedCountry,function(value1, key1){
				 if(value1 == data.name && data.ticked == true){
					 
					 flag = 1;
				 }
			 });
		 });
		if(flag == 1){
			data.ticked = false;
			
			 notificationService.error("Rate already Exist For This Market");
		}
		
	}
    
    $scope.selectedRatesId;
	console.log($scope.selectedRatesId);
	//$scope.webBrowsersGrouped =[];
	$scope.msClose;
	$scope.getSelectedCity = [];
	
	
	$scope.showMarketTable = function(alloc) {
		 
			
		console.log(alloc);
		$scope.getSelectedCity.splice(0);
		if(angular.isUndefined(alloc.id)) {
			$scope.selectedRatesId = 0;
		} else {
			$scope.selectedRatesId = alloc.id;
		}
		console.log($scope.rateObject);
		console.log($scope.selectedRatesId);
		$http.get('/getMarketGroup/'+$scope.selectedRatesId)
		.success(function(data){
			console.log(data);
			if(data) {
				alloc.allocatedCities = [];
				
					for(var i = 0; i<data.length;i++){
						alloc.allocatedCities.push({
							name:'<strong>'+data[i].country.marketName+'</strong>',
							multiSelectGroup:true
						});
						for(var j =0; j<data[i].country.conutryvm.length;j++){
							if(alloc.applyToMarket == false)
								{
							alloc.allocatedCities.push({
								name:data[i].country.conutryvm[j].countryName,
								ticked:data[i].country.conutryvm[j].tick
							});
								}else{
									console.log("True all tick");
									alloc.allocatedCities.push({
									name:data[i].country.conutryvm[j].countryName,
									ticked:true
									});
									
									}
						
						}
						alloc.allocatedCities.push({
							multiSelectGroup:false
						});
					}
					
					//console.log(alloc.allocatedCities);
					console.log(alloc.allocatedCities);
					//alloc.allocatedCities =  $scope.webBrowsersGrouped; //[{name:"pune",ticked:true}];	
			} 
		});
	}
	
	$scope.applyToAll = function(allot){
		for(var i = 0; i<allot.allocatedCities.length;i++){
			if(allot.allocatedCities[i].multiSelectGroup == undefined ){
				allot.allocatedCities[i].ticked = true;
			}
		}
		/*console.log($scope.selectedRatesId);
		if($scope.selectedRatesId != 0) {
			$scope.setSelection(allot);
		}*/
		
	}
	
	
	$scope.setSelection = function(allot) {
		
		$scope.getSelectedCity.splice(0);
		console.log(allot.allocatedCities)
			for(var i = 0; i<allot.allocatedCities.length;i++){
				if(allot.allocatedCities[i].multiSelectGroup == undefined ){
					$scope.getSelectedCity.push({
                        name:allot.allocatedCities[i].name,
                        ticked:allot.allocatedCities[i].ticked,
                        countryCode : allot.allocatedCities[i].marketCode
					});
				}
			}
			$http.post('/setCitySelection',{city:$scope.getSelectedCity,id:$scope.selectedRatesId})
			.success(function(data){
				
			});
		
	};
	
	
	
	
	
	
}]);


angular.module('travel_portal').
controller("manageSuppliersController",function($scope,notificationService,$rootScope,$location, $http,ngDialog){
		
	$(".form-validate").validate({
        errorPlacement: function(error, element){
            error.insertAfter(element);
        }
    });
	
	$scope.preferHotel = function(perferInfo){
		console.log(perferInfo);
		$scope.userId = perferInfo.code;
		$http.get('/perferUser/'+$scope.userId).success(function(response){
			console.log("Success")
			 notificationService.success("Hotel Perferred");
		
		});
	}
	
	$scope.blockresult = function(blockinfo){
		$scope.userId = blockinfo.id;
		//console.log($scope.userId);
		$http.get('/blockUser/'+$scope.userId).success(function(response){
			console.log("Success")
		$scope.blockUsers.splice($scope.blockUsers.indexOf(blockinfo),1);
		$scope.getData();
	});
		ngDialog.close();
	}
	
	$scope.rejectresult = function(rejectinfo){
		$scope.userId = rejectinfo.id;
		//console.log($scope.userId);
		$http.get('/rejectUser/'+$scope.userId).success(function(response){
			console.log("Success")
		$scope.approvedUsers.splice($scope.approvedUsers.indexOf(rejectinfo),1);
		$scope.getData();
	});
		ngDialog.close();
	}	
	$scope.approvresult = function(approvinfo){
		
		$scope.userId = approvinfo.id;
		$scope.email = approvinfo.email;
		$scope.supplierCode = approvinfo.code;
		$http.get('/approveUser/'+$scope.userId+'/'+$scope.email+'/'+$scope.supplierCode).success(function(response){
			$scope.pendingUsers.splice($scope.pendingUsers.indexOf(approvinfo),1);
			$scope.getData();
		});
		ngDialog.close();
	}
	
	
			$scope.getData = function() {
				
				$http.get('/getPendingUsers').success(function(response){
					console.log(response);
					$scope.pendingUsers = response;
				});
				
				$http.get('/getApprovedUsers').success(function(response){
					console.log(response);
					$scope.approvedUsers = response;
				});
				
				$http.get('/getRejectedUsers').success(function(response){
					console.log(response);
					$scope.rejectedUsers = response;
				});
				
				$http.get('/getBlockUsers').success(function(response){
					console.log(response);
					$scope.blockUsers = response;
				});
				
			}
		
		$scope.approvePending = function() {
			$scope.userId = $scope.generalInfo.id;
			$scope.email = $scope.generalInfo.email;
			$scope.supplierCode = $scope.generalInfo.code;
			console.log($scope.generalInfo.perfer);
			if($scope.generalInfo.perfer == undefined){
				$scope.perfer = false; 
			}else{
				$scope.perfer = $scope.generalInfo.perfer;
			}
			
			$http.get('/approveUser/'+$scope.userId+'/'+$scope.email+'/'+$scope.supplierCode+'/'+$scope.perfer).success(function(response){
				$scope.pendingUsers.splice($scope.pendingUsers.indexOf($scope.generalInfo),1);
				$scope.getData();
			});
		}
		
		$scope.ApprovedUser = function(user) {
			//$scope.userId = userId;
			console.log(user);
			
			$scope.generalInfo = user;
			console.log($scope.generalInfo);
			$http.get("/marketrate").success(function(response) {
				$scope.marketrate = response;
			});
			
			ngDialog.open({
				template: 'Approved',
				scope : $scope,
				className: 'ngdialog-theme-default'
			});
			
		}
		
		$scope.rejectedUser = function(user) {
			//$scope.userId = userId;
			console.log(user);
			
			$scope.generalInfo = user;
			console.log($scope.generalInfo);
			$http.get("/marketrate").success(function(response) {
				$scope.marketrate = response;
			});
			
			ngDialog.open({
				template: 'Reject',
				scope : $scope,
				className: 'ngdialog-theme-default'
			});
			
		}
		
		
		$scope.pendingU = function(user) {
			
			console.log(user);
			
			$scope.generalInfo = user;
			console.log($scope.generalInfo);
			$http.get("/marketrate").success(function(response) {
				$scope.marketrate = response;
			});
			
			ngDialog.open({
				template: 'pending',
				scope : $scope,
				className: 'ngdialog-theme-default'
			});
			
		}
		
		$scope.ApprovReject = function(){
			//
			console.log($scope.generalInfo);
			$scope.userId = $scope.generalInfo.id;
			//console.log($scope.userId);
			$http.get('/rejectUser/'+$scope.userId).success(function(response){
				console.log("Success")
			$scope.approvedUsers.splice($scope.approvedUsers.indexOf($scope.generalInfo),1);
			$scope.getData();
		});
		}
		
		$scope.pendingUser = function() {
			
			$scope.userId = $scope.generalInfo.id;
			$http.get('/pendingUser/'+$scope.userId).success(function(response){
				$scope.rejectedUsers.splice($scope.rejectedUsers.indexOf($scope.generalInfo),1);
				$scope.getData();
			});
		}
		
		$scope.searchSupplier = function(find){
			
			console.log($scope.find);
			$scope.supplierCode = $scope.find.supplierCode;
			$scope.supplierName = $scope.find.hotelNm;
	
			$http.get('/supplierfind/'+$scope.supplierCode+'/'+$scope.supplierName).success(function(response){
				console.log(response);
				if(response == "true"){
					//window.open('http://localhost:9000/adminLogin#/findSupplier');
					
				}else{
					notificationService.error("Supplier Not Found");
				}
				
			}).error(function(data, status, headers, config) {
				console.log('ERROR');
				notificationService.error("Please Enter Required Fields");
			});
			
			
			
			//$http.post('/findSupplier',$scope.find).success(function(data){
		//		console.log("Success")
			//	console.log(data);
			//	$scope.flagAdmin = 1;
		//	$location.path("/supplier/206");
		//window.location.assign("hotel_profile/206");
				
				 //window.open('hotel_profile/'+$scope.supplierCode+"/"+$scope.flagAdmin);
				/*$http.get('/supplierfind/'+$scope.supplierCode+'/'+$scope.supplierName).success(function(response){
					console.log("Success");
				});*/
				/*$location.path('supplierfind/'+$scope.supplierCode+"/"+$scope.supplierName);*/
		
			
		//});
			
			
		}
		
});


angular.module('travel_portal').
controller("manageSpecialsController",['$scope','notificationService','$filter','$rootScope','$http',function($scope,notificationService,$filter,$rootScope, $http){
	
	$(".form-validate").validate({
        errorPlacement: function(error, element){
            error.insertAfter(element);
        }
    });
	
	$scope.specialsObject = [];
	$scope.specialsData = [];
	$scope.showRemoveMarket = false;
	$scope.showSavedRemoveMarket = false;
	$scope.showRemovePeriod = false;
	$scope.showSavedPeriods = false;
	$scope.isCreateNew = false;
	
	$http.get("/getPeriod/"+supplierCode).success(function(response){
		console.log('success');
		console.log(response);
		$scope.allperiod = response;
		
		
		 angular.forEach($scope.allperiod, function(obj, index){
				$scope.allperiod[index].fromDate = $filter('date')(response[index].fromDate, "dd-MM-yyyy");
				
				$scope.allperiod[index].toDate = $filter('date')(response[index].toDate, "dd-MM-yyyy");
				return;
			});
		
	});
	
	$http.get('/getRooms').success(function(response){
		console.log(response);
		$scope.rooms = response;
	});
	
	$http.get('/getfreeStay').success(function(response){
		console.log("response");
		console.log(response);
		$scope.freeStay = response;
	});
	
	
	$scope.createNewPeriod = function() {
		$scope.specialsObject = [];
		$http.get('/getSpecialsObject').success(function(response){
			$scope.specialsObject.push(response);
			console.log($scope.specialsObject);
			$scope.specialsObject[0].markets[0].applyToMarket="true";
			$scope.showMarketTable($scope.specialsObject[0].markets[0]);
			
			$scope.isCreateNew = true;
			$scope.showSavedPeriods = false;
			$scope.formData.datePeriodId = null;
		});
		
		if($scope.specialsObject.length > 0) {
			$scope.showRemovePeriod = true;
		}
	}
	
	$scope.addNewMarket = function(index) {
		$scope.specialsObject[index].markets.push({applyToMarket:"true",id:0,allocatedCities:[]});
		console.log($scope.specialsObject[index].markets);
		for(var i=1;i<$scope.specialsObject[index].markets.length;i++){
			$scope.showMarketTable($scope.specialsObject[index].markets[i]);
		}
		//$scope.showMarketTable($scope.specialsObject[index].markets);
		if($scope.specialsObject[index].markets.length > 0) {
			$scope.showRemoveMarket = true;
		}
	}
	
	$scope.addNewSavedMarket = function(index) {
		$scope.specialsData[index].markets.push({applyToMarket:"true"});
		 /*$scope.allotmentM.push( {applyMarket:"true"} );*/
		$scope.showMarketTable($scope.specialsData[index].markets);
		if($scope.specialsData[index].markets.length > 0) {
			$scope.showSavedRemoveMarket = true;
		}
	}
	
	$scope.deleteMarket = function(parentIndex,index) {
		$scope.specialsObject[parentIndex].markets.splice(index,1);
		if($scope.specialsObject[parentIndex].markets.length == 1) {
			$scope.showRemoveMarket = false;
		}
	}
	
	$scope.deleteSavedMarket = function(parentIndex,index) {
		
		console.log($scope.specialsData[parentIndex].markets[index].id);
		$scope.marketId = $scope.specialsData[parentIndex].markets[index].id;
		var r = confirm("Do You Want To Delete!");
	    if (r == true) {
		$http.get("/deleteMarket/"+$scope.marketId).success(function(response){
			console.log('success');
			
			$scope.specialsData[parentIndex].markets.splice(index,1);
			if($scope.specialsData[parentIndex].markets.length == 1) {
				$scope.showSavedRemoveMarket = false;
			}
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			$scope.specialsData[parentIndex].markets.splice(index,1);
		});
	    }
		
		
		
	}
	
	$scope.deletePeriod = function(index) {
		console.log($scope.specialsData);
		$scope.id = $scope.specialsData[0].id;
		
		
		var r = confirm("Do You Want To Delete!");
	    if (r == true) {
		$http.get("/deletePeriod/"+$scope.id).success(function(response){
			console.log('success');
			
			$scope.showSavedPeriods = false;
			console.log($scope.showRemovePeriod);
			$http.get("/getPeriod/"+supplierCode).success(function(response){
				console.log('success');
				console.log(response);
				$scope.allperiod = response;
				
				
				 angular.forEach($scope.allperiod, function(obj, index){
						$scope.allperiod[index].fromDate = $filter('date')(response[index].fromDate, "-MM-dd");
						
						$scope.allperiod[index].toDate = $filter('date')(response[index].toDate, "-MM-dd");
						return;
					});
				
			});
			
		});
		 alert("Delete Successfully !");
	    }
		
		/*$http.get('/deletePeriod', {"specialsData":$scope.specialsData}).success(function(data){
			console.log('success');
			
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});*/
		
	}
	
	$scope.checkCountry = function(data){
		console.log(data);
	}
	
	$scope.checkDatesandcountry = {}
	$scope.savePeriod = function() {
		$scope.specialsObject[0].supplierCode = supplierCode;
		console.log($scope.specialsObject);
		
		for(var i=0;i<$scope.specialsObject[0].markets.length;i++) {
			
			if($scope.specialsObject[0].markets[i].allocatedCities.length == 0)
				{
				 $scope.showMarketTable($scope.specialsObject[0].markets[i]);
			
				}
		}
		
		var arraycountry = [];
		var count=0;
		angular.forEach($scope.specialsObject[0].markets,function(value,key){
			angular.forEach(value.allocatedCities,function(value1,key1){
				if(value1.ticked == true){
					arraycountry[count] = value1.name;
					count++;
				} 
			});
		});
			/*console.log(arraycountry);
			 $http.get("/checkPeriod/"+supplierCode+'/'+tDate,).success(function(response){
				 
			 });*/
	  /*$http.post('/UpdateSpecificMarkup',$scope.selectedSpecificData).success(function(data){
		  
	  });*/
			 
		
		
		var arr = $scope.specialsObject[0].toDate.split("-");
		var tDate = (arr[1]+"/"+arr[0]+"/"+arr[2])
		var arr1 = $scope.specialsObject[0].fromDate.split("-");
		var fDate = (arr1[1]+"/"+arr1[0]+"/"+arr1[2])
	
		 var toDate = Date.parse(tDate);
         var fromDate = Date.parse(fDate);
		
		if(fromDate < toDate){
			if($scope.specialsObject[0].promotionName != null){
				if($scope.specialsObject[0].roomTypes.length != 0){
			$http.post('/saveSpecials', {"specialsObject":$scope.specialsObject}).success(function(data){
				console.log('success');
				 notificationService.success("Save Successfully");
				 $http.get("/getPeriod/"+supplierCode).success(function(response){
						console.log('success');
						console.log(response);
						$scope.allperiod = response;
						
						
						 angular.forEach($scope.allperiod, function(obj, index){
								$scope.allperiod[index].fromDate = $filter('date')(response[index].fromDate, "dd-MM-yyyy");
								
								$scope.allperiod[index].toDate = $filter('date')(response[index].toDate, "dd-MM-yyyy");
								return;
							});
						
					});
				
			}).error(function(data, status, headers, config) {
				console.log('ERROR');
				notificationService.error("Please Enter Required Fields");
			});
			$("#roomtypeshow").hide();
			$("#promotion").hide();
			$("#datediffShow").hide();
				}
				else
					{
					$("#roomtypeshow").show();
					}
			}
			else
			{
			$("#promotion").show();
			}
			
		}else
			{
			$("#datediffShow").show();
			}
		
	}
	
	$scope.updatePeriod = function() {
		$scope.specialsData[0].supplierCode = supplierCode;
		console.log($scope.rooms);
		if($scope.specialsData[0].roomTypes.length == 0){
		angular.forEach($scope.specialsData,function(value,key){
			angular.forEach(value.roomallInfo,function(value1,key1){
				$scope.specialsData[0].roomTypes[key1] = value1.roomId;
			});
		});
		}
		
		console.log($scope.specialsData);
		$http.post('/updateSpecials', {"specialsObject":$scope.specialsData}).success(function(data){
			console.log('success');
			 notificationService.success("Update Successfully");
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
	}
	
	$scope.showPeriods = function() {
		console.log($scope.formData);
		
		var arr = $scope.formData.datePeriodId.split("@");
		$scope.formData.fromDate = arr[0];
		$scope.formData.toDate = arr[1];
		$scope.formData.promotionName = arr[2];
		
		$http.get('/getSpecialsData/'+$scope.formData.fromDate+'/'+$scope.formData.toDate+'/'+$scope.formData.promotionName).success(function(response){
			console.log(response);
			$scope.specialsData = response;
			//$scope.specialsData[0].roomTypes = [];
			//$scope.specialsData[0].roomTypes = $scope.specialsData[0].roomallInfo;
			console.log($scope.specialsData);
			for(var i=1;i<$scope.specialsData[0].markets.length;i++){
				 $scope.showMarketTable($scope.specialsData[0].markets[i]);
			}
			if(angular.isUndefined($scope.specialsData) || $scope.specialsData == "") {
				$scope.messageShow = "No Period Found then";
				$scope.link = "Add New Rate";
				$scope.showRateUpdate = false;
				$scope.showSavedPeriods = false;
			} else {
			
				console.log("*(*(*((*(*(*");
				console.log($scope.specialsData[0].markets[0]);
			//	for(var i=0;i<$scope.specialsData.length;i++) {
					
					//if($scope.specialsData[0].markets[0].applyToMarket == "false"){
						//console.log($scope.specialsData[i].id);
						$scope.showMarketTable($scope.specialsData[0].markets[0]);
					//}
					
					//}
				
				$scope.isCreateNew =false;
				$scope.messageShow = " ";
				$scope.link = " ";
				$scope.showRateUpdate = true;
				$scope.showSavedPeriods = true;
				var i,j,k;
				for(i=0;i<$scope.specialsData.length;i++) {
					for(j=0;j<$scope.rooms.length;j++) {
						for(k=0;k<$scope.specialsData[i].roomallInfo.length;k++) {
							
								$scope.rooms[j].isSelected = false;
							
						};
					};
				};
				
				for(i=0;i<$scope.specialsData.length;i++) {
					for(j=0;j<$scope.rooms.length;j++) {
						for(k=0;k<$scope.specialsData[i].roomallInfo.length;k++) {
							if($scope.rooms[j].roomId == $scope.specialsData[i].roomallInfo[k].roomId) {
								$scope.rooms[j].isSelected = true;
							}
						};
					};
				};
				
			}
			
			console.log($scope.rooms);
		});
	
	}
	
	
	
	$scope.selectedRatesId;
	console.log($scope.selectedRatesId);
	//$scope.webBrowsersGrouped =[];
	$scope.msClose;
	$scope.getSelectedCity = [];
	
	
	$scope.showMarketTable = function(alloc) {
		 
		
		//console.log(alloc);
		//$scope.getSelectedCity.splice(0);
		if(angular.isUndefined(alloc.id)) {
			$scope.selectedRatesId = 0;
		} else {
			$scope.selectedRatesId = alloc.id;
		}
		console.log($scope.rateObject);
		console.log($scope.selectedRatesId);
		$http.get('/getSpecialMarketGroup/'+$scope.selectedRatesId)
		.success(function(data){
			if(data) {
				
					alloc.allocatedCities = [];
								
				
					for(var i = 0; i<data.length;i++){
						alloc.allocatedCities.push({
							name:'<strong>'+data[i].country.marketName+'</strong>',
							multiSelectGroup:true
						});
						
						for(var j =0; j<data[i].country.conutryvm.length;j++){
							if(alloc.applyToMarket == "false")
								{
							alloc.allocatedCities.push({
								name:data[i].country.conutryvm[j].countryName,
								ticked:data[i].country.conutryvm[j].tick
							});
								}else{
									console.log("True all tick");
									alloc.allocatedCities.push({
									name:data[i].country.conutryvm[j].countryName,
									ticked:true
									});
									
									}
						
						}
						alloc.allocatedCities.push({
							multiSelectGroup:false
						});
					}
					
					//console.log(alloc.allocatedCities);
					console.log(alloc.allocatedCities);
					//alloc.allocatedCities =  $scope.webBrowsersGrouped; //[{name:"pune",ticked:true}];	
			} 
		});
	}
	
	$scope.applyToAll = function(allot){
		alert("HIIIII");
		for(var i = 0; i<allot.allocatedCities.length;i++){
			if(allot.allocatedCities[i].multiSelectGroup == undefined ){
				allot.allocatedCities[i].ticked = true;
				console.log("Hiiii");
			}
		}
		/*console.log($scope.selectedRatesId);
		if($scope.selectedRatesId != 0) {
			$scope.setSelection(allot);
		}*/
		
	}
	
}]);


angular.module('travel_portal').
controller("manageAgentController",['$scope','notificationService','$filter','$rootScope','$http','ngDialog',function($scope,notificationService,$filter,$rootScope, $http,ngDialog){
	
	$(".form-validate").validate({
        errorPlacement: function(error, element){
            error.insertAfter(element);
        }
    });
	
	
	$scope.blockresult = function(blockinfo){
		console.log(blockinfo);
		$scope.userId = blockinfo.id;
		$http.get('/blockresultAgent/'+$scope.userId).success(function(response){
			console.log("Success")
			 notificationService.success("Reject Successfully");
		$scope.approvedUsers.splice($scope.approvedUsers.indexOf(blockinfo),1);
		$scope.getData();
		ngDialog.close();
	});
	}
	
	$scope.rejectresult = function(rejectinfo){
		console.log(rejectinfo);
		$scope.userId = rejectinfo.id;
		$http.get('/rejectAgent/'+$scope.userId).success(function(response){
			console.log("Success")
			 notificationService.success("Reject Successfully");
		$scope.approvedUsers.splice($scope.approvedUsers.indexOf(rejectinfo),1);
		$scope.getData();
		
		});
		ngDialog.close();
	}	
	$scope.approvresult = function(approvinfo){
		console.log(approvinfo);
		$scope.userId = approvinfo.id;
		$scope.email = approvinfo.EmailAddr;
		$scope.agentCode = approvinfo.agentCode;
		$scope.loginId = approvinfo.loginId;
		$http.get('/approveAgent/'+$scope.userId+'/'+$scope.email+'/'+$scope.agentCode).success(function(response){
			 notificationService.success("Approved Successfully");
			$scope.pendingUsers.splice($scope.pendingUsers.indexOf(approvinfo),1);
			$scope.getData();
		});
		ngDialog.close();
	}
	
	
	$scope.getData = function() {
	
	   $http.get('/getApprovedAgent').success(function(response){
			
			console.log(response);
			$scope.approvedUsers = response;
		});
		
		$http.get('/getPendingAgent').success(function(response){
			console.log(response);
			$scope.pendingUsers = response;
		});
		
		$http.get('/getRejectedAgent').success(function(response){
			console.log(response);
			$scope.rejectedUsers = response;
		});
		
		$http.get('/getBlockAgent').success(function(response){
			console.log(response);
			$scope.blockUsers = response;
		});
		
	}
	
	$scope.pendingU = function(user) {
		
		console.log(user);
		
		$scope.generalInfo = user;
		console.log($scope.generalInfo);
		/*$http.get("/marketrate").success(function(response) {
			$scope.marketrate = response;
		});*/
		
		ngDialog.open({
			template: 'pending',
			scope : $scope,
			className: 'ngdialog-theme-default'
		});
		
	}
	
	$scope.rejectedUser = function(user) {
	
		console.log(user);
		
		$scope.generalInfo = user;
		console.log($scope.generalInfo);
		/*$http.get("/marketrate").success(function(response) {
			$scope.marketrate = response;
		});*/
		
		ngDialog.open({
			template: 'Reject',
			scope : $scope,
			className: 'ngdialog-theme-default'
		});
		
	}
	
	$scope.ApprovedUser = function(user) {
		
		console.log(user);
		
		$scope.generalInfo = user;
		console.log($scope.generalInfo);
		/*$http.get("/marketrate").success(function(response) {
			$scope.marketrate = response;
		});*/
		
		ngDialog.open({
			template: 'Approved',
			scope : $scope,
			className: 'ngdialog-theme-default'
		});
		
	}
	
	$scope.updatecreditLimit = function(agent){
		$scope.agent = agent;
		if(agent.paymentMethod == "Credit" || agent.paymentMethod == "Pre-Payment"){
			ngDialog.open({
				template: '/assets/html/admin/credit_limit_update.html',
				scope : $scope,
				className: 'ngdialog-theme-default'				
			});
		}
	}
	
	$scope.updateCreditLimit = function(agentId,creditlimit){
		console.log(agentId);
		console.log(creditlimit);
		$http.get('/creditLimitUpdate/'+agentId+'/'+creditlimit).success(function(response){
			 notificationService.success("Credit Limit Update Successfully");
			//$scope.pendingUsers.splice($scope.pendingUsers.indexOf($scope.generalInfo),1);
			//$scope.getData();
		});

	}
	
	
	$scope.approvePending = function() {
		console.log($scope.generalInfo.paymentMethod);
		$scope.userId = $scope.generalInfo.id;
		$scope.email = $scope.generalInfo.EmailAddr;
		$scope.agentCode = $scope.generalInfo.agentCode;
		$scope.loginId = $scope.generalInfo.loginId;
		
		if($scope.generalInfo.creditLimit == undefined){
			$scope.generalInfo.creditLimit = 0;
		}
			
		$scope.creditLimit = $scope.generalInfo.creditLimit;
		if($scope.generalInfo.creditLimit == 0){
			if($scope.generalInfo.paymentMethod == "Credit" || $scope.generalInfo.paymentMethod == "Pre-Payment"){
				ngDialog.open({
					template: '/assets/html/admin/credit_limit.html',
					scope : $scope,
					className: 'ngdialog-theme-default'				
				});
			}else{
				$http.get('/approveAgent/'+$scope.userId+'/'+$scope.email+'/'+$scope.agentCode+'/'+$scope.creditLimit).success(function(response){
					 notificationService.success("Approved Successfully");
					$scope.pendingUsers.splice($scope.pendingUsers.indexOf($scope.generalInfo),1);
					$scope.getData();
				});
			}
		}else{
			$http.get('/approveAgent/'+$scope.userId+'/'+$scope.email+'/'+$scope.agentCode+'/'+$scope.creditLimit).success(function(response){
				 notificationService.success("Approved Successfully");
				$scope.pendingUsers.splice($scope.pendingUsers.indexOf($scope.generalInfo),1);
				$scope.getData();
			});
		}
		
	}
	
	/*$scope.getCreditLimit = function(){
		console.log($scope.generalInfo.creditLimit);
		$scope.userId = $scope.generalInfo.id;
		$scope.email = $scope.generalInfo.EmailAddr;
		$scope.agentCode = $scope.generalInfo.agentCode;
		$scope.loginId = $scope.generalInfo.loginId;
		
		
	}*/
	
	
	
	$scope.ApprovReject = function(){
		console.log($scope.generalInfo);
		$scope.userId = $scope.generalInfo.id;
		$http.get('/rejectAgent/'+$scope.userId).success(function(response){
			console.log("Success")
			 notificationService.success("Reject Successfully");
		$scope.approvedUsers.splice($scope.approvedUsers.indexOf($scope.generalInfo),1);
		$scope.getData();
	});
	}
	
	$scope.pendingUser = function() {
		$scope.userId = $scope.generalInfo.id;
		$http.get('/pendingAgent/'+$scope.userId).success(function(response){
			 notificationService.success("Pending Successfully");
			$scope.rejectedUsers.splice($scope.rejectedUsers.indexOf($scope.generalInfo),1);
			$scope.getData();
		});
	}
	
}]);

angular.module('travel_portal').
controller("markupController",['$scope','notificationService','$filter','$rootScope','$http','ngDialog',function($scope,notificationService,$filter,$rootScope, $http,ngDialog){

	
	$(".form-validate").validate({
        errorPlacement: function(error, element){
            error.insertAfter(element);
        }
    });
	$(".form-validate1").validate({
        errorPlacement: function(error, element){
            error.insertAfter(element);
        }
    });
	$(".form-validate2").validate({
        errorPlacement: function(error, element){
            error.insertAfter(element);
        }
    });
	$(".form-validate3").validate({
        errorPlacement: function(error, element){
            error.insertAfter(element);
        }
    });
	
	$scope.supplier_check = [];
	$scope.agent_check = [];
	$scope.specificAgent_check = [];
	$scope.rate_check = [];
	$scope.specialrate_check = [];
	
	$http.get("/getSupplier").success(function(response){
		$scope.supplier=response;
		
		console.log($scope.supplier);
	});
	
	$scope.supplierClicked = function(e, supplierInfo) {

		if($(e.target).is(":checked")) {
			$scope.supplier_check.push(supplierInfo.code);
		} else {
			DeletesupplierItem(supplierInfo);
		}
		console.log("//////////");
		console.log($scope.supplier_check);
	}

	DeletesupplierItem = function(supplierInfo){
		angular.forEach($scope.supplier_check, function(obj, index){
			if ((supplierInfo.code == obj)) {
				$scope.supplier_check.splice(index, 1);
				return;
			};
		});

	}
	
	
	$scope.suppliercheckAll = function () {
        if ($scope.selectedAll) {
            $scope.selectedAll = false;
            angular.forEach($scope.supplier, function (supplierInfo) {
            	supplierInfo.isSelected = $scope.selectedAll;
            });
            $scope.supplier_check = [];
        } else {
        	
            $scope.selectedAll = true;
            $scope.supplier_check = [];
            angular.forEach($scope.supplier, function (supplierInfo,index) {
            	supplierInfo.isSelected = $scope.selectedAll;
                    	$scope.supplier_check.push($scope.supplier[index].code);
            });
        }
       
        	console.log($scope.supplier_check);
    };
	
	
	$http.get("/agentCountries").success(function(response) {
		$scope.countries = response;
		console.log($scope.countries);
	}); 
	
	$scope.init = function(){
		
		  $scope.agentselectedAll = false;
		  $scope.specificAgentselectedAll = false;
		   $scope.agent_check = [];
		   $scope.specificAgent_check = [];
		$http.get("/getAgent").success(function(response){
			$scope.agent=response;
			console.log("+++++++++++++++=");
			console.log($scope.agent);
			
		});
	}
	

	$scope.onCountryChange = function(){
		console.log($scope.countryCode);
		   $scope.agentselectedAll = false;
		   $scope.specificAgentselectedAll = false;
		   $scope.agent_check = [];
		   $scope.specificAgent_check = [];
		$http.get("/getAgentCountry/"+$scope.countryCode).success(function(response){
			$scope.agent=response;
			console.log("+++++++++++++++=");
			console.log($scope.agent);
			
		});
	
	}
	
	
	$scope.agentClicked = function(e, agentInfo) {

		if($(e.target).is(":checked")) {
			$scope.agent_check.push(agentInfo.agentCode);
		} else {
			DeleteagentItem(agentInfo);
		}
		console.log("//////////");
		console.log($scope.agent_check);
	}

	DeleteagentItem = function(agentInfo){
		angular.forEach($scope.agent_check, function(obj, index){
			if ((agentInfo.agentCode == obj)) {
				$scope.agent_check.splice(index, 1);
				return;
			};
		});

	}
	
	$scope.agentcheckAll = function () {
        if ($scope.agentselectedAll) {
            $scope.agentselectedAll = false;
            angular.forEach($scope.agent, function (agentInfo1) {
            angular.forEach(agentInfo1.agentDatavm, function (agentInfo) {
            	agentInfo.isSelected = $scope.agentselectedAll;
            });
            });
            $scope.agent_check = [];
        } else {
        	
            $scope.agentselectedAll = true;
            $scope.agent_check = [];
       
            angular.forEach($scope.agent, function (agentInfo1) {
            angular.forEach(agentInfo1.agentDatavm, function (agentInfo, index) {
            	agentInfo.isSelected = $scope.agentselectedAll;
            	console.log(agentInfo);
                    	$scope.agent_check.push(agentInfo1.agentDatavm[index].agentCode);
                    	
                    	
            	});
            });
        }
       
        	console.log($scope.agent_check);
    };
    
    $scope.saveBatchMarkup = function(){
    	$scope.batchMarkup.agent = $scope.agent_check;
    	$scope.batchMarkup.supplier = $scope.supplier_check;
    	if($scope.batchMarkup.selected == "1"){
    		delete $scope.batchMarkup.flat;
    	}else{
    		delete $scope.batchMarkup.percent;
    	}
    		
    	console.log($scope.batchMarkup);
   
    	$http.post('/savebatchMarkup',$scope.batchMarkup).success(function(data){
    		console.log("Success");
    		 notificationService.success("BatchMarkup Save Successfully");
    	}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
    }
	
	
    $scope.specificAgentClicked = function(e, specificAgentInfo) {

		if($(e.target).is(":checked")) {
			$scope.specificAgent_check.push(specificAgentInfo.agentCode);
		} else {
			DeletespecificAgentItem(specificAgentInfo);
		}
		console.log("//////////");
		console.log($scope.specificAgent_check);
	}

	DeletespecificAgentItem = function(specificAgentInfo){
		angular.forEach($scope.specificAgent_check, function(obj, index){
			if ((specificAgentInfo.agentCode == obj)) {
				$scope.specificAgent_check.splice(index, 1);
				return;
			};
		});

	}
	
	$scope.specificAgentcheckAll = function () {
        if ($scope.specificAgentselectedAll) {
            $scope.specificAgentselectedAll = false;
            angular.forEach($scope.agent, function (specificAgentInfo1) {
            angular.forEach(specificAgentInfo1.agentDatavm, function (specificAgentInfo) {
            	specificAgentInfo.isSelected = $scope.specificAgentselectedAll;
            });
            });
            $scope.specificAgent_check = [];
        } else {
        	
            $scope.specificAgentselectedAll = true;
            $scope.specificAgent_check = [];
            angular.forEach($scope.agent, function (specificAgentInfo1) {
            angular.forEach(specificAgentInfo1.agentDatavm, function (specificAgentInfo, index) {
            	specificAgentInfo.isSelected = $scope.specificAgentselectedAll;
            	console.log(specificAgentInfo);
                    	$scope.specificAgent_check.push(specificAgentInfo1.agentDatavm[index].agentCode);
                    	
                    	
            });
            });
        }
       
        	console.log($scope.specificAgent_check);
    };
    
    $scope.showRate = function(code){
    	console.log(code);
    	$scope.showAllRate = true;
    	$http.get("/getSupplierRate/"+code).success(function(response){
    		$scope.supplierRate = response;
    		console.log(response);
    	});
    }
    
    
    $scope.rateClicked = function(e, rate) {

		if($(e.target).is(":checked")) {
			$scope.rate_check.push(rate.id);
		} else {
			DeleterateItem(rate);
		}
		console.log("//////////");
		console.log($scope.rate_check);
	}

	DeleterateItem = function(rate){
		angular.forEach($scope.rate_check, function(obj, index){
			if ((rate.id == obj)) {
				$scope.rate_check.splice(index, 1);
				return;
			};
		});

	}
	
	$scope.ratecheckAll = function () {
        if ($scope.rateselectedAll) {
            $scope.rateselectedAll = false;
            angular.forEach($scope.supplierRate, function (rate) {
            	rate.isSelected = $scope.rateselectedAll;
            });
            $scope.rate_check = [];
        } else {
        	
            $scope.rateselectedAll = true;
            $scope.rate_check = [];
            $scope.i = 0;
            angular.forEach($scope.supplierRate, function (rate, index) {
            	rate.isSelected = $scope.rateselectedAll;
            	console.log(rate);
                    	$scope.rate_check.push($scope.supplierRate[index].id);
                    	
                    	
            });
        }
       
        	console.log($scope.rate_check);
    };
    
    
    
    
  /* $scope.specialrateClicked = function(e, specialrate) {

		if($(e.target).is(":checked")) {
			$scope.specialrate_check.push(specialrate.id);
		} else {
			DeletespecialrateItem(specialrate);
		}
		console.log("//////////");
		console.log($scope.specialrate_check);
	}

	DeletespecialrateItem = function(specialrate){
		angular.forEach($scope.specialrate_check, function(obj, index){
			if ((specialrate.id == obj)) {
				$scope.specialrate_check.splice(index, 1);
				return;
			};
		});

	}
	
	 	$scope.specialratecheckAll = function () {
        if ($scope.specialrateselectedAll) {
            $scope.specialrateselectedAll = false;
            angular.forEach($scope.supplierSpecialRate, function (specialrate) {
            	specialrate.isSelected = $scope.specialrateselectedAll;
            });
            $scope.specialrate_check = [];
        } else {
        	
            $scope.specialrateselectedAll = true;
            $scope.specialrate_check = [];
            $scope.i = 0;
            angular.forEach($scope.supplierSpecialRate, function (specialrate, index) {
            	specialrate.isSelected = $scope.specialrateselectedAll;
            	console.log(specialrate);
                    	$scope.specialrate_check.push($scope.supplierSpecialRate[index].id);
                    	
                    	
            });
        }
       
        	console.log($scope.specialrate_check);
    };
    */
    
    
    
    $scope.saveSpecificMarkup = function(){
    	$scope.specificMarkup.rateSelected = $scope.rate_check;
    	$scope.specificMarkup.agentSpecific = $scope.specificAgent_check;
    	//$scope.specificMarkup.specialRate =$scope.specialrate_check;
    	if($scope.specificMarkup.specificSelected == "1"){
    		delete $scope.specificMarkup.specificFlat;
    	}else{
    		delete $scope.specificMarkup.specificPercent;
    	}
    		
    	console.log($scope.specificMarkup);
    	
    	$http.post('/savespecificMarkup',$scope.specificMarkup).success(function(data){
    		console.log("Success");
    		 notificationService.success("SpecificMarkUp Save Successfully");
    	}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
    }
    
    
    $scope.batchAgent = function(){
    	
    }
    $scope.onSupplierChoose = function(){
    	console.log($scope.code);
    	$http.get("/getSupplerWiseRate/"+$scope.code).success(function(response){
    		$scope.SupplerWiseRate = response;
    		console.log(response);
    	});
    	
    	$http.get("/getSupplerWiseSpecificRate/"+$scope.code).success(function(response){
    		console.log(response);
    		$scope.SupplerWiseSpecificRate = response;
    		/* angular.forEach($scope.SupplerWiseSpecificRate, function(obj, index){
    		 
    			 $scope.SupplerWiseSpecificRate[index].rateSelected.fromDate = $filter('date')(response[index].rateSelected.fromDate, "dd/MM/yyyy");
    			 $scope.SupplerWiseSpecificRate[index].rateSelected.toDate = $filter('date')(response[index].rateSelected.toDate, "dd/MM/yyyy");
    			 return;
    		 });
    		*/
    		console.log(response);
    	});
    }
    
    $scope.selectedSpecificData = {};
    $scope.updateSpecificId = function(supplerWiseSpecific){
    	console.log(supplerWiseSpecific);
    	if(supplerWiseSpecific.selected == "1"){
    		supplerWiseSpecific.flat = null;
    	}else{
    		supplerWiseSpecific.percent =null;
    	}
    	console.log(supplerWiseSpecific);
    	$scope.selectedSpecificData.specificMarkupId = supplerWiseSpecific.specificMarkupId;
    	$scope.selectedSpecificData.specificFlat = supplerWiseSpecific.flat;
    	$scope.selectedSpecificData.specificPercent = supplerWiseSpecific.percent;
    	$scope.selectedSpecificData.specificSelected = supplerWiseSpecific.selected;
    	console.log($scope.selectedSpecificData);
    	$http.post('/UpdateSpecificMarkup',$scope.selectedSpecificData).success(function(data){
    		console.log("Success");
    		 notificationService.success("Update Successfully");
    	}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
    }
    
    $scope.selectedData = {};
    
    $scope.updateAgentId = function(supplerWise){
    	console.log(supplerWise);
    	if(supplerWise.selected == "1"){
    		supplerWise.flat = null;
    	}else{
    		supplerWise.percent =null;
    	}
    	console.log(supplerWise);
    	$scope.selectedData.batchMarkupId = supplerWise.batchMarkupId;
    	$scope.selectedData.flat = supplerWise.flat;
    	$scope.selectedData.percent = supplerWise.percent;
    	$scope.selectedData.selected = supplerWise.selected;
    	console.log($scope.selectedData);
    	$http.post('/UpdateBatchMarkup', $scope.selectedData).success(function(data){
    		console.log("Success");
    		 notificationService.success("Update Successfully");
    	}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
    	
    }
    
    $scope.saveBreakfast = function(breakfast){
    	console.log(breakfast);
    	$http.get("/saveBreakfastRate/"+breakfast).success(function(response){
    		notificationService.success("Save Successfully");
    		console.log(response);
    		
    	});
    }
    
}]);

angular.module('travel_portal').
controller("confirmbookingController",['$scope','notificationService','$filter','$rootScope','$http','ngDialog',function($scope,notificationService,$filter,$rootScope, $http,ngDialog){
		
	$scope.pageNumber;
	$scope.pageSize;
	$scope.fromData = "1";
	$scope.toDate="1";
	$scope.agentNm = "1";
	var currentPage = 1;
	var totalPages;
	$scope.flag = 0;
		
	
	console.log(supplierCode);
	$http.get("/getbookingInfo/"+supplierCode+"/"+currentPage+"/"+$scope.fromData+"/"+$scope.toDate+"/"+$scope.agentNm).success(function(response){
		
		console.log(response);
		totalPages = response.totalPages;
		currentPage = response.currentPage;
		$scope.pageNumber = response.currentPage;
		$scope.pageSize = response.totalPages;
		$scope.bookinginfo = response.results;
		if(totalPages == 0) {
			$scope.pageNumber = 0;
		}
		
	});
	
	$scope.showBookingDataDateWise = function(selectDate){
		console.log(selectDate);
		if(selectDate.agentNm == undefined || selectDate.agentNm == ""){
			$scope.agentNm = "1";
		}else{
			$scope.agentNm = selectDate.agentNm;
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
				
		
		$http.get("/getbookingInfo/"+supplierCode+"/"+currentPage+"/"+$scope.fromData+"/"+$scope.toDate+"/"+$scope.agentNm).success(function(response){
		
			console.log(response);
			totalPages = response.totalPages;
			currentPage = response.currentPage;
			$scope.pageNumber = response.currentPage;
			$scope.pageSize = response.totalPages;
			$scope.bookinginfo = response.results;
			if(totalPages == 0) {
				$scope.pageNumber = 0;
			}
		});
	}
	
	$scope.showDetails = function(book){
		console.log(book);
		$scope.bookinfo = book;
		ngDialog.open({
			template: '/assets/html/booking_process/confirm_booking_details.html',
			scope : $scope,
			//controller:'hoteProfileController',
			className: 'ngdialog-theme-default'
		});
	}
	
	$scope.rateDatewise = [];
	$scope.showdateWiseView = function(bookId){
		console.log(bookId);
		$scope.rateDatewise = [];
		$http.get("/getbookDateWise/"+bookId).success(function(response){
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
	
	$scope.onNext = function() {
		if(currentPage < totalPages) {
			currentPage++;
			$scope.searchBooking(currentPage);
		}
	};
	$scope.onPrev = function() {
		if(currentPage > 1) {
			currentPage--;
			$scope.searchBooking(currentPage);
		}
	};
	
	$scope.searchBooking = function(page) {
		currentPage = page;
		/*if(angular.isUndefined($scope.title) || $scope.title=="") {
			console.log('inside function');
			$scope.title = " ";
		}*/
		
		currentPage = page;
		console.log(currentPage);
		$http.get("/getbookingInfo/"+supplierCode+"/"+currentPage+"/"+$scope.fromData+"/"+$scope.toDate+"/"+$scope.agentNm).success(function(response){
			
			console.log(response);
			totalPages = response.totalPages;
			currentPage = response.currentPage;
			$scope.pageNumber = response.currentPage;
			$scope.pageSize = response.totalPages;
			$scope.bookinginfo = response.results;
			if(totalPages == 0) {
				$scope.pageNumber = 0;
			}
			
		});
	};
	
}]);


angular.module('travel_portal').
controller("onrequestController",['$scope','notificationService','$filter','$rootScope','$http','ngDialog',function($scope,notificationService,$filter,$rootScope, $http,ngDialog){
		
	
	$scope.pageNumber;
	$scope.pageSize;
	$scope.fromData = "1";
	$scope.toDate="1";
	$scope.agentNm = "1";
	var currentPage = 1;
	var totalPages;
	$scope.flag = 0;
		
	
	console.log(supplierCode);
	$http.get("/getonrequestInfo/"+supplierCode+"/"+currentPage+"/"+$scope.fromData+"/"+$scope.toDate+"/"+$scope.agentNm).success(function(response){
		
		console.log(response);
		totalPages = response.totalPages;
		currentPage = response.currentPage;
		$scope.pageNumber = response.currentPage;
		$scope.pageSize = response.totalPages;
		$scope.onrequestinfo = response.results;
		if(totalPages == 0) {
			$scope.pageNumber = 0;
		}
		
	});
	

	$scope.onNext = function() {
		if(currentPage < totalPages) {
			currentPage++;
			$scope.searchonrequest(currentPage);
		}
	};
	$scope.onPrev = function() {
		if(currentPage > 1) {
			currentPage--;
			$scope.searchonrequest(currentPage);
		}
	};
	
	$scope.searchonrequest = function(page) {
		currentPage = page;
		/*if(angular.isUndefined($scope.title) || $scope.title=="") {
			console.log('inside function');
			$scope.title = " ";
		}*/
		
		currentPage = page;
		console.log(currentPage);
		$http.get("/getonrequestInfo/"+supplierCode+"/"+currentPage+"/"+$scope.fromData+"/"+$scope.toDate+"/"+$scope.agentNm).success(function(response){
			
			console.log(response);
			totalPages = response.totalPages;
			currentPage = response.currentPage;
			$scope.pageNumber = response.currentPage;
			$scope.pageSize = response.totalPages;
			$scope.onrequestinfo = response.results;
			if(totalPages == 0) {
				$scope.pageNumber = 0;
			}
			
		});
	};
	
	$scope.showoOnRequestDataDateWise = function(selectDate){
		console.log(selectDate);
		if(selectDate.agentNm == undefined || selectDate.agentNm == ""){
			$scope.agentNm = "1";
		}else{
			$scope.agentNm = selectDate.agentNm;
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
				
		
		$http.get("/getonrequestInfo/"+supplierCode+"/"+currentPage+"/"+$scope.fromData+"/"+$scope.toDate+"/"+$scope.agentNm).success(function(response){
		
			console.log(response);
			totalPages = response.totalPages;
			currentPage = response.currentPage;
			$scope.pageNumber = response.currentPage;
			$scope.pageSize = response.totalPages;
			$scope.onrequestinfo = response.results;
			if(totalPages == 0) {
				$scope.pageNumber = 0;
			}
		});
	}
	
	$scope.rateDatewise = [];
	$scope.showdateWiseView = function(onrequest){
		console.log(onrequest);
		$scope.rateDatewise = [];
		$http.get("/getbookDateWise/"+onrequest).success(function(response){
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
	
	
	
	$scope.showDetails = function(request){
		console.log(request);
		$scope.requestinfo = request;
		ngDialog.open({
			template: '/assets/html/booking_process/on_request_details.html',
			scope : $scope,
			//controller:'hoteProfileController',
			className: 'ngdialog-theme-default'
		});
	}
	
	$scope.rejectBooking = function(id){
		
		console.log(id);
		$http.get("/getRejectBooking/"+id).success(function(response){
			
			notificationService.success("Reject Booking");
			ngDialog.close();
			$http.get("/getonrequestInfo/"+supplierCode+"/"+currentPage+"/"+$scope.fromData+"/"+$scope.toDate+"/"+$scope.agentNm).success(function(response){
				
				console.log(response);
				totalPages = response.totalPages;
				currentPage = response.currentPage;
				$scope.pageNumber = response.currentPage;
				$scope.pageSize = response.totalPages;
				$scope.onrequestinfo = response.results;
				if(totalPages == 0) {
					$scope.pageNumber = 0;
				}
				
			});
			
			//$scope.pendingUsers.splice($scope.pendingUsers.indexOf($scope.generalInfo),1);
		});
			
	}
	$scope.confBooking = function(id){
		
		console.log(id);
		$http.get("/getconfBooking/"+id).success(function(response){
			
			notificationService.success("Confirmed Booking");
			ngDialog.close();
			$http.get("/getonrequestInfo/"+supplierCode+"/"+currentPage+"/"+$scope.fromData+"/"+$scope.toDate+"/"+$scope.agentNm).success(function(response){
				
				console.log(response);
				totalPages = response.totalPages;
				currentPage = response.currentPage;
				$scope.pageNumber = response.currentPage;
				$scope.pageSize = response.totalPages;
				$scope.onrequestinfo = response.results;
				if(totalPages == 0) {
					$scope.pageNumber = 0;
				}
				
			});
		});
	}
	
}]);	

angular.module('travel_portal').
controller("confirmbookingController",['$scope','notificationService','$filter','$rootScope','$http','ngDialog',function($scope,notificationService,$filter,$rootScope, $http,ngDialog){
		
	$scope.pageNumber;
	$scope.pageSize;
	$scope.fromData = "1";
	$scope.toDate="1";
	$scope.agentNm = "1";
	var currentPage = 1;
	var totalPages;
	$scope.flag = 0;
		
	
	console.log(supplierCode);
	$http.get("/getbookingInfo/"+supplierCode+"/"+currentPage+"/"+$scope.fromData+"/"+$scope.toDate+"/"+$scope.agentNm).success(function(response){
		
		console.log(response);
		totalPages = response.totalPages;
		currentPage = response.currentPage;
		$scope.pageNumber = response.currentPage;
		$scope.pageSize = response.totalPages;
		$scope.bookinginfo = response.results;
		if(totalPages == 0) {
			$scope.pageNumber = 0;
		}
		
	});
	
	$scope.showBookingDataDateWise = function(selectDate){
		console.log(selectDate);
		if(selectDate.agentNm == undefined || selectDate.agentNm == ""){
			$scope.agentNm = "1";
		}else{
			$scope.agentNm = selectDate.agentNm;
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
				
		
		$http.get("/getbookingInfo/"+supplierCode+"/"+currentPage+"/"+$scope.fromData+"/"+$scope.toDate+"/"+$scope.agentNm).success(function(response){
		
			console.log(response);
			totalPages = response.totalPages;
			currentPage = response.currentPage;
			$scope.pageNumber = response.currentPage;
			$scope.pageSize = response.totalPages;
			$scope.bookinginfo = response.results;
			if(totalPages == 0) {
				$scope.pageNumber = 0;
			}
		});
	}
	
	$scope.showDetails = function(book){
		console.log(book);
		$scope.bookinfo = book;
		ngDialog.open({
			template: '/assets/html/booking_process/confirm_booking_details.html',
			scope : $scope,
			//controller:'hoteProfileController',
			className: 'ngdialog-theme-default'
		});
	}
	
	$scope.rateDatewise = [];
	$scope.showdateWiseView = function(bookId){
		console.log(bookId);
		$scope.rateDatewise = [];
		$http.get("/getbookDateWise/"+bookId).success(function(response){
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
	
	$scope.onNext = function() {
		if(currentPage < totalPages) {
			currentPage++;
			$scope.searchBooking(currentPage);
		}
	};
	$scope.onPrev = function() {
		if(currentPage > 1) {
			currentPage--;
			$scope.searchBooking(currentPage);
		}
	};
	
	$scope.searchBooking = function(page) {
		currentPage = page;
		/*if(angular.isUndefined($scope.title) || $scope.title=="") {
			console.log('inside function');
			$scope.title = " ";
		}*/
		
		currentPage = page;
		console.log(currentPage);
		$http.get("/getbookingInfo/"+supplierCode+"/"+currentPage+"/"+$scope.fromData+"/"+$scope.toDate+"/"+$scope.agentNm).success(function(response){
			
			console.log(response);
			totalPages = response.totalPages;
			currentPage = response.currentPage;
			$scope.pageNumber = response.currentPage;
			$scope.pageSize = response.totalPages;
			$scope.bookinginfo = response.results;
			if(totalPages == 0) {
				$scope.pageNumber = 0;
			}
			
		});
	};
	
}]);


angular.module('travel_portal').
controller("rejectController",['$scope','notificationService','$filter','$rootScope','$http','ngDialog',function($scope,notificationService,$filter,$rootScope, $http,ngDialog){
		
	
	$scope.pageNumber;
	$scope.pageSize;
	$scope.fromData = "1";
	$scope.toDate="1";
	$scope.agentNm = "1";
	var currentPage = 1;
	var totalPages;
	$scope.flag = 0;
		
	
	console.log(supplierCode);
	$http.get("/getrejectInfo/"+supplierCode+"/"+currentPage+"/"+$scope.fromData+"/"+$scope.toDate+"/"+$scope.agentNm).success(function(response){
		
		console.log(response);
		totalPages = response.totalPages;
		currentPage = response.currentPage;
		$scope.pageNumber = response.currentPage;
		$scope.pageSize = response.totalPages;
		$scope.rejectinfo = response.results;
		if(totalPages == 0) {
			$scope.pageNumber = 0;
		}
		
	});
	

	$scope.onNext = function() {
		if(currentPage < totalPages) {
			currentPage++;
			$scope.searchreject(currentPage);
		}
	};
	$scope.onPrev = function() {
		if(currentPage > 1) {
			currentPage--;
			$scope.searchreject(currentPage);
		}
	};
	
	$scope.searchreject = function(page) {
		currentPage = page;
		/*if(angular.isUndefined($scope.title) || $scope.title=="") {
			console.log('inside function');
			$scope.title = " ";
		}*/
		
		currentPage = page;
		console.log(currentPage);
		$http.get("/getrejectInfo/"+supplierCode+"/"+currentPage+"/"+$scope.fromData+"/"+$scope.toDate+"/"+$scope.agentNm).success(function(response){
			
			console.log(response);
			totalPages = response.totalPages;
			currentPage = response.currentPage;
			$scope.pageNumber = response.currentPage;
			$scope.pageSize = response.totalPages;
			$scope.rejectinfo = response.results;
			if(totalPages == 0) {
				$scope.pageNumber = 0;
			}
			
		});
	};
	
	$scope.showRejectDataDateWise = function(selectDate){
		console.log(selectDate);
		if(selectDate.agentNm == undefined || selectDate.agentNm == ""){
			$scope.agentNm = "1";
		}else{
			$scope.agentNm = selectDate.agentNm;
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
				
		
		$http.get("/getrejectInfo/"+supplierCode+"/"+currentPage+"/"+$scope.fromData+"/"+$scope.toDate+"/"+$scope.agentNm).success(function(response){
		
			console.log(response);
			totalPages = response.totalPages;
			currentPage = response.currentPage;
			$scope.pageNumber = response.currentPage;
			$scope.pageSize = response.totalPages;
			$scope.rejectinfo = response.results;
			if(totalPages == 0) {
				$scope.pageNumber = 0;
			}
		});
	}
	
	$scope.rateDatewise = [];
	$scope.showdateWiseView = function(reject){
		console.log(reject);
		$scope.rateDatewise = [];
		$http.get("/getbookDateWise/"+reject).success(function(response){
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
	
	
	
	$scope.showDetails = function(reject){
		console.log(reject);
		$scope.rejectedinfo = reject;
		ngDialog.open({
			template: '/assets/html/booking_process/reject_booking_details.html',
			scope : $scope,
			//controller:'hoteProfileController',
			className: 'ngdialog-theme-default'
		});
	}
	
	
	
	
}]);	



angular.module('travel_portal').
controller("cancelController",['$scope','notificationService','$filter','$rootScope','$http','ngDialog',function($scope,notificationService,$filter,$rootScope, $http,ngDialog){
		
	
	$scope.pageNumber;
	$scope.pageSize;
	$scope.fromData = "1";
	$scope.toDate="1";
	$scope.agentNm = "1";
	var currentPage = 1;
	var totalPages;
	$scope.flag = 0;
		
	
	console.log(supplierCode);
	$http.get("/getcancelInfo/"+supplierCode+"/"+currentPage+"/"+$scope.fromData+"/"+$scope.toDate+"/"+$scope.agentNm).success(function(response){
		
		console.log(response);
		totalPages = response.totalPages;
		currentPage = response.currentPage;
		$scope.pageNumber = response.currentPage;
		$scope.pageSize = response.totalPages;
		$scope.cancelinfo = response.results;
		if(totalPages == 0) {
			$scope.pageNumber = 0;
		}
		
	});
	

	$scope.onNext = function() {
		if(currentPage < totalPages) {
			currentPage++;
			$scope.searchcancel(currentPage);
		}
	};
	$scope.onPrev = function() {
		if(currentPage > 1) {
			currentPage--;
			$scope.searchcancel(currentPage);
		}
	};
	
	$scope.searchcancel = function(page) {
		currentPage = page;
		/*if(angular.isUndefined($scope.title) || $scope.title=="") {
			console.log('inside function');
			$scope.title = " ";
		}*/
		
		currentPage = page;
		console.log(currentPage);
		$http.get("/getcancelInfo/"+supplierCode+"/"+currentPage+"/"+$scope.fromData+"/"+$scope.toDate+"/"+$scope.agentNm).success(function(response){
			
			console.log(response);
			totalPages = response.totalPages;
			currentPage = response.currentPage;
			$scope.pageNumber = response.currentPage;
			$scope.pageSize = response.totalPages;
			$scope.cancelinfo = response.results;
			if(totalPages == 0) {
				$scope.pageNumber = 0;
			}
			
		});
	};
	
	$scope.showCancelDataDateWise = function(selectDate){
		console.log(selectDate);
		if(selectDate.agentNm == undefined || selectDate.agentNm == ""){
			$scope.agentNm = "1";
		}else{
			$scope.agentNm = selectDate.agentNm;
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
				
		
		$http.get("/getcancelInfo/"+supplierCode+"/"+currentPage+"/"+$scope.fromData+"/"+$scope.toDate+"/"+$scope.agentNm).success(function(response){
		
			console.log(response);
			totalPages = response.totalPages;
			currentPage = response.currentPage;
			$scope.pageNumber = response.currentPage;
			$scope.pageSize = response.totalPages;
			$scope.cancelinfo = response.results;
			if(totalPages == 0) {
				$scope.pageNumber = 0;
			}
		});
	}
	
	$scope.rateDatewise = [];
	$scope.showdateWiseView = function(cancel){
		console.log(cancel);
		$scope.rateDatewise = [];
		$http.get("/getbookDateWise/"+cancel).success(function(response){
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
	
	
	
	$scope.showDetails = function(cancel){
		console.log(cancel);
		$scope.canceledinfo = cancel;
		ngDialog.open({
			template: '/assets/html/booking_process/cancel_details.html',
			scope : $scope,
			//controller:'hoteProfileController',
			className: 'ngdialog-theme-default'
		});
	}
	
	
	
	
}]);	

angular.module('travel_portal').
controller("manageBookingController",['$scope','notificationService','$upload','$filter','$rootScope','$http','ngDialog',function($scope,notificationService,$upload,$filter,$rootScope, $http,ngDialog){

	$scope.pageNumber;
	$scope.pageSize;
	$scope.fromData = "1";
	$scope.toDate="1";
	$scope.status = "1";
	var currentPage = 1;
	var totalPages;
	$scope.flag = 0;
	$scope.showtable = 0; 
	
	$scope.findAgentList = function(agentCode){
		
		$http.get("/getAllAgentInfo/"+agentCode).success(function(response){
			console.log(response);
			totalPages = response.totalPages;
			currentPage = response.currentPage;
			$scope.pageNumber = response.currentPage;
			$scope.pageSize = response.totalPages;
			$scope.agentBook = response.results;
			if(response.results.length != 0){
				$scope.showtable = 1;
			}
			console.log(response.results.length);
			console.log($scope.showtable);
			if(totalPages == 0) {
				$scope.pageNumber = 0;
			}
		});
		
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
	
	$scope.paymentWise = function(paymentStatus, agentCode){
		
		$http.get("/getpaymentWise/"+paymentStatus+"/"+agentCode).success(function(response1){
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
	
	
	$scope.showtableBooking = function(checkIn,checkOut,guest,status,bookingId,agentCode){
		if(guest=="")
		{
			guest="undefined";
		}
		if(checkIn=="")
		{
			checkIn="undefined";
		}
		if(checkOut=="")
		{
			checkOut="undefined";
		}
		if(bookingId=="" || bookingId==undefined)
		{
			bookingId = 0;
		}
		
		console.log(guest);
		console.log(status);
		console.log(checkIn);
		console.log(checkOut);
		console.log(bookingId);
		console.log(agentCode);
		
		$http.get("/getagentInfobyAll/"+currentPage+"/"+checkIn+"/"+checkOut+"/"+guest+"/"+status+"/"+bookingId+"/"+agentCode).success(function(response1){
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
	
	/*$scope.searchagent = function(page) {
		currentPage = page;
		
		currentPage = page;
		console.log(currentPage);
		$http.get("/getagentInfobyAll/"+currentPage+"/"+$scope.fromData+"/"+$scope.toDate+"/"+$scope.status).success(function(response){
			
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
	};*/
	
	
	$scope.bookingPayment = function(bookingId, payment, total){
		console.log(bookingId);
		console.log(payment);
		$http.get("/getBookingPaymentInfo/"+bookingId+"/"+payment+"/"+total).success(function(response){
			console.log("OK,,OK");
		});
		
	}
	$scope.savedoc = {};
	$scope.selectBookingDoc = function($files,bookingId)
	   {		
			files = $files[0]; 		
			console.log(bookingId);
			$scope.savedoc.bookingId = bookingId;
			 $scope.upload = $upload.upload({
		           url: '/saveBookingfiles', 
		           method:'post',
		           data:$scope.savedoc,
		           fileFormDataName: 'file1',
		           file:files,
		          
		   }).progress(function(evt) {
		           console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
		   }).success(function(data, status, headers, config) {
			   
			  console.log("Ok");
		   }); 
			
			 $scope.buttonChange = "true";
			
	   }
	   
	$scope.buttonChange = "true";
	$scope.showFileSelect = function(){
		$scope.buttonChange = "false";
	}	
	
}]);

angular.module('travel_portal').
controller("manageRateController",['$scope','notificationService','$filter','$rootScope','$http','ngDialog',function($scope,notificationService,$filter,$rootScope, $http,ngDialog){
		$scope.rateMetaDate;
		$scope.supplierCode;
		
		$http.get("/getSupplier").success(function(response){
			console.log(response);
			$scope.hotelName = response;
		});
		
		$http.get("/getPendingRate").success(function(response){
			console.log(response);
			$scope.rateMetaDate = response;
		});
		
		
		$scope.showSpplier = function(supplierCode){
			console.log(supplierCode);
			$scope.supplierCode = supplierCode;
			$http.get("/getSupplierWisePendingRate/"+supplierCode).success(function(response){
				console.log(response);
				$scope.rateMetaDate = response;
			});
		}
		
		$scope.approvedUser = function(rateId){
			console.log(rateId);
			console.log($scope.supplierCode);
			
			$http.get("/getAppPendingRate/"+rateId).success(function(response){
				console.log("Rate App");
				
				if($scope.supplierCode != undefined){
					$http.get("/getSupplierWisePendingRate/"+$scope.supplierCode).success(function(response){
						console.log(response);
						$scope.rateMetaDate = response;
					});
				}else{
					$http.get("/getPendingRate").success(function(response){
						console.log(response);
						$scope.rateMetaDate = response;
					});
				}
				notificationService.success("Rate Approved");
				notificationService.success("add Markup on markup page");
				
			});
		}
}]);

angular.module('travel_portal').
controller("manageExchangeRateController",['$scope','notificationService','$filter','$rootScope','$http','ngDialog',function($scope,notificationService,$filter,$rootScope, $http,ngDialog){
	
	$http.get("/exchangeCurrency").success(function(response) {
		$scope.currency = response;
		console.log($scope.currency);
		
	});
	
	
	$scope.saveCurrencyRate = function(){
		
		console.log($scope.currencyRate);
		$http.post('/saveCurrencyExchangeRate',$scope.currencyRate).success(function(data){
			console.log('success');
			notificationService.success("saveCurrency");
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
			notificationService.error("Please Enter Required Fields");
		});
		
		
	}
	
	$scope.showCurrency = function(selectedCurr){
		
		$http.get("/getExchangeRate/"+selectedCurr).success(function(response){
			console.log("OK..........");
			console.log(response);
			$scope.currencyRate.curr_THB = response.curr_THB;
			$scope.currencyRate.curr_SGD = response.curr_SGD;
			$scope.currencyRate.curr_MYR = response.curr_MYR;
			$scope.currencyRate.curr_INR = response.curr_INR;
		});
		
	}
	console.log("Hiiiii");
}]);
