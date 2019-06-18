import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AdditionalServicesModel } from '../model/AdditionalServices.model';
import { AccomodationTypeModel } from '../model/AccomodationType.model';

@Injectable()
export class AccomodationService {

  private BASE_URL = 'https://localhost:8087/api/accomodation/accSevice';

  constructor(private http: HttpClient) {

  }


  addNewAdditionalService(service: AdditionalServicesModel) {
    return this.http.post<AdditionalServicesModel[]>(`${this.BASE_URL}/addNewAdditionalService`, service);
  }

  removeAdditionalService(service: AdditionalServicesModel) {
    return this.http.post<AdditionalServicesModel[]>(`${this.BASE_URL}/removeAdditionalService`, service);
  }

  addNewAccomodationType(type: AccomodationTypeModel) {
    return this.http.post<AccomodationTypeModel[]>(`${this.BASE_URL}/addNewAccomodationType`, type);
  }

  removeAccomodationType(type: AccomodationTypeModel) {
    return this.http.post<AccomodationTypeModel[]>(`${this.BASE_URL}/addNewAccomodationType`, type);
  }

  getAdditionalServices() {
    return this.http.get<AdditionalServicesModel[]>(`${this.BASE_URL}/getAdditionalServices`);
  }

  getAccomodationTypes() {
    return this.http.get<AccomodationTypeModel[]>(`${this.BASE_URL}/getAccomodationTypes`);
  }
}
