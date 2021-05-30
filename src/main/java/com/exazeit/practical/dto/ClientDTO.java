package com.exazeit.practical.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.exazeit.practical.util.Constants;

public class ClientDTO {

	private Integer id;

	@NotBlank(message = "First Name should not be blank")
	private String firstName;

	@NotBlank(message = "Last Name should not be blank")
	private String lastName;

	private String mobileNumber;

	@NotBlank(message = "Id Number should not be blank")
	@Pattern(regexp = "(((\\d{2}((0[13578]|1[02])(0[1-9]|[12]\\d|3[01])|(0[13456789]|1[012])(0[1-9]|[12]\\d|30)|02(0[1-9]|1\\d|2[0-8])))|([02468][048]|[13579][26])0229))(( |-)(\\d{4})( |-)(\\d{3})|(\\d{7}))", message = Constants.ENTER_VALID_ID_NUMBER)
	private String idNumber;

	private String physicalAddress;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getPhysicalAddress() {
		return physicalAddress;
	}

	public void setPhysicalAddress(String physicalAddress) {
		this.physicalAddress = physicalAddress;
	}

}
