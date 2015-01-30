package controllers.travelbusiness;

import java.io.File;
import java.util.List;
import java.util.Set;

import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.travelportal.domain.AmenitiesType;
import com.travelportal.domain.HotelAmenities;
import com.travelportal.domain.HotelProfile;
import com.travelportal.domain.InfoWiseImagesPath;
import com.travelportal.domain.Salutation;
import com.travelportal.domain.rooms.HotelRoomTypes;

public class HotelDetailsController extends Controller {

	@Transactional(readOnly = false)
	public static Result getHotelGenImg(long supplierCode) {

		InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath
				.findById(supplierCode);
		File f = new File(infowiseimagesPath.getGeneralPicture());
		return ok(f);

	}

	@Transactional(readOnly = false)
	public static Result getHotelServImg(long supplierCode) {

		InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath
				.findById(supplierCode);
		File f = new File(infowiseimagesPath.getAmenitiesServices());
		return ok(f);

	}

	@Transactional(readOnly = false)
	public static Result getHotelRoomImg(long supplierCode) {

		InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath
				.findById(supplierCode);
		File f = new File(infowiseimagesPath.getHotelRoom());
		return ok(f);

	}

	@Transactional(readOnly = false)
	public static Result getHotelLobbyImg(long supplierCode) {

		InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath
				.findById(supplierCode);
		File f = new File(infowiseimagesPath.getHotel_Lobby());
		return ok(f);

	}

	@Transactional(readOnly = false)
	public static Result getHotelLSImg(long supplierCode) {

		InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath
				.findById(supplierCode);
		File f = new File(infowiseimagesPath.getLeisureSports());
		return ok(f);

	}

	@Transactional(readOnly = false)
	public static Result getHotelMapImg(long supplierCode) {

		InfoWiseImagesPath infowiseimagesPath = InfoWiseImagesPath
				.findById(supplierCode);
		File f = new File(infowiseimagesPath.getMap_image());
		return ok(f);

	}
	
	@Transactional(readOnly=true)
	public static Result getAmenities() {
		final List<HotelAmenities> hotelamenities = HotelAmenities.getamenities(AmenitiesType.getamenitiesIdByCode(1));
		return ok(Json.toJson(hotelamenities));
	}
	
	@Transactional(readOnly=true)
	public static Result getBusiness() {
		final List<HotelAmenities> hotelamenities = HotelAmenities.getamenities(AmenitiesType.getamenitiesIdByCode(2));
		return ok(Json.toJson(hotelamenities));
	}

	@Transactional(readOnly=true)
	public static Result getLeisureSport() {
		List<HotelAmenities> hotelamenities = HotelAmenities.getamenities(AmenitiesType.getamenitiesIdByCode(3));
		return ok(Json.toJson(hotelamenities));
	}
	
	@Transactional(readOnly=true)
	public static Result findAmenitiesData(long supplierCode) {
		HotelProfile hotelProfile = HotelProfile.findAllData(supplierCode);
		Set<HotelAmenities> hotelamenities = hotelProfile.getAmenities();
		return ok(Json.toJson(hotelamenities));
	}
	
	@Transactional(readOnly=false)
	public static Result getHotelRoomImagePath(long roomId) {
		
		HotelRoomTypes hRoomTypes = HotelRoomTypes.findById(roomId);
		File f = new File(hRoomTypes.getRoomPic());
	    return ok(f);		
		
	}
	
}
