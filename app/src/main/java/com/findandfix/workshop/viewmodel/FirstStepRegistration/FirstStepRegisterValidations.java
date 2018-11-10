package com.findandfix.workshop.viewmodel.FirstStepRegistration;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.findandfix.workshop.BR;
import com.findandfix.workshop.R;
import com.findandfix.workshop.ui.callback.CallAnotherActivityNavigator;
import com.findandfix.workshop.utils.ConfigurationFile;
import com.findandfix.workshop.utils.ValidationUtils;

/**
 * Created by DELL on 21/03/2018.
 */

public class FirstStepRegisterValidations extends BaseObservable {

    private String workshopNameError;
    private String EmaildError;
    private String passwordError;
    private String passwordConfirmationError;
    private String workshopPhoneError;
    private String workshopWebsite;
    private Context context;
    private CallAnotherActivityNavigator navigator;

    public FirstStepRegisterValidations(Context context, CallAnotherActivityNavigator navigator) {
        this.context = context;
        this.navigator = navigator;
    }

    @Bindable
    public String getWorkshopWebsite() {
        return workshopWebsite;
    }

    public void setWorkshopWebsite(String workshopWebsite) {
        this.workshopWebsite = workshopWebsite;
        notifyPropertyChanged(BR.workshopWebsite);
    }

    @Bindable
    public String getWorkshopPhoneError() {
        return workshopPhoneError;
    }

    public void setWorkshopPhoneError(String workshopPhoneError) {
        this.workshopPhoneError = workshopPhoneError;
        notifyPropertyChanged(BR.workshopPhoneError);
    }

    @Bindable
    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
        notifyPropertyChanged(BR.passwordError);
    }

    @Bindable
    public String getPasswordConfirmationError() {
        return passwordConfirmationError;
    }

    public void setPasswordConfirmationError(String passwordConfirmationError) {
        this.passwordConfirmationError = passwordConfirmationError;
        notifyPropertyChanged(BR.passwordConfirmationError);
    }

    @Bindable
    public String getEmaildError() {
        return EmaildError;
    }

    public void setEmaildError(String emaildError) {
        EmaildError = emaildError;
        notifyPropertyChanged(BR.emaildError);
    }

    public void setWorkshopNameError(String workshopNameError) {
        this.workshopNameError = workshopNameError;
        notifyPropertyChanged(BR.workshopNameError);
    }

    @Bindable
    public String getWorkshopNameError() {
        System.out.println("Workshop Name Error");
        return workshopNameError;
    }


    public int validator(String workshopName, String email, int validEmail, String password, String confirmpassword, String workshopPhone, String workshopWebsite, String location, double lat, double lang, int CountryPosition, int cityposition, int validPhone) {
        if (!(ValidationUtils.isEmpty(workshopName))) {

            setWorkshopNameError("");
            if (!ValidationUtils.isEmpty(email)) {
                setEmaildError("");
                if (ValidationUtils.isMail(email)) {
                    setEmaildError("");
                    if (validEmail == 0) {
                        setEmaildError("");
                        if (!ValidationUtils.isEmpty(password)) {
                            setPasswordError("");
                            if (ValidationUtils.checkPasswordLength(password)) {
                                setPasswordError("");
                                if (!ValidationUtils.isEmpty(confirmpassword)) {
                                    setPasswordConfirmationError("");
                                    if (ValidationUtils.checkPasswordLength(confirmpassword)) {
                                        setPasswordConfirmationError("");
                                        if (!ValidationUtils.isEmpty(workshopPhone)) {
                                            setWorkshopPhoneError("");
                                            if (ValidationUtils.isPhone(workshopPhone)) {
                                                setWorkshopPhoneError("");
                                                if (!ValidationUtils.isEmpty(workshopWebsite)) {
                                                    setWorkshopWebsite("");
                                                    //  if (ValidationUtils.isUrl(workshopWebsite)) {
                                                    //   setWorkshopWebsite("");
                                                    if ((!ValidationUtils.isEmpty(location)) && (lat != 0.0) && (lang != 0.0)) {

                                                        if (CountryPosition != -1) {
                                                            if (cityposition != -1) {
                                                                if (ValidationUtils.isPasswordMatches(password, confirmpassword)) {
                                                                    setPasswordError("");

                                                                    if (validPhone == 0) {

                                                                        return 1;
                                                                    } else {
                                                                        setWorkshopPhoneError(context.getString(R.string.invalid_phone_number));
                                                                    }
                                                                } else {
                                                                    setPasswordConfirmationError(context.getString(R.string.matches_password));
                                                                }

                                                            } else {
                                                                navigator.updateUi(ConfigurationFile.Constants.INVALID_CITY_CODE);
                                                            }
                                                        } else {
                                                            navigator.updateUi(ConfigurationFile.Constants.INVALID_COUNTRY_CODE);
                                                        }
//My Loc
                                                    } else {
                                                        navigator.updateUi(ConfigurationFile.Constants.INVALID_LOCATION_CODE);
                                                    }

                                                /*    } else {
                                                       setWorkshopWebsite(context.getString(R.string.invalid_site_url));
                                                    }*/
                                                } else {
                                                    setWorkshopWebsite(context.getString(R.string.reqired_field));
                                                }

                                            } else {
                                                setWorkshopPhoneError(context.getString(R.string.invalid_phone_format));
                                            }

                                        } else {
                                            setWorkshopPhoneError(context.getString(R.string.reqired_field));
                                        }

                                    } else {
                                        setPasswordConfirmationError(context.getString(R.string.password_length));
                                    }

                                } else {
                                    setPasswordConfirmationError(context.getString(R.string.reqired_field));
                                }

                            } else {
                                setPasswordError(context.getString(R.string.password_length));
                            }
                        } else {
                            setPasswordError(context.getString(R.string.reqired_field));
                        }
                    } else {
                        setEmaildError(context.getString(R.string.email_alredy_taken));
                    }

                } else {
                    setEmaildError(context.getString(R.string.invalid_email_format));
                }

            } else {
                setEmaildError(context.getString(R.string.reqired_field));
            }
        } else {
            System.out.println("Clicked eRROR");
            setWorkshopNameError(context.getString(R.string.reqired_field));

        }

        return 0;
    }


    public void reset() {
        context = null;
    }
}
