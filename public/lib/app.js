'use strict';
/*angular.module('travel_portal',['ngRoute','rcWizard', 'rcForm', 'rcDisabledBootstrap']).config(function ($routeProvider) {*/
angular.module('travel_portal',['ngRoute','ngDialog']).config(function ($routeProvider) {
	$routeProvider
	.when('/', {
		templateUrl: '/assets/html/hotel_profile/general_info.html',
		controller: 'hoteProfileController'
	})
	.when('/profile0', {
		templateUrl: '/assets/html/hotel_profile/general_info.html',
		controller: 'hoteProfileController'
	})
	.when('/profile1', {
		templateUrl: '/assets/html/hotel_profile/hotel_description.html',
		controller: 'hoteProfileController'
	})
	.when('/profile2', {
		templateUrl: '/assets/html/hotel_profile/internal_information.html',
		controller: 'hoteProfileController'
	})
	.when('/profile3', {
		templateUrl: '/assets/html/hotel_profile/contact_info.html',
		controller: 'hoteProfileController'
	})
	.when('/profile4', {
		templateUrl: '/assets/html/hotel_profile/hotel_communication.html',
		controller: 'hoteProfileController'
	})
	.when('/profile5', {
		templateUrl: '/assets/html/hotel_profile/billing_information.html',
		controller: 'hoteProfileController'
	})
	.when('/profile6', {
		templateUrl: '/assets/html/hotel_profile/addi_information.html',
		controller: 'hoteProfileController'
	})
	.when('/profile7', {
		templateUrl: '/assets/html/hotel_profile/meal_plan.html',
		controller: 'hoteProfileController'
	})
	.when('/profile8', {
		templateUrl: '/assets/html/hotel_profile/hotel_amenities.html',
		controller: 'hoteProfileController'
	})
	.when('/profile9', {
		templateUrl: '/assets/html/hotel_profile/business_amenities.html',
		controller: 'hoteProfileController'
	})
	.when('/profile10', {
		templateUrl: '/assets/html/hotel_profile/leisure_sport.html',
		controller: 'hoteProfileController'
	})
	.when('/profile11', {
		templateUrl: '/assets/html/hotel_profile/area_attraction.html',
		controller: 'hoteProfileController'
	})
	.when('/profile12', {
		templateUrl: '/assets/html/hotel_profile/transportation_directions.html',
		controller: 'hoteProfileController'
	})
	.when('/profile13', {
		templateUrl: '/assets/html/hotel_profile/health_and_safety.html',
		controller: 'hoteProfileController'
	})
	.when('/addHotelRoomType', {
		templateUrl: '/assets/html/room_profile/hotel_room_info.html',
		controller: 'hoteRoomController'
	})/*
	.when('/editHotelRoomType', {
		templateUrl: '/assets/html/hotel_room_info.html',
		controller: 'hoteRoomController'
	})*/

});